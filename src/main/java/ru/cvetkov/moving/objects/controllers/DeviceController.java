package ru.cvetkov.moving.objects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.services.DeviceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getMovingDevice(@PathVariable Long id){
        Optional<Device> device = deviceService.getById(id);
        if (!device.isPresent()){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(device.get(), HttpStatus.OK);
    }

    @GetMapping("/allDevices")
    public List<Device> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @GetMapping("/page")
    public List<Device> getPageDevices(@RequestParam(defaultValue = "1") int firstPage, @RequestParam(defaultValue = "3") int pageSize){
        return deviceService.getPageAsListDevices(firstPage, pageSize);
    }

    @PostMapping("/{name}")
    public String createDevice(@PathVariable String name){
        return deviceService.createDevice(new Device(name));
    }

    @PutMapping
    public String updateDevice(@PathVariable Device device){
        return deviceService.updateDevice(device);
    }
}
