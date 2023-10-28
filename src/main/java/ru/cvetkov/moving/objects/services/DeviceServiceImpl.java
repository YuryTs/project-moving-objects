package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{
    private final DeviceRepository repository;

    @Override
    public Optional<Device> getById(Long id){
        return repository.findById(id); //todo validation
    }

    @Override
    public List<Device> getAllDevices(){
        return repository.findAll();
    }

    @Override
    public List<Device> getPageAsListDevices(int firstPage, int pageSize){
        return repository.findAll(PageRequest.of(firstPage - 1, pageSize)).getContent();
    }

    @Override
    public Device save(Device device){
        return repository.save(device);
    }

    @Override
    @Transactional
    public Device updateDevice(DeviceDtoRq deviceDtoRq){
        Device deviceForUpdate = repository.findById(deviceDtoRq.getId()).get(); //todo validation if id==null
        deviceForUpdate.setDeviceName(deviceDtoRq.getDeviceName());
        return save(deviceForUpdate);
    }

    @Override
    public void deletById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Device createNewDevice(DeviceDtoRq deviceDtoRq) {
        Device device = new Device();
        device.setDeviceName(deviceDtoRq.getDeviceName());
        return save(device);
    }
}
