package ru.cvetkov.moving.objects.controllers;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cvetkov.moving.objects.converters.DeviceConverter;
import ru.cvetkov.moving.objects.converters.GeopositionConverter;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.dto.DeviceDtoRs;
import ru.cvetkov.moving.objects.dto.ErrorDto;
import ru.cvetkov.moving.objects.dto.GeopositionDto;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.services.DeviceService;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/device_control")
public class DeviceController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final DeviceService deviceService;
    private final DeviceConverter deviceConverter;
    private final GeopositionConverter geopositionConverter;



    @GetMapping("/device/{id}")
    public DeviceDtoRs getDevice(@PathVariable Long id) {
        Device device = deviceService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Device with id = " + id + " not found."));
        return deviceConverter.entityToDto(device);
    }

    @GetMapping("/imei/{imei}")
    public DeviceDtoRs getDeviceByImei(@PathVariable String imei) {
        Device device = deviceService.getDeviceByImei(imei).orElseThrow(() -> new ResourceNotFoundException("Device with imei = " + imei + " not found."));
        return deviceConverter.entityToDto(device);
    }

    @GetMapping("/page")
    public List<DeviceDtoRs> getPageDevices(@RequestParam(defaultValue = "1") int firstPage, @RequestParam(defaultValue = "3") int pageSize) {
        List<Device> devices = deviceService.getPageAsListDevices(firstPage, pageSize);
        return devices.stream().map(deviceConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping()
    public DeviceDtoRs createDevice(@RequestBody DeviceDtoRq deviceDtoRq) {
        Device device = deviceService.createNewDevice(deviceDtoRq);
        return deviceConverter.entityToDto(device);
    }

    @PutMapping
    public ResponseEntity<?> updateDevice(@RequestBody DeviceDtoRq deviceDtoRq) {
        if (
                deviceDtoRq == null
                        || deviceDtoRq.getId() <= 0
                        || deviceDtoRq.getId() >= Long.MAX_VALUE
                        || StringUtils.isEmpty(deviceDtoRq.getDeviceName())
        ) {
            LOG.info("request for updateDevice has INVALID_PARAMS");
            return new ResponseEntity<>(new ErrorDto("INVALID_PARAM", "You should check request`s parameters"), HttpStatus.BAD_REQUEST);
        }
        Device device = deviceService.updateDevice(deviceDtoRq);
        return new ResponseEntity<>(deviceConverter.entityToDto(device), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if ( id <= 0 || id >= Long.MAX_VALUE) {
            LOG.info("request for updateDevice has INVALID_PARAMS");
            return new ResponseEntity<>(new ErrorDto("INVALID_PARAM", "You should check request`s parameters"), HttpStatus.BAD_REQUEST);
        }
        if (deviceService.deletById(id)) {
            return new ResponseEntity<>("Device with id = " + id + " deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorDto("NOT_FOUND", "Device with id = " + id + " not found"), HttpStatus.BAD_REQUEST);
    }

    // Request with __Date as 2023-08-15 08:06:21
    @GetMapping("/geopositions")
    public List<GeopositionDto> getGeopositionsForDevice( //todo validation?
            @RequestParam Long deviceId,
            @RequestParam Timestamp startDate,
            @RequestParam Timestamp endDate) {
        List<Geoposition> geopositions = deviceService.getGeopositionsByDeviceIdAndDateInterval(deviceId, startDate, endDate);
        return geopositions.stream().map(geopositionConverter::entityToDo).collect(Collectors.toList());
    }
}
