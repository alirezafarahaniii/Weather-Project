package com.project.server2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ObjectCreationException extends RuntimeException {
	private String scheduleName;
	private String message;

	public ObjectCreationException(String scheduleName, String message) {
		super(String.format("Failed to create Schedule [%s] : '%s'", scheduleName, message));
		this.scheduleName = scheduleName;
		this.message = message;
	}

	public ObjectCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}