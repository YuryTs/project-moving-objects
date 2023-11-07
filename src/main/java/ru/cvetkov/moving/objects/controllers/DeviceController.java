package ru.cvetkov.moving.objects.controllers;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.cvetkov.moving.objects.converters.DeviceConverter;
import ru.cvetkov.moving.objects.converters.GeopositionConverter;
import ru.cvetkov.moving.objects.dto.*;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.exeptions.ValidationException;
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


    @GetMapping("/byid/{id}")
    public DeviceDtoRs getDevice(@PathVariable Long id) {
        Device device = deviceService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Device with id = " + id + " not found."));
        return deviceConverter.entityToDto(device);
    }

    @GetMapping("/byimei/{imei}")
    public DeviceDtoRs getDeviceByImei(@PathVariable String imei) {
        Device device = deviceService.getDeviceByImei(imei).orElseThrow(() -> new ResourceNotFoundException("Device with imei = " + imei + " not found."));
        return deviceConverter.entityToDto(device);
    }

    @GetMapping("/list")
    public List<DeviceDtoRs> getPageDevices(@RequestParam(defaultValue = "0") int firstPage, @RequestParam(defaultValue = "3") int pageSize) {
        List<Device> devices = deviceService.getPageAsListDevices(firstPage, pageSize);
        return devices.stream().map(deviceConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping("/new")
    public DeviceDtoRs createDevice(@RequestBody DeviceDtoRq deviceDtoRq) {
        Device device = deviceService.createNewDevice(deviceDtoRq);
        return deviceConverter.entityToDto(device);
    }

    @PutMapping("/update")
    public ResponseEntity<DeviceDtoRs> updateDevice(@RequestBody DeviceDtoRq deviceDtoRq) {
        if (deviceDtoRq == null || deviceDtoRq.getId() <= 0 || StringUtils.isEmpty(deviceDtoRq.getDeviceName())) {
            LOG.info("request for updateDevice has INVALID_PARAMS");
            throw  new ValidationException(new ErrorDto("INVALID_PARAMS", "You should check request`s parameters"));
        }
        Device device = deviceService.updateDevice(deviceDtoRq);
        return new ResponseEntity<>(deviceConverter.entityToDto(device), HttpStatus.OK);
    }

    @DeleteMapping("/byid/{id}")
    public ResponseEntity<MessageAnswerDto> deleteById(@PathVariable Long id) {
        if (id <= 0) {
            LOG.info("request for updateDevice has INVALID_PARAMS");
            throw  new ValidationException(new ErrorDto("INVALID_PARAMS", "You should check request`s parameters"));
        }
        if (!deviceService.deletById(id)) {
            throw  new ResourceNotFoundException("Device with id = " + id + " not found.");
        }
        return new ResponseEntity<>(new MessageAnswerDto("Device with id = " + id + " deleted."), HttpStatus.OK);
    }

    // Request with __Date as 2023-08-15 08:06:21
    @GetMapping("/geopositions")
    public List<GeopositionDto> getGeopositionsForDevice(
            @RequestParam Long deviceId,
            @RequestParam Timestamp startDate,
            @RequestParam Timestamp endDate
    ) {
        if (startDate.after(endDate)){
            throw new ValidationException(new ErrorDto("INVALID_PARAMS", "startDate must be before endDate"));
        }
        List<Geoposition> geopositions = deviceService.getGeopositionsByDeviceIdAndDateInterval(deviceId, startDate, endDate);
        return geopositions.stream().map(geopositionConverter::entityToDo).collect(Collectors.toList());
    }

    @MessageMapping("/currentgeoposition")
    @SendTo("/topic/activity")
    public Message change(Message message) {
        return null;
    }
}
