package ru.cvetkov.moving.objects.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "device_groups_id")
    private DeviceGroup deviceGroup;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private List<Geoposition> geopositions;

    @Column(unique = true)
    String imei;


    public Device(String name, String imei, DeviceGroup deviceGroup) {
        this.name = name;
        this.imei = imei;
        this.deviceGroup = deviceGroup;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deviceGroup=" + (deviceGroup != null ? deviceGroup.getId() : "null") +
                ", imei='" + imei + '\'' +
                '}';
    }
}
