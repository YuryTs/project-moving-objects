package ru.cvetkov.moving.objects.services;

import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

public interface DeviceGroupService {
    DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupRq);
}
