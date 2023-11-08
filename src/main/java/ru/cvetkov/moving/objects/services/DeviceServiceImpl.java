package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.dto.ErrorDto;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.exeptions.ValidationException;
import ru.cvetkov.moving.objects.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;

    @Value("${spring.max.page_size:5}")
    private Integer MAX_PAGE_SIZE;
    @Value("${spring.default.device_group_id:1}")
    private Long DEFAULT_DEVICE_GROUP_ID;

    @Override
    public Optional<Device> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Device> getDeviceByImei(String imei) {
        return repository.getDeviceByImei(imei);
    }

    @Override
    public List<Device> getPageAsListDevices(int firstPage, int pageSize) {
        if (firstPage < 0) {
            firstPage = 0;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return repository.findAll(PageRequest.of(firstPage, pageSize)).getContent();
    }

    @Override
    @Transactional
    public Device updateDevice(DeviceDtoRq deviceDtoRq) {
        Device deviceForUpdate = repository.findById(deviceDtoRq.getId()).get();//TODO validation if DB hasn`t that deviceId
        deviceForUpdate.setName(deviceDtoRq.getDeviceName());
        if (!deviceDtoRq.getDeviceName().isEmpty()) {
            deviceForUpdate.setDeviceGroupId(deviceDtoRq.getId());
        }
        return repository.save(deviceForUpdate);
    }

    @Override
    @Transactional
    public boolean deletById(Long id) {
        Optional<Device> optionalDevice = repository.findById(id);
        if (optionalDevice.isEmpty()) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public Device createNewDevice(DeviceDtoRq deviceDtoRq) {
        Device device = new Device();
        String imei = deviceDtoRq.getImei();
        if (repository.getDeviceByImei(imei).isPresent()) {
            throw new ValidationException(new ErrorDto("INVALID_PARAMS", "You can`t create device with imai = " + imei));
        }
        device.setName(deviceDtoRq.getDeviceName());
        device.setImei(deviceDtoRq.getImei());
        device.setDeviceGroupId(device.getDeviceGroupId());
        if (deviceDtoRq.getDeviceGroupId() == null) {
            device.setDeviceGroupId(DEFAULT_DEVICE_GROUP_ID);
        } else {
            device.setDeviceGroupId(deviceDtoRq.getDeviceGroupId());
        }
        return repository.save(device);
    }

}
