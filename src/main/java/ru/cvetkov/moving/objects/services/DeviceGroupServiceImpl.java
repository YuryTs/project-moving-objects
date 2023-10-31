package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.repositories.DeviceGroupRepository;

@Service
@RequiredArgsConstructor
public class DeviceGroupServiceImpl implements DeviceGroupService{

    private final DeviceGroupRepository deviceGroupRepository;
    @Override
    public DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupRq) {
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setDeviceGroupName(deviceGroupRq.getDeviceGroupName());
        deviceGroup.setDeviceList(deviceGroupRq.getDeviceList());
        return deviceGroupRepository.save(deviceGroup);
    }
}
