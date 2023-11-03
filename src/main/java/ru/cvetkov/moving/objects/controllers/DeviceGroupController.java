package ru.cvetkov.moving.objects.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cvetkov.moving.objects.converters.DeviceGroupConverter;
import ru.cvetkov.moving.objects.dto.*;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.exeptions.ValidationException;
import ru.cvetkov.moving.objects.services.DeviceGroupService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/group")
public class DeviceGroupController {

    DeviceGroupService deviceGroupService;
    DeviceGroupConverter deviceGroupConverter;

    @GetMapping("/byname/{name}")
    public DeviceGroupDtoRs getGroupByName(@PathVariable String name){
        return deviceGroupConverter.entityToDto(deviceGroupService.findDevcesByGroupName(name));
    }

    @PostMapping("/new")
    public DeviceGroupDtoRs createDeviceGroup(@RequestBody DeviceGroupDtoRq deviceGroupRq){ //todo validation
        DeviceGroup deviceGroup = deviceGroupService.createNewDeviceGroup(deviceGroupRq);
        return deviceGroupConverter.entityToDto(deviceGroup);
    }

    @GetMapping("/list")
    public List<DeviceGroupDtoRs> getPageDeviceGroup(@RequestParam(defaultValue = "1") int firstPage, @RequestParam(defaultValue = "3") int pageSize) {
        List<DeviceGroup> deviceGroups = deviceGroupService.getPageAsListDevices(firstPage, pageSize);
        return deviceGroups.stream().map(deviceGroupConverter::entityToDto).collect(Collectors.toList());
    }

    @PutMapping("/update")
    public DeviceGroupDtoRs updateDeviceGroup(@RequestBody DeviceGroupDtoRq deviceGroupDtoRq){
        if (deviceGroupDtoRq == null){
            throw new ValidationException(new ErrorDto("INVALID_PARAM", "Your request shouldn`t be null."));
        }
        return deviceGroupConverter.entityToDto(deviceGroupService.updateDeviceGroupByIdGroup(deviceGroupDtoRq));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MessageAnswerDto> removeDevicesFromGroup(@PathVariable Long deviceGroupId){
        if (deviceGroupId <= 0){
            throw new ValidationException(new ErrorDto("INVALID_PARAM", "Your request shouldn`t be null."));
        }
        if (!deviceGroupService.deleteDeviceGroupById(deviceGroupId)) {
            throw  new ResourceNotFoundException("DeviceGroup with id = " + deviceGroupId + " not found.");
        }
        return new ResponseEntity<>(new MessageAnswerDto("DeviceGroup with id = " + deviceGroupId + " deleted."), HttpStatus.OK);
    }





}
