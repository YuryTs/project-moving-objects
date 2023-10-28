package ru.cvetkov.moving.objects.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.dto.DeviceDtoRq;
import ru.cvetkov.moving.objects.entities.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    public Optional<Device> getById(Long id);

    public List<Device> getAllDevices();
    public List<Device> getPageAsListDevices(int firstPage, int pageSize);

    public Device save(Device device);

    public Device updateDevice(DeviceDtoRq deviceDtoRq);

    public void deletById(Long id);

    public Device createNewDevice(DeviceDtoRq deviceDtoRq);
}
