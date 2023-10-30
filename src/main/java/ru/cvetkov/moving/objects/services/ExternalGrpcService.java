package ru.cvetkov.moving.objects.services;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import main.proto.ExternalGeoposition;
import main.proto.ExternalGeopositionServiceGrpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.entities.Device;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Service
public class ExternalGrpcService extends ExternalGeopositionServiceGrpc.ExternalGeopositionServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DeviceServiceImpl deviceService;

    public ExternalGrpcService(DeviceServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void putExternalGeoposition(ExternalGeoposition request, StreamObserver<Empty> responseObserver) {
        LOG.info("recived method putExternalGeoposition");
        if (
                request == null
                        || StringUtils.isEmpty(request.getImei())
                        || request.getLatitude() == 0.0
                        || request.getLongitude() == 0
                        || request.getSpeed() <= 0
//                        || request.hasTrackTime()
        ) {
            LOG.error("Bad putExternalGeoposition request content: {}", request);
            responseObserver.onError(Status.INVALID_ARGUMENT.asRuntimeException());
            return;
        }
        String imei = request.getImei();

        try {
            Optional<Device> optionalDevice = deviceService.getDeviceByImei(imei);

            if (!optionalDevice.isPresent()) {
                responseObserver.onError(Status.NOT_FOUND.withDescription("Not found Device for imei = " + imei).asRuntimeException());
            }

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
