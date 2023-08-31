package com.nttdata.bootcamp.s01accountservice.exception;

public class DuplicateAccountException extends AccountException {

	private static final long serialVersionUID = 1L;

	public DuplicateAccountException(String message) {
        super(message);
    }

    public DuplicateAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}