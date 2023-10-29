package ru.cvetkov.moving.objects.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cvetkov.moving.objects.converters.DeviceConverter;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.dto.DeviceDtoRs;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.services.DeviceService;
import ru.cvetkov.moving.objects.services.DeviceServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceConverter deviceConverter;

    private final DeviceService deviceService;


    @GetMapping("/{id}")
    public DeviceDtoRs getDevice(@PathVariable Long id){
        Device device = deviceService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Device with id = " + id + " not found."));
        return deviceConverter.entityToDto(device);
    }

    @GetMapping("/imei/{imei}")
    public DeviceDtoRs getDeviceByImei(@PathVariable String imei){
        Device device= deviceService.getDeviceByImei(imei).orElseThrow(() -> new RuntimeException("Device with imei = " + imei + " not found."));
        return deviceConverter.entityToDto(device);
    }

    @GetMapping("/page")
    public List<Device> getPageDevices(@RequestParam(defaultValue = "1") int firstPage, @RequestParam(defaultValue = "3") int pageSize){
        return deviceService.getPageAsListDevices(firstPage, pageSize); //todo list<page>
    }

    @PostMapping()
    public DeviceDtoRs createDevice(@RequestBody DeviceDtoRq deviceDtoRq){
        Device device = deviceService.createNewDevice(deviceDtoRq);
        return deviceConverter.entityToDto(device);
    }

    @PutMapping
    public DeviceDtoRs updateDevice(@RequestBody DeviceDtoRq deviceDtoRq){
        Device device = deviceService.updateDevice(deviceDtoRq);
        return deviceConverter.entityToDto(device);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        deviceService.deletById(id); //todo validation
    }

}
