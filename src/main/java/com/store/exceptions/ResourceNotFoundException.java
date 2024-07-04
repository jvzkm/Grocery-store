package com.store.exceptions;

import static com.store.util.ExceptionConstants.RESOURCE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundException extends AppException{
    public ResourceNotFoundException() {
        super(3003, RESOURCE_NOT_FOUND, NOT_FOUND);
    }
}
