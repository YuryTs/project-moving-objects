package ru.cvetkov.moving.objects.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cvetkov.moving.objects.converters.DeviceGroupConverter;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRs;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.services.DeviceGroupService;

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
}
