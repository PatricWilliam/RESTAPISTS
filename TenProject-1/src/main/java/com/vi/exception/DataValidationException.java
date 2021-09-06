package com.vi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataValidationException extends RuntimeException {
	private static final long serialVersionUID = 5L;

	public DataValidationException(String message) {
		super(message);
	}

}
