package ru.cvetkov.moving.objects.services;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.cvetkov.moving.objects.entities.Geoposition;
import ru.cvetkov.moving.objects.repositories.GeopositionRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeopositionServiceImpl implements GeopositionService{
    private final GeopositionRepository geopositionRepository;
//    private final SimpMessagingTemplate template;

    @Override
    public void saveGeoposition(Geoposition geoposition) {
        geopositionRepository.save(geoposition);
    }

    @Override
    public List<Geoposition> getGeopositionsByDeviceIdAndDateInterval(Long deviceId, Timestamp startDate, Timestamp endDate) {
        return geopositionRepository.getGeopositionsByDeviceIdAndDateInterval(deviceId, startDate, endDate);
    }

//    @Scheduled(fixedDelay = 1000)
//    public void broadcastCurrentGeoposition(){
//        template.convertAndSendToUser("user", "/device",
//                geopositionRepository.findAllWithLatestGeopositionDateTime());//todo необходимо конвертация
//    }

}


