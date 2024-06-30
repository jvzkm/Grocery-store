package com.store.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class AppException extends RuntimeException{
    private int code;
    private String message;
    private HttpStatus status;
}
