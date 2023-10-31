package ru.cvetkov.moving.objects.services;

import ru.cvetkov.moving.objects.entities.Geoposition;

import java.util.Optional;

public interface GeopositionService {
    void saveGeoposition(Geoposition geoposition);

    Optional<Geoposition> getLastGeoposition(Long deviceId);
}
