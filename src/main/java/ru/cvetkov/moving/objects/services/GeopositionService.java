package ru.cvetkov.moving.objects.services;

import ru.cvetkov.moving.objects.entities.Geoposition;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface GeopositionService {
    void saveGeoposition(Geoposition geoposition);

    List<Geoposition> getGeopositionsByDeviceIdAndDateInterval(Long deviceId, Timestamp startDate, Timestamp endDate);

}
