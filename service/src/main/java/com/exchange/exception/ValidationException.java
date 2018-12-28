package com.exchange.exception;

/**
 * Created by Uladzislau Hrytsau on 7.12.18
 */
public class ValidationException extends RuntimeException {

    public ValidationException() {
    }

    public ValidationException(String msg) {
        super(msg);
    }
}
