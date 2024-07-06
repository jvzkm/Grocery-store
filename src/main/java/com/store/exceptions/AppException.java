package com.store.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class AppException extends RuntimeException{
     private int code;
    private String message;
    private HttpStatus status;
}
