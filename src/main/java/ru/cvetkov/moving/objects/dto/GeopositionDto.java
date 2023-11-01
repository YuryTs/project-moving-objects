package ru.cvetkov.moving.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeopositionDto {
    private Timestamp geopositionDateTime;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Double speed;
    private Double direction;
}
