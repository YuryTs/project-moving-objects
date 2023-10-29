//package ru.cvetkov.moving.objects.repositories;
//
//import org.apache.commons.lang3.tuple.Pair;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import ru.cvetkov.moving.objects.entities.Track;
//
//import java.util.List;
//@Repository
//public interface TrackRepository extends JpaRepository<Track, Long> {
//    Track getLatestTrack(long vehicleId);
//
//    List<Track> getLatest(List<Long> vehicleIds);
//
//    void batchSaveTracks(List<Track> tracks);
//
//    void batch(List<String> statements);
//
//    void createTracksTable(long vehicleId);
//
//    boolean doesTableExist(long vehicleId);
//
//    void updateLatest(List<Track> tracks);
//
//    void removeOldTracks();
//}
