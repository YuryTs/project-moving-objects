package ru.cvetkov.moving.objects.services;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import main.proto.ExternalGeoposition;
import main.proto.ExternalGeopositionServiceGrpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.utils.*;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

import java.util.Optional;

import static ru.cvetkov.moving.objects.converters.TimestampSqlTimeConverter.convertToSqlTimestamp;

@Service
public class ExternalGrpcService extends ExternalGeopositionServiceGrpc.ExternalGeopositionServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DeviceService deviceService;
    private final GeopositionService geopositionService;

    private final EventPublisher geoPublisher;

    public ExternalGrpcService(DeviceServiceImpl deviceService, GeopositionServiceImpl geopositionService, EventPublisher geoPublisher) {
        this.deviceService = deviceService;
        this.geopositionService = geopositionService;
        this.geoPublisher = geoPublisher;
    }

    @Override
    public void putExternalGeoposition(ExternalGeoposition request, StreamObserver<Empty> responseObserver) {
        LOG.info("recived method putExternalGeoposition");
        if (
                request == null
                        || StringUtils.isEmpty(request.getImei())
                        || request.getLatitude() == 0.0
                        || request.getLongitude() == 0.0
                        || request.getSpeed() < 0
        ) {
            LOG.error("Bad putExternalGeoposition request content: {}", request); //TODO move to catch
            responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
            return;
        }
        String imei = request.getImei();

        try {
            Optional<Device> optionalDevice = deviceService.getDeviceByImei(imei);
            if (optionalDevice.isEmpty()) {
                responseObserver.onError(Status.NOT_FOUND.withDescription("Not found Device for imei = " + imei).asRuntimeException());
            return;
            }

            Timestamp externalGeoDateTime = request.getExternalGeopositionDateTime();
            java.sql.Timestamp geopositionDateTime = StringUtils.isEmpty(externalGeoDateTime.toString()) ? java.sql.Timestamp.valueOf(LocalDateTime.now()) : convertToSqlTimestamp(externalGeoDateTime);
            LOG.info("externalGeoDateTime = {}", externalGeoDateTime);
            Geoposition geoposition = new Geoposition.Builder()
                    .geopositionDateTime(geopositionDateTime)
                    .longitude(request.getLongitude())
                    .latitude(request.getLatitude())
                    .altitude(request.getAltitude())
                    .speed(request.getSpeed())
                    .direction(request.getDirection())
                    .device(optionalDevice.get())
                    .build();
            geopositionService.saveGeoposition(geoposition);
            Empty response = Empty.getDefaultInstance();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            LOG.info("Geoposition for deviceId = {} has saved successfully", optionalDevice.get().getId());

            Event event = new Event(EventType.ADD, geoposition);
            geoPublisher.publish(event);
            LOG.info("Geoposition for deviceId = {} has translated to webSocket", optionalDevice.get().getId());

        } catch (Exception e) {
            LOG.error("Error during putExternalGeoposition for imei = {}", imei);
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getClass().getSimpleName() + " : message: " + e.getMessage())
                            .withCause(e)
                            .asRuntimeException());
        }
    }

}
