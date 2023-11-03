package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.dto.ErrorDto;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.exeptions.ValidationException;
import ru.cvetkov.moving.objects.repositories.DeviceGroupRepository;
import ru.cvetkov.moving.objects.repositories.DeviceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceGroupServiceImpl implements DeviceGroupService {
    @Value("${spring.device_groups.max.page_size:5}")
    private Integer MAX_PAGE_SIZE;

    private final DeviceGroupRepository deviceGroupRepository;
    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;

    private List<Device> devices = new ArrayList<>();
//    @Override
//    public DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupRq) {
//        DeviceGroup deviceGroup = new DeviceGroup();
//        deviceGroup.setDeviceGroupName(deviceGroupRq.getDeviceGroupName());
//        deviceGroup.setDeviceList(deviceGroupRq.getDeviceList());
//        return deviceGroupRepository.save(deviceGroup);
//    }

    @Override
    @Transactional
    public DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupDtoRq) {
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setDeviceGroupName(deviceGroupDtoRq.getDeviceGroupName());
        deviceGroupDtoRq.getDeviceIds().stream().forEach(id -> deviceRepository.findById(id).ifPresent(device -> {
                    device.setDeviceGroupId(id);
                    deviceRepository.save(device);
                })
        );
        return deviceGroupRepository.save(deviceGroup);
    }

    @Override
    public DeviceGroup updateDeviceGroupByIdGroup(DeviceGroupDtoRq deviceGroupDtoRq)  {
        try {
            deviceGroupRepository.updateDeviceIdsForDeviceGroup(deviceGroupDtoRq.getDeviceIds(), deviceGroupDtoRq.getId());
            return deviceGroupRepository.getReferenceById(deviceGroupDtoRq.getId());
        } catch (Exception e){
            throw new ResourceNotFoundException("Request has wrong parametres");
        }
    }

    @Override
    public List<DeviceGroup> getPageAsListDevices(int firstPage, int pageSize){
        if (firstPage < 0){
            firstPage = 0;
        }
        if (pageSize > MAX_PAGE_SIZE){
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
    public DeviceGroup findDevcesByGroupName(String name) {
        return deviceGroupRepository.getDeviceGroupByName(name).orElseThrow(() -> new ResourceNotFoundException("DeviceGroup with name = " + name + " not found."));
    }



}
