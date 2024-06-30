package com.store.exceptions;

import static com.store.util.ExceptionConstants.MALFORMED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MalformedJsonException extends AppException {

    public MalformedJsonException() {
        super(5003, MALFORMED, BAD_REQUEST);
    }
}
