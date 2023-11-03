package ru.cvetkov.moving.objects.converters;

import javafx.util.Pair;
import org.springframework.stereotype.Component;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRs;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceGroupConverter {
    public DeviceGroupDtoRs entityToDto(DeviceGroup deviceGroup){
        List<Pair<Long,String>> pairs = deviceGroup.getDeviceList().stream().map(device -> new Pair(device.getId(), device.getName())).collect(Collectors.toList());
        return new DeviceGroupDtoRs(
                deviceGroup.getId(),
                deviceGroup.getDeviceGroupName(),
                pairs
        );
    }
}
