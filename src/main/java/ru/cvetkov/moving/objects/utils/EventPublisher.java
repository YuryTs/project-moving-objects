package ru.cvetkov.moving.objects.utils;

import org.springframework.stereotype.Component;
import ru.cvetkov.moving.objects.entities.Geoposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class EventPublisher {

    Map<EventType, List<GeopositionListener>> subscribers = new ConcurrentHashMap<>();

    LinkedBlockingDeque<Event> geopositionsQueue = new LinkedBlockingDeque<>();

    public void publish(Event event) {
        geopositionsQueue.add(event);
    }

    public void notifySubscibers(Event event) {
        if (subscribers.containsKey(event.getType())) {
            subscribers.get(event.getType()).forEach(subscriber -> {
                try {
                    subscriber.accept(event);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        notifySubscibers(geopositionsQueue.take());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    public void subscribe(EventType type, GeopositionListener listener) {
        subscribers.computeIfAbsent(type, key -> new ArrayList<>()).add(listener);
    }

}
