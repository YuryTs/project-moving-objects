package ru.cvetkov.moving.objects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cvetkov.moving.objects.entities.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
