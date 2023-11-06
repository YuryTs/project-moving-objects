package ru.cvetkov.moving.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceDtoRq {

    private Long id;

    private String deviceName;

    private Long deviceGroupId;

    private String imei;

}
