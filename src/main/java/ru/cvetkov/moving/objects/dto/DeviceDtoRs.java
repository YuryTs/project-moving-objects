package ru.cvetkov.moving.objects.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceDtoRs {

    private long id;

    private String deviceName;

    private long deviceGroupId;

    private String deviceGroupName;
}
