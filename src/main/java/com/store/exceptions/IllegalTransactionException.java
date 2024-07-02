package com.store.exceptions;

import static com.store.util.ExceptionConstants.ILLEGAL_TRANSACTION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class IllegalTransactionException extends AppException {
    public IllegalTransactionException() {
        super(4003, ILLEGAL_TRANSACTION, BAD_REQUEST);
    }
}
