package ru.cvetkov.moving.objects.entities;

import java.time.LocalDateTime;

public class Geoposition {
    private LocalDateTime trackDateTime;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Double speed;
    private Integer direction;
    private String imei;
    public Geoposition() {}

    public Geoposition(Geoposition geoposition) {
        this.trackDateTime  = geoposition.trackDateTime;
        this.longitude      = geoposition.longitude;
        this.latitude       = geoposition.latitude;
        this.altitude       = geoposition.altitude;
        this.speed          = geoposition.speed;
        this.direction      = geoposition.direction;
    }

    public Geoposition(LocalDateTime trackDateTime, Double lon, Double lat, Double alt, Double sp, Integer dir) {
        this.trackDateTime  = trackDateTime;
        this.longitude      = lon;
        this.latitude       = lat;
        this.altitude       = alt;
        this.speed          = sp;
        this.direction      = dir;
    }

    private Geoposition(Builder builder) {
        this.trackDateTime = builder.trackDateTime;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.speed = builder.speed;
        this.altitude = builder.altitude;
        this.direction = builder.direction;
        this.imei = builder.imei;
    }

    public static class Builder {

        private LocalDateTime trackDateTime;
        private Double longitude;
        private Double latitude;
        private Double altitude;
        private Double speed;
        private Integer direction;
        private String imei;

        public Builder() {
        }

        public Geoposition build() {
            Geoposition geoposition = new Geoposition(this);
            clean();
            return geoposition;
        }

        private void clean() {
            imei = null;
            longitude = null;
            latitude = null;
            altitude = null;
            speed = null;
            direction = null;
            trackDateTime = null;
        }

        public Builder vehicleId(String imei) {
            this.imei = imei;
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

    public String getDeviceImei() {
        return imei;
    }

    public void setDeviceImei(String imei) {
        this.imei = imei;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Geoposition geoposition = (Geoposition) o;

        if (trackDateTime != null ? !trackDateTime.equals(geoposition.trackDateTime) : geoposition.trackDateTime != null) { return false; }
        if (longitude != null ? !longitude.equals(geoposition.longitude) : geoposition.longitude != null) { return false; }
        if (latitude != null ? !latitude.equals(geoposition.latitude) : geoposition.latitude != null) { return false; }
        if (altitude != null ? !altitude.equals(geoposition.altitude) : geoposition.altitude != null) { return false; }
        if (speed != null ? !speed.equals(geoposition.speed) : geoposition.speed != null) { return false; }
        if (direction != null ? !direction.equals(geoposition.direction) : geoposition.direction != null) { return false; }
        return !(imei != null ? !imei.equals(geoposition.imei) : geoposition.imei != null);

    }

    @Override
    public int hashCode() {
        int result = trackDateTime != null ? trackDateTime.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (altitude != null ? altitude.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (imei != null ? imei.hashCode() : 0);
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
