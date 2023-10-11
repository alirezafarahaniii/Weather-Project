package com.example.server1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenResourceUsageException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ForbiddenResourceUsageException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("you can not access to %s with this %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ForbiddenResourceUsageException(String message, Throwable cause) {
        super(message, cause);
    }
}
