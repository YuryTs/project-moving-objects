package ru.cvetkov.moving.objects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.cvetkov.moving.objects.entities.Device;
import ru.cvetkov.moving.objects.entities.DeviceGroup;

import java.util.List;
import java.util.Optional;

public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

    @Query(value = "SELECT gr FROM DeviceGroup gr WHERE gr.deviceGroupName = :name")
    Optional<DeviceGroup> getDeviceGroupByName(@Param("name") String name);

    @Query("UPDATE DeviceGroup dg SET dg.deviceList = :deviceIds WHERE dg.id = :devicegroupId")
    void updateDeviceIdsForDeviceGroup(@Param("deviceIds") List<Long> deviceIds, @Param("devicegroupId") Long devicegroupId);

    DeviceGroup save(DeviceGroup deviceGroup);

    Long getByDeviceGroupName(String name);

}
