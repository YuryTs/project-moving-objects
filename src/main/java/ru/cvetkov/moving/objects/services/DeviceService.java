package ru.cvetkov.moving.objects.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.Geoposition;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface DeviceService {
    Optional<Device> getById(Long id);

    Optional<Device> getDeviceByImei(String imei);

    List<Device> getPageAsListDevices(int firstPage, int pageSize);

    Device updateDevice(DeviceDtoRq deviceDtoRq);

    boolean deletById(Long id);

    Device createNewDevice(DeviceDtoRq deviceDtoRq);
}
