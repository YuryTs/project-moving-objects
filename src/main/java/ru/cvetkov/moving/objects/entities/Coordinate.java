package ru.cvetkov.moving.objects.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Coordinate {
    private Double lat;
    private Double lng;
}
