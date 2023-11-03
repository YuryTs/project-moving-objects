package ru.cvetkov.moving.objects.exeptions;

import org.springframework.http.HttpStatus;
import ru.cvetkov.moving.objects.dto.ErrorDto;

public class ValidationException extends RuntimeException{
    public ErrorDto errorDto;

    public ValidationException(ErrorDto dto) {
        super("message");
        this.errorDto = dto;
    }
}
