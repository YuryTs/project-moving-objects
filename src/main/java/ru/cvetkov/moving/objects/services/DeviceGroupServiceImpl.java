package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.dto.DeviceGroupDtoRq;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.repositories.DeviceGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceGroupServiceImpl implements DeviceGroupService{

    private final DeviceGroupRepository deviceGroupRepository;
    private final DeviceService deviceService;

    private List<Device> devices = new ArrayList<>();
    @Override
    public DeviceGroup createNewDeviceGroup(DeviceGroupDtoRq deviceGroupRq) {
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setDeviceGroupName(deviceGroupRq.getDeviceGroupName());
        deviceGroup.setDeviceList(deviceGroupRq.getDeviceList());
        return deviceGroupRepository.save(deviceGroup);
    }

    @Override
    public List<String> putDevicesToDeviceGroup(String name, List<Long> deviceIds){
        if(deviceIds.isEmpty()){
            //todo validation
        }
        for (var deviceId : deviceIds){
            Device device = deviceService.getById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device with id = " + deviceId + " not found."));
            devices.add(device);
        }
        DeviceGroup deviceGroup = deviceGroupRepository.getDeviceGroupByName(name).orElseThrow(() -> new ResourceNotFoundException("DeviceGroup with name = " + name + " not found."));
        deviceGroup.setDeviceList(devices);
        deviceGroupRepository.save(deviceGroup);
        return deviceGroup.getDeviceList().stream().map(device -> device.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> delDevicesToDeviceGroup(String name, List<Long> deviceIds){
        if(deviceIds.isEmpty()){
            //todo validation
        }
        for (var deviceId : deviceIds){
            Device device = deviceService.getById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device with id = " + deviceId + " not found."));
            devices.remove(device);
        }
        DeviceGroup deviceGroup = deviceGroupRepository.getDeviceGroupByName(name).orElseThrow(() -> new ResourceNotFoundException("DeviceGroup with name = " + name + " not found."));
        deviceGroup.setDeviceList(devices);
        deviceGroupRepository.save(deviceGroup);
        return deviceGroup.getDeviceList().stream().map(device -> device.getName()).collect(Collectors.toList());
    }
    
    @Override
    public List<String> findDevcesByGroupName(String name) {
        DeviceGroup deviceGroup = deviceGroupRepository.getDeviceGroupByName(name).orElseThrow(() -> new ResourceNotFoundException("DeviceGroup with name = " + name + " not found."));
        return  deviceGroup.getDeviceList().stream().map(device -> device.getName()).collect(Collectors.toList());
    }

}
