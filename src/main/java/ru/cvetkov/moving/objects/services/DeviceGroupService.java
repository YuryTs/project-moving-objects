package ru.cvetkov.moving.objects.services;

import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

import java.util.List;
import java.util.Optional;

public interface DeviceGroupService {
    DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupRq);

    List<String> delDevicesToDeviceGroup(String name, List<Long> deviceIds);

    List<String> findDevcesByGroupName(String name);

    List<String> putDevicesToDeviceGroup(String name, List<Long> deviceIds);
}
