package ru.cvetkov.moving.objects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

import java.util.Optional;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

    @Query(value = "SELECT gr FROM DeviceGroup gr WHERE gr.deviceGroupName = :name")
    Optional<DeviceGroup> getDeviceGroupByName(@Param("name") String name);
}
