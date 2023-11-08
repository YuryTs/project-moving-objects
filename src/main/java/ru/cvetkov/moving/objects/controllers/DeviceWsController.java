package ru.cvetkov.moving.objects.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cvetkov.moving.objects.utils.Event;
import ru.cvetkov.moving.objects.utils.EventPublisher;

import java.lang.invoke.MethodHandles;


@Controller
@RequestMapping("/serverWs")
public class DeviceWsController{
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SimpMessagingTemplate template;
    private final ObjectMapper objectMapper;
    private final EventPublisher eventPublisher;

    public DeviceWsController(SimpMessagingTemplate template, ObjectMapper objectMapper, EventPublisher eventPublisher) {
        this.template = template;
        this.objectMapper = objectMapper;
        this.eventPublisher = eventPublisher;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sendToWebSocket(eventPublisher.take());
                    } catch (InterruptedException e) {
                        LOG.info("Exception while retrieving geoposition from queue");
                    }
                }
            }
        }).start();

    }


    public void sendToWebSocket(Event event) {
        try {
            String json = objectMapper.writeValueAsString(event.getGeoposition());
            template.convertAndSend("/topic/currentGeoposition", json);
        } catch (JsonProcessingException e){
            e.getMessage();
        }
    }
}
