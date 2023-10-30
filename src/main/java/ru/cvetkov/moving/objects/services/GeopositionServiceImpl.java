package ru.cvetkov.moving.objects.services;

import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.repositories.GeopositionRepository;

@Service
public class GeopositionServiceImpl implements GeopositionService{
    GeopositionRepository geopositionRepository;
    @Override
    public void saveGeoposition(Geoposition geoposition) {
        geopositionRepository.save(geoposition);
    }
}
