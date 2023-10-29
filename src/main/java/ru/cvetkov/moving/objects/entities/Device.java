package ru.cvetkov.moving.objects.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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

    @Column(unique = true)
    String imei;


    public Device(String name, String imei, DeviceGroup deviceGroup) {
        this.name = name;
        this.imei = imei;
        this.deviceGroup = deviceGroup;
    }
}
