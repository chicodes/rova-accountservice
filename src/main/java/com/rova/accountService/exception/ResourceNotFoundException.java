package com.rova.accountService.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5067211343246617692L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
