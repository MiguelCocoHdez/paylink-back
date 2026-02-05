package com.paylink.auth.domain.exception;

public class InvalidEmailOrUsernameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidEmailOrUsernameException(String message) {
		super(message);
	}
	
}
