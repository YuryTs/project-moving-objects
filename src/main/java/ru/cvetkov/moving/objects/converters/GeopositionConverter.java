package ru.cvetkov.moving.objects.converters;

import org.springframework.stereotype.Component;
import ru.cvetkov.moving.objects.dto.GeopositionDto;
import ru.cvetkov.moving.objects.entities.Geoposition;

@Component
public class GeopositionConverter {
    public GeopositionDto entityToDo(Geoposition geoposition){
        return new GeopositionDto(
                geoposition.getGeopositionDateTime(),
                geoposition.getLongitude(),
                geoposition.getLatitude(),
                geoposition.getAltitude(),
                geoposition.getSpeed(),
                geoposition.getDirection());
    }
}
