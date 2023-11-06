package ru.cvetkov.moving.objects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cvetkov.moving.objects.entities.Geoposition;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface GeopositionRepository extends JpaRepository<Geoposition, Long>{

        @Query(value = "SELECT g FROM Geoposition g WHERE g.device.id = :deviceId AND g.geopositionDateTime BETWEEN :startDate AND :endDate")
        List<Geoposition> getGeopositionsByDeviceIdAndDateInterval(
                @Param("deviceId") Long deviceId,
                @Param("startDate") Timestamp startDate,
                @Param("endDate") Timestamp endDate
        );

//        List<Geoposition> findAllWithLatestGeopositionDateTime();



}
