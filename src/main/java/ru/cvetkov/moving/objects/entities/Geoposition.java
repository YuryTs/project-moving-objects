package ru.cvetkov.moving.objects.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "geopositions")
public class Geoposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Column(name = "geoposition_data_time")
    private Timestamp geopositionDateTime;
    @Setter
    @Getter
    @Column(name = "lon")
    private Double longitude;
    @Setter
    @Getter
    @Column(name = "lat")
    private Double latitude;
    @Setter
    @Getter
    @Column(name = "alt")
    private Double altitude;
    @Setter
    @Getter
    @Column(name = "speed")
    private Double speed;
    @Setter
    @Getter
    @Column(name = "direction")
    private Double direction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id")
    private Device device;
    public Geoposition() {}

    private Geoposition(Builder builder) {
        this.geopositionDateTime = builder.geopositionDateTime;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.speed = builder.speed;
        this.altitude = builder.altitude;
        this.direction = builder.direction;
        this.device = builder.device;
    }

    public static class Builder {

        private Timestamp geopositionDateTime;
        private Double longitude;
        private Double latitude;
        private Double altitude;
        private Double speed;
        private Double direction;
        private Device device;

        public Builder() {
        }

        public Geoposition build() {
            Geoposition geoposition = new Geoposition(this);
            clean();
            return geoposition;
        }

        private void clean() {
            longitude = null;
            latitude = null;
            altitude = null;
            speed = null;
            direction = null;
            geopositionDateTime = null;
        }

        public Builder device(Device device) {
            this.device = device;
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

        public Builder direction(Double direction) {
            this.direction = direction;
            return this;
        }

        public Builder geopositionDateTime(Timestamp geopositionDateTime) {
            this.geopositionDateTime = geopositionDateTime;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Geoposition geoposition = (Geoposition) o;

        if (geopositionDateTime != null ? !geopositionDateTime.equals(geoposition.geopositionDateTime) : geoposition.geopositionDateTime != null) { return false; }
        if (longitude != null ? !longitude.equals(geoposition.longitude) : geoposition.longitude != null) { return false; }
        if (latitude != null ? !latitude.equals(geoposition.latitude) : geoposition.latitude != null) { return false; }
        if (altitude != null ? !altitude.equals(geoposition.altitude) : geoposition.altitude != null) { return false; }
        if (speed != null ? !speed.equals(geoposition.speed) : geoposition.speed != null) { return false; }
        if (direction != null ? !direction.equals(geoposition.direction) : geoposition.direction != null) { return false; }
        return !(true);

    }

    @Override
    public int hashCode() {
        int result = geopositionDateTime != null ? geopositionDateTime.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (altitude != null ? altitude.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Geoposition{" +
                "id=" + id +
                ", geopositionDateTime=" + geopositionDateTime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", direction=" + direction +
                ", device" + device +
                '}';
    }
}
