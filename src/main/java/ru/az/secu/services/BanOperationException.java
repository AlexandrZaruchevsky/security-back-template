package ru.az.secu.services;

import org.springframework.http.HttpStatus;

public class BanOperationException extends MyException {

    public BanOperationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public BanOperationException(String message) {
        super(message);
    }

}
