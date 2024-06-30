package com.store.exceptions;

import static com.store.util.ExceptionConstants.BANK_NOT_FND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class BankAccountNotFound extends AppException {

    public BankAccountNotFound() {
        super(1001, BANK_NOT_FND, NOT_FOUND);
    }
}
