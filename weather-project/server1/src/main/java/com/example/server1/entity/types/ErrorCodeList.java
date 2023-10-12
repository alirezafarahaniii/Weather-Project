package com.example.server1.entity.types;


public enum ErrorCodeList {

    OBJECT_CAN_NOT_CREATE(1L),
    OBJECT_CAN_NOT_READ(2L),
    OBJECT_CAN_NOT_UPDATE(3L),
    OBJECT_CAN_NOT_DELETE(4L);
    private Long code;

    ErrorCodeList(Long code) {
        this.setCode(code);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
    }
