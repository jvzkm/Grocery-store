package com.store.exceptions;

import static com.store.util.ExceptionConstants.ILLEGAL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class IllegalSaleException extends AppException {
    public IllegalSaleException() {
        super(2003, ILLEGAL, BAD_REQUEST);
    }
}
