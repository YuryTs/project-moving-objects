package ru.cvetkov.moving.objects.converters;

import org.springframework.stereotype.Component;
import ru.cvetkov.moving.objects.dto.DeviceDtoRs;
import ru.cvetkov.moving.objects.entities.Device;

@Component
public class DeviceConverter {
    public DeviceDtoRs entityToDto(Device device){
        return new DeviceDtoRs(
                device.getId(),
                device.getName()
        );
    }
}
