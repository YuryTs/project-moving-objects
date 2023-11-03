package ru.cvetkov.moving.objects.services;

import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

import java.util.List;

public interface DeviceGroupService {
    DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupRq);

    boolean deleteDeviceGroupById(Long deviceGroupId);

    DeviceGroup findDevcesByGroupName(String name);

    List<DeviceGroup> getPageAsListDevices(int firstPage, int pageSize);

    DeviceGroup updateDeviceGroupByIdGroup(DeviceGroupDtoRq deviceGroupDtoRq);
}
