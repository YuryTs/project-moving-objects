package ru.cvetkov.moving.objects.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "device_groups")
@Entity
public class DeviceGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = true, unique = true)
    private String deviceGroupName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "device_groups_id", insertable = false, updatable = false)
    @JsonManagedReference
    private List<Device> deviceList;

    @Override
    public String toString() {
        return "DeviceGroup{" +
                "id=" + id +
                ", deviceGroupName='" + deviceGroupName + '\'' +
                ", deviceList=" + deviceList +
                '}';
    }
}
