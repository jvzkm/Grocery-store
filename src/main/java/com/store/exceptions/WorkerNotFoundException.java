package com.store.exceptions;

import static com.store.util.ExceptionConstants.WORK_NOT_FND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class WorkerNotFoundException extends AppException{
    public WorkerNotFoundException() {
        super(3001, WORK_NOT_FND, NOT_FOUND);
    }
}
