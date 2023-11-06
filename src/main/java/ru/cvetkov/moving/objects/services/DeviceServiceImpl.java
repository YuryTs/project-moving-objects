package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.dto.ErrorDto;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.DeviceGroup;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.exeptions.ValidationException;
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
//    private final DeviceGroupService deviceGroupService;
    private final GeopositionService geopositionService;

    @Value("${spring.max.page_size:5}")
    private Integer MAX_PAGE_SIZE;
    @Override
    public Optional<Device> getById(Long id){
        return repository.findById(id);
    }

    @Override
    public Optional<Device> getDeviceByImei(String imei) {
        return repository.getDeviceByImei(imei);
    }

    @Override
    public List<Device> getPageAsListDevices(int firstPage, int pageSize){
        if (firstPage < 0){
            firstPage = 0;
        }
        if (pageSize > MAX_PAGE_SIZE){
            pageSize = MAX_PAGE_SIZE;
        }
        return repository.findAll(PageRequest.of(firstPage, pageSize)).getContent();
    }

    @Override
    @Transactional
    public Device updateDevice(DeviceDtoRq deviceDtoRq){
        Device deviceForUpdate = repository.findById(deviceDtoRq.getId()).get();
        deviceForUpdate.setName(deviceDtoRq.getDeviceName()); //todo нужно ли иметь возможность менять отсюда группу в которую входит устройство
        return repository.save(deviceForUpdate);
    }

    @Override
    @Transactional
    public boolean deletById(Long id) {
        boolean deleted = false;
        Optional<Device> optionalDevice = repository.findById(id);
        if (optionalDevice.isEmpty()){
           return deleted;
        }
        repository.deleteById(id);
        deleted = true;
        return deleted;
    }

    @Override
    public Device createNewDevice(DeviceDtoRq deviceDtoRq) {
        Device device = new Device();
        String imei = deviceDtoRq.getImei();
        if (repository.getDeviceByImei(imei).isPresent()){
            throw new ValidationException(new ErrorDto("INVALID_PARAMS", "You can`t create device with imai = " + imei));
        }
        device.setName(deviceDtoRq.getDeviceName());
        device.setImei(deviceDtoRq.getImei());
        device.setDeviceGroupId(device.getDeviceGroupId());
        if(deviceDtoRq.getDeviceGroupId() == null){
            device.setDeviceGroupId(1L);
        } else {
            device.setDeviceGroupId(deviceDtoRq.getDeviceGroupId());
        }
//        device.setDeviceGroup(deviceDtoRq.);
        return repository.save(device);
    }

    @Override
    public List<Geoposition> getGeopositionsByDeviceIdAndDateInterval(Long deviceId, Timestamp startDate, Timestamp endDate) {
        return geopositionService.getGeopositionsByDeviceIdAndDateInterval(deviceId, startDate, endDate);
    }

}
