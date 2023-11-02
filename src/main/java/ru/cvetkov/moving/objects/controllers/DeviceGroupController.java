package ru.cvetkov.moving.objects.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.cvetkov.moving.objects.converters.DeviceGroupConverter;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRequest;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRs;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.services.DeviceGroupService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/group")
public class DeviceGroupController {

    DeviceGroupService deviceGroupService;
    DeviceGroupConverter deviceGroupConverter;

    @PostMapping
    public DeviceGroupDtoRs createDeviceGroup(@RequestBody DeviceGroupDtoRq deviceGroupRq){ //todo validation
        DeviceGroup deviceGroup = deviceGroupService.createNewDeviceGroup(deviceGroupRq);
        return deviceGroupConverter.entityToDto(deviceGroup);
    }

    @PutMapping("/putdevices")
    public List<String> putDevicesToGroup(@RequestBody DeviceGroupDtoRequest  group){
        return deviceGroupService.putDevicesToDeviceGroup(group.getName(), group.getDeviceIds());
    }

    @PutMapping("/delldevices")
    public List<String> removeDevicesFromGroup(@RequestBody DeviceGroupDtoRequest  group){
        return deviceGroupService.delDevicesToDeviceGroup(group.getName(), group.getDeviceIds());
    }


    @GetMapping("/name/{name}")
    public List<String> getGroupByName(@PathVariable String name){
        return deviceGroupService.findDevcesByGroupName(name);
    }


}
