package com.store.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AuthException extends AppException{
    public AuthException(String message) {
        super(8804, message, FORBIDDEN);
    }
}
