package ru.cvetkov.moving.objects.utils;

import ru.cvetkov.moving.objects.entities.Geoposition;

public interface GeopositionListener {
    void accept(Event event) throws InterruptedException;
}
