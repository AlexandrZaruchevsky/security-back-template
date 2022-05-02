package ru.az.secu.services;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MyException extends Exception {

    @Getter
    private String message;
    @Getter
    private HttpStatus httpStatus;

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
        this.message = message;
    }

    public MyException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.httpStatus =httpStatus;
    }


}
