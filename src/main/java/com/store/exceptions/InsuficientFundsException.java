package com.store.exceptions;

import static com.store.util.ExceptionConstants.INSUFFICIENT_FUNDS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class InsuficientFundsException extends AppException {
    public InsuficientFundsException() {
        super(5001, INSUFFICIENT_FUNDS, FORBIDDEN);
    }
}
