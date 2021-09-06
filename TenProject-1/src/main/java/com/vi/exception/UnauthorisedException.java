package com.vi.exception;

public class UnauthorisedException extends RuntimeException{
	
	private static final long serialVersionUID = 2L;

	public UnauthorisedException(String message) {
		super(message);
	}
}
