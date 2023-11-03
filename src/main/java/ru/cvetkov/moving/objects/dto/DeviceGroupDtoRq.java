package ru.cvetkov.moving.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.cvetkov.moving.objects.entities.Device;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceGroupDtoRq {
    private Long id;

    private String deviceGroupName;

    private List<Long> deviceIds;
}
