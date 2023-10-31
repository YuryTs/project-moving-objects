package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.exeptions.ResourceNotFoundException;
import ru.cvetkov.moving.objects.repositories.DeviceGroupRepository;
import ru.cvetkov.moving.objects.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{
    private final Integer DEFOULT_DEVICE_GROUP = 1;
    private final DeviceRepository repository;
    private final DeviceGroupService deviceGroupService;
    private final DeviceGroupRepository deviceGroupRepository;
    private final GeopositionService geopositionService;

    @Override
    public Optional<Device> getById(Long id){
        return repository.findById(id); //todo validation -1
    }

    @Override
    public Optional<Device> getDeviceByImei(String imei) {
        return repository.getDeviceByImei(imei);//todo validation?
    }

    @Override
    public List<Device> getPageAsListDevices(int firstPage, int pageSize){ //todo validation page max pages
        return repository.findAll(PageRequest.of(firstPage - 1, pageSize)).getContent();
    }

//    @Override
//    public Device save(Device device){
//        String imei = device.getImei();
//        if (imei.isEmpty() || repository.getDeviceByImei(imei).isPresent()) {
//            throw new ResourceNotFoundException("You can`t save device with imei = " + imei);
//        }
//        if (device.getDeviceGroup() == null){ //todo delete
//            device.setDeviceGroup(DEFOULT_DEVICE_GROUP);
//        }
//        return repository.save(device);
//    } //todo удалить

    @Override
    @Transactional
    public Device updateDevice(DeviceDtoRq deviceDtoRq){
        Device deviceForUpdate = repository.findById(deviceDtoRq.getId()).get(); //todo validation if id==null
        deviceForUpdate.setName(deviceDtoRq.getDeviceName());//todo засетить все поля
        return repository.save(deviceForUpdate);
    }

    @Override
    public void deletById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Device createNewDevice(DeviceDtoRq deviceDtoRq) {
        Device device = new Device();
        device.setName(deviceDtoRq.getDeviceName());
//        device.setImei(deviceDtoRq.IMei);
        device.setDeviceGroup(deviceGroupRepository.getReferenceById(deviceDtoRq.getId()));
        return repository.save(device);
    }

    @Override
    public Optional<Geoposition> getLastGeoposition(Long deviceId){
        return geopositionService.getLastGeoposition(deviceId);
    }
}
