package ru.cvetkov.moving.objects.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.utils.Event;
import ru.cvetkov.moving.objects.utils.GeopositionListener;


@Controller
@RequestMapping("/serverWs")
public class DeviceWsController implements GeopositionListener {
    private final SimpMessagingTemplate template;
    private final ObjectMapper objectMapper;

    public DeviceWsController(SimpMessagingTemplate template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }


    @Override
    public void accept(Event event) throws InterruptedException {
        try {
            String json = objectMapper.writeValueAsString(event.getGeoposition());
            template.convertAndSend("/topic/currentGeoposition", json);
        } catch (JsonProcessingException e){ //TODO rewrite before finish
            e.getMessage();
        }
    }
}
