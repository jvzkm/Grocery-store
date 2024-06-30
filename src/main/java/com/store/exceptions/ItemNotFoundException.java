package com.store.exceptions;

import static com.store.util.ExceptionConstants.ITEM_NOT_FND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ItemNotFoundException extends AppException{
    public ItemNotFoundException() {
        super(2001, ITEM_NOT_FND, NOT_FOUND);
    }
}
