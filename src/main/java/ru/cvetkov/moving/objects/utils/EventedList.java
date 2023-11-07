package ru.cvetkov.moving.objects.utils;

import ru.cvetkov.moving.objects.entities.Geoposition;

import java.util.ArrayList;
import java.util.List;

public class EventedList {
    private List<Geoposition> geopositionList = new ArrayList<>();

    private EventPublisher publisher;

    public void setPublisher(EventPublisher publisher) {
        this.publisher = publisher;
    }

    public void add(Geoposition geoposition){
        geopositionList.add(geoposition);
        publishEvent(EventType.ADD, geoposition);
    }

    private void publishEvent(EventType type, Geoposition geoposition) {
        if (publisher == null) {
            return;
        }

        publisher.publish(new Event(type, geoposition));
    }
}
