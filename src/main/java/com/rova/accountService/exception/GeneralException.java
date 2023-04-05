package com.rova.accountService.exception;

import java.io.IOException;

public class GeneralException extends RuntimeException {

    public GeneralException(IOException e) {
        super();
    }

    public GeneralException(String message) {
        super(message);
    }
}
