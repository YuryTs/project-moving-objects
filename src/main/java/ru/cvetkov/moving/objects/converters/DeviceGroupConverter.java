package ru.cvetkov.moving.objects.converters;

import org.springframework.stereotype.Component;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRs;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

@Component
public class DeviceGroupConverter {
    public DeviceGroupDtoRs entityToDto(DeviceGroup deviceGroup){
        return new DeviceGroupDtoRs(
                deviceGroup.getId(),
                deviceGroup.getDeviceGroupName()
        );
    }
}
