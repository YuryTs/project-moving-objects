package ru.cvetkov.moving.objects.utils;

import ru.cvetkov.moving.objects.entities.Geoposition;

public class Event {
    private final EventType type;
    private final Geoposition geoposition;

    public Event(EventType type, Geoposition geoposition) {
        this.type = type;
        this.geoposition = geoposition;
    }

    public EventType getType() {
        return type;
    }

    public Geoposition getGeoposition() {
        return geoposition;
    }
}
