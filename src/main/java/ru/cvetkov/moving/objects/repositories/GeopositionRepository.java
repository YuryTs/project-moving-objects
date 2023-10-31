package ru.cvetkov.moving.objects.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cvetkov.moving.objects.entities.Geoposition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GeopositionRepository extends JpaRepository<Geoposition, Long>{

        Optional<Geoposition> findLastGeopositionByDevice_Id(Long deviceId);

}
