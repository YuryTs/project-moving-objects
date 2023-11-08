package ru.cvetkov.moving.objects.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "device_groups_id", insertable = false, updatable = false)
    private DeviceGroup deviceGroup;

    @Column(name = "device_group_id")
    private Long deviceGroupId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private List<Geoposition> geopositions;

    @Column(unique = true)
    String imei;

    public Device(String name, Long deviceGroupId, String imei) {
        this.name = name;
        this.deviceGroupId = deviceGroupId;
        this.imei = imei;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device device)) return false;
        return Objects.equals(imei, device.imei);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imei);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deviceGroup=" + deviceGroup +
                ", deviceGroupId=" + deviceGroupId +
                ", geopositions=" + geopositions +
                ", imei='" + imei + '\'' +
                '}';
    }
}
