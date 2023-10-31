package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.repositories.GeopositionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeopositionServiceImpl implements GeopositionService{
    private final GeopositionRepository geopositionRepository;

    @Override
    public void saveGeoposition(Geoposition geoposition) {
        geopositionRepository.save(geoposition);
    }

    @Override
    public Optional<Geoposition> getLastGeoposition(Long deviceId) {
//        return geopositionRepository.findById(deviceId);
        return geopositionRepository.findLastGeopositionByDevice_Id(deviceId);
//        return geopositionRepository.findFirstByDevice_IdOrderByGeopositionDateTimeGeopositionDateTimeDesc(deviceId);
    }
}


