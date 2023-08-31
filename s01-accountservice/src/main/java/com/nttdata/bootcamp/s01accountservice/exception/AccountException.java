package com.nttdata.bootcamp.s01accountservice.exception;

public class AccountException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public AccountException(String string) {
		super(string);
	}

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

}
