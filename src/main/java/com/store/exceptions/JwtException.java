package com.store.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class JwtException extends AppException {

    public JwtException(String message) {
        super(8009, message, FORBIDDEN);
    }
}
