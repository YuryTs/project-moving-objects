package ru.cvetkov.moving.objects.dto;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceGroupDtoRs {
    private long id;

    private String deviceGroupName;

    private List<Pair<Long, String>> devices; //todo just for card
}
