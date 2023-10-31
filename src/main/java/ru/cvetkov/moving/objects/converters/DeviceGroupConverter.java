package ru.cvetkov.moving.objects.converters;

import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRs;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

public class DeviceGroupConverter {
    public DeviceGroupDtoRs entityToDto(DeviceGroup deviceGroup){
        return new DeviceGroupDtoRs(
                deviceGroup.getId(),
                deviceGroup.getDeviceGroupName()
        );
    }
}
