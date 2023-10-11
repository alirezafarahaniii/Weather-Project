package com.project.server2.exception;

import com.project.server2.types.CrudType;

public class ResponseException extends RuntimeException {

    private Enum<CrudType> crudType;
    private Object entity;
    private String message;
    private Long errorCode;

    public ResponseException(Enum<CrudType> crudType, Object entity, String message, Long errorCode) {
        super(String.format("%s operation on %s object get error: [%s] and error code: {%s}", crudType, entity, message,errorCode));
        this.crudType = crudType;
        this.entity = entity;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
