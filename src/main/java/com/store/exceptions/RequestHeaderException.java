package com.store.exceptions;

import static com.store.util.ExceptionConstants.TYPE_HEADER;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

public class RequestHeaderException extends AppException {
    public RequestHeaderException() {
        super(5001, TYPE_HEADER, NOT_ACCEPTABLE);
    }
}
