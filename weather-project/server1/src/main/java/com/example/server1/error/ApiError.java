package com.example.server1.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private Long errorCode;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
        this.errorCode = Long.valueOf(ex.getLocalizedMessage().
                substring(ex.getLocalizedMessage().indexOf("{") + 1, ex.getLocalizedMessage().indexOf("}")));    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.errorCode = Long.valueOf(ex.getLocalizedMessage().
                substring(ex.getLocalizedMessage().indexOf("{") + 1, ex.getLocalizedMessage().indexOf("}")));
    }
}