package com.project.server2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class GetObjectException extends RuntimeException {
	private String param;
	private String message;

	public GetObjectException(String param, String message) {
		super(String.format("Can not get object by id = %s : '%s'", param,message));
		this.param = param;
		this.message = message;
	}

	public GetObjectException(String message, Throwable cause) {
		super(message, cause);
	}
}