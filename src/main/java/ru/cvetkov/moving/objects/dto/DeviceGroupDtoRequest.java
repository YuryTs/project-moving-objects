package ru.cvetkov.moving.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceGroupDtoRequest {
    private String name;
    List<Long> deviceIds;
}
