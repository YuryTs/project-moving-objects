package ru.cvetkov.moving.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {
    private String code;
    private String message;
    private LocalDateTime date;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
