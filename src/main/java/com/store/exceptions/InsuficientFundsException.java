package com.store.exceptions;

import static com.store.util.ExceptionConstants.INSUFFICIENT_FUNDS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InsuficientFundsException extends AppException {
    public InsuficientFundsException() {
        super(5001, INSUFFICIENT_FUNDS, BAD_REQUEST);
    }
}
