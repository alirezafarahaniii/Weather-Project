package com.project.server2.error;


import com.project.server2.exception.ResponseException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            NoSuchElementException.class,
            ResponseException.class
    })

    public final ResponseEntity<Object> restHandleException(Exception ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof NoSuchElementException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleNoSuchElementException((NoSuchElementException) ex, headers, status, request);
        } else if (ex instanceof ResponseException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleResponseException((ResponseException) ex, headers, status, request);
        } else {
            // Unknown exception, typically a wrapper with a common MVC exception as cause
            // (since @ExceptionHandler type declarations also match first-level causes):
            // We only deal with top-level MVC exceptions here, so let's rethrow the given
            // exception for further processing through the HandlerExceptionResolver chain.
            throw ex;
        }
    }

    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = "No Value Present!!";

        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    protected ResponseEntity<Object> handleResponseException(ResponseException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = "Response Exception";

        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    //other exception handlers below

}
