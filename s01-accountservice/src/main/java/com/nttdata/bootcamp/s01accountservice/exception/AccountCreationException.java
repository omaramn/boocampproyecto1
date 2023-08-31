package com.nttdata.bootcamp.s01accountservice.exception;


public class AccountCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountCreationException(String message) {
        super(message);
    }

    public AccountCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}