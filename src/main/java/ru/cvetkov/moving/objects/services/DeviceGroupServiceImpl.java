package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.repositories.DeviceGroupRepository;
import ru.cvetkov.moving.objects.repositories.DeviceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceGroupServiceImpl implements DeviceGroupService {

    @Value("${spring.device_groups.max.page_size:5}")
    private Integer MAX_PAGE_SIZE;
    @Value("${spring.default.device_group_id:1}")
    private Long DEFAULT_DEVICE_GROUP_ID;
    private final DeviceGroupRepository deviceGroupRepository;
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupDtoRq) {
        DeviceGroup deviceGroup = new DeviceGroup();
        List<Device> devices = new ArrayList<>();
        deviceGroup.setDeviceGroupName(deviceGroupDtoRq.getDeviceGroupName());
        deviceGroup.setDeviceList(devices);
        DeviceGroup savedDeviceGroup = deviceGroupRepository.save(deviceGroup);

        if (deviceGroupDtoRq.getDeviceIds().isEmpty()) {
            return savedDeviceGroup;
        }
        Long deviceGroupId = savedDeviceGroup.getId();
        deviceGroupDtoRq.getDeviceIds().stream().forEach(id -> deviceRepository.findById(id).ifPresent(device -> {
                    device.setDeviceGroupId(deviceGroupId);
                    deviceRepository.save(device);
                    devices.add(device);
                })
        );
        savedDeviceGroup.setDeviceList(devices);
        deviceGroupRepository.save(savedDeviceGroup);
        Optional<DeviceGroup> deviceGroup1 = deviceGroupRepository.getDeviceGroupByName(deviceGroupDtoRq.getDeviceGroupName());
        System.out.println(deviceGroup1);
        return savedDeviceGroup;
    }

    @Override
    @Transactional
    public DeviceGroup updateDeviceGroupByIdGroup(DeviceGroupDtoRq deviceGroupDtoRq) {
        System.out.println("deviceGroupDtoRq ids =" + deviceGroupDtoRq.getDeviceIds());

        DeviceGroup deviceGroup = deviceGroupRepository.findById(deviceGroupDtoRq.getId()).orElseThrow(() -> new ResourceNotFoundException("Not fined group with id = " + deviceGroupDtoRq.getId()));
        System.out.println("deviceGroup=" + deviceGroupRepository.getDeviceGroupByName(deviceGroup.getDeviceGroupName()));
        System.out.println("deviceGroup List =" + deviceGroup.getDeviceList());

        if (StringUtils.isNotEmpty(deviceGroupDtoRq.getDeviceGroupName())) {
            deviceGroup.setDeviceGroupName(deviceGroupDtoRq.getDeviceGroupName());
        }
        List<Device> devices = new ArrayList<>();
        List<Long> newDeviceIds = deviceGroupDtoRq.getDeviceIds();
        System.out.println("newDeviceIds=" + newDeviceIds);
        if (deviceGroup != null) {
            List<Long> oldDeviceIds = deviceGroup.getDeviceList().stream().map(d -> d.getId()).collect(Collectors.toList());
            System.out.println("oldDeviceIds=" + oldDeviceIds);

            List<Long> ejectedIds = oldDeviceIds.stream().filter(id -> !newDeviceIds.contains(id)).collect(Collectors.toList());
            System.out.println("ejectedIds=" + ejectedIds);
            ejectedIds.stream().forEach(id -> deviceRepository.findById(id).ifPresent(device -> {
                device.setDeviceGroupId(DEFAULT_DEVICE_GROUP_ID);
                deviceRepository.save(device);
            }));
        }
        List<Device> devices1 = deviceGroupDtoRq.getDeviceIds().stream().map(id -> deviceRepository.findById(id).get()).collect(Collectors.toList());
        System.out.println(devices1);
        devices1.stream().forEach(device -> {
            device.setDeviceGroupId(DEFAULT_DEVICE_GROUP_ID);
            deviceRepository.save(device);
        });
        System.out.println("devices1="+devices1);
        deviceGroupDtoRq.getDeviceIds().stream().forEach(id -> deviceRepository.findById(id).ifPresent(device -> {
                    device.setDeviceGroupId(deviceGroupDtoRq.getId());
                    deviceRepository.save(device);
                    devices.add(device);
                })
        );
        System.out.println(devices);
        deviceGroup.setDeviceList(devices);
        System.out.println("deviceGroup before save = " + deviceGroup);
        deviceGroupRepository.save(deviceGroup);
        System.out.println("deviceGroup after save = " + deviceGroupRepository.getDeviceGroupByName("GUGA"));
        return deviceGroup;
    }

    @Override
    public List<DeviceGroup> getPageAsListDevices(int firstPage, int pageSize) {
        if (firstPage < 0) {
            firstPage = 0;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return deviceGroupRepository.findAll(PageRequest.of(firstPage, pageSize)).getContent();
    }

    @Override
    public boolean deleteDeviceGroupById(Long deviceGroupId) {
        boolean deleted = false;
        deviceGroupRepository.deleteById(deviceGroupId);
        deleted = true;
        return deleted;
    }

    @Override
    public DeviceGroup findDevicesByGroupName(String name) {
        return deviceGroupRepository.getDeviceGroupByName(name).orElseThrow(() -> new ResourceNotFoundException("DeviceGroup with name = " + name + " not found."));
    }


}
