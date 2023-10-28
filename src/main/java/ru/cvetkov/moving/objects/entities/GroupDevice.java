package ru.cvetkov.moving.objects.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GroupDevice {
    private long id;
    private String nameGroupDevice;
    private List<Device> deviceList = new ArrayList<>();
}
