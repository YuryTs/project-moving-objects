package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository repository;

    public Optional<Device> getById(Long id){
        return repository.findById(id);
    }

    public List<Device> getAllDevices(){
        return repository.findAll();
    }
    public List<Device> getPageAsListDevices(int firstPage, int pageSize){
        return repository.findAll(PageRequest.of(firstPage - 1, pageSize)).getContent();
    }

    public String createDevice(Device device){
        repository.save(device);
        System.out.printf("Device {} saved", device.getDeviceName());
        return "Device " + device.getDeviceName() + "saved";
    }
    public String updateDevice(Device device){
        Device deviceForUpdate = repository.findById(device.getId()).get();
        deviceForUpdate.setDeviceName(device.getDeviceName());
        createDevice(deviceForUpdate);
        return "Device id = " + device.getId() + "updated";
    }
}
