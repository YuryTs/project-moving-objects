package ru.cvetkov.moving.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceDtoRq {

    private long id;

    private String deviceName;

    private String deviceGroupName;

}
