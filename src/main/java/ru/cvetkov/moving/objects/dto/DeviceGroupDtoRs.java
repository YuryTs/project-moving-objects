package ru.cvetkov.moving.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceGroupDtoRs {
    private Long id;

    private String deviceGroupName;

    private List<Pair<Long, String>> devices;
}
