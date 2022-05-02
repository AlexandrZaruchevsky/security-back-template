package ru.az.secu.services;

import org.springframework.http.HttpStatus;

public class NotFoundException extends MyException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

}
