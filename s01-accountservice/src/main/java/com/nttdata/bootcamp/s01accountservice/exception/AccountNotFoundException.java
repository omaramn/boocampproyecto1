package com.nttdata.bootcamp.s01accountservice.exception;

public class AccountNotFoundException extends AccountCreationException {

	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}