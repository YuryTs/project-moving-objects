package ru.cvetkov.moving.objects.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class EventPublisher {


    LinkedBlockingDeque<Event> geopositionsQueue = new LinkedBlockingDeque<>();

    public void publish(Event event) {
        geopositionsQueue.add(event);
    }

    public Event take() throws InterruptedException {
        return geopositionsQueue.take();
    }

}
