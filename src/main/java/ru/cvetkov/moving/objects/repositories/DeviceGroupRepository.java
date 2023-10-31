package ru.cvetkov.moving.objects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {
}
