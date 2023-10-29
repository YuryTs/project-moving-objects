package ru.cvetkov.moving.objects.entities;

import java.time.LocalDateTime;

public class Track {
    private LocalDateTime trackDateTime;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Double speed;
    private Integer direction;
    private Long deviceId;
    public Track() {}

    public Track(Track track) {
        this.trackDateTime  = track.trackDateTime;
        this.longitude      = track.longitude;
        this.latitude       = track.latitude;
        this.altitude       = track.altitude;
        this.speed          = track.speed;
        this.direction      = track.direction;
    }

    public Track(LocalDateTime trackDateTime, Double lon, Double lat, Double alt, Double sp, Integer dir) {
        this.trackDateTime  = trackDateTime;
        this.longitude      = lon;
        this.latitude       = lat;
        this.altitude       = alt;
        this.speed          = sp;
        this.direction      = dir;
    }

    private Track(Builder builder) {
        this.trackDateTime = builder.trackDateTime;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.speed = builder.speed;
        this.altitude = builder.altitude;
        this.direction = builder.direction;
        this.deviceId = builder.deviceId;
    }

    public static class Builder {

        private LocalDateTime trackDateTime;
        private Double longitude;
        private Double latitude;
        private Double altitude;
        private Double speed;
        private Integer direction;
        private Long deviceId;
        private Long id;

        public Builder() {
        }

        public Track build() {
            Track track = new Track(this);
            clean();
            return track;
        }

        private void clean() {
            deviceId = null;
            id = null;
            longitude = null;
            latitude = null;
            altitude = null;
            speed = null;
            direction = null;
            trackDateTime = null;
        }

        public Builder vehicleId(Long vehicleId) {
            this.deviceId = vehicleId;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder altitude(Double altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder speed(Double speed) {
            this.speed = speed;
            return this;
        }

        public Builder direction(Integer direction) {
            this.direction = direction;
            return this;
        }

        public Builder trackDateTime(LocalDateTime trackDateTime) {
            this.trackDateTime = trackDateTime;
            return this;
        }

    }

    public LocalDateTime getTrackDateTime() {
        return trackDateTime;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Long getVehicleId() {
        return deviceId;
    }

    public void setVehicleId(Long vehicleId) {
        this.deviceId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Track track = (Track) o;

        if (trackDateTime != null ? !trackDateTime.equals(track.trackDateTime) : track.trackDateTime != null) { return false; }
        if (longitude != null ? !longitude.equals(track.longitude) : track.longitude != null) { return false; }
        if (latitude != null ? !latitude.equals(track.latitude) : track.latitude != null) { return false; }
        if (altitude != null ? !altitude.equals(track.altitude) : track.altitude != null) { return false; }
        if (speed != null ? !speed.equals(track.speed) : track.speed != null) { return false; }
        if (direction != null ? !direction.equals(track.direction) : track.direction != null) { return false; }
        return !(deviceId != null ? !deviceId.equals(track.deviceId) : track.deviceId != null);

    }

    @Override
    public int hashCode() {
        int result = trackDateTime != null ? trackDateTime.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (altitude != null ? altitude.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackDateTime=" + trackDateTime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
