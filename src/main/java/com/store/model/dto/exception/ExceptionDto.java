package com.store.model.dto.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionDto {
    private int code;
    private String message;
    private HttpStatus status;
}
