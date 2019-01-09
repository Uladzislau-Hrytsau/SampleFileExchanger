package com.exchange.exception;

/**
 * Created by Uladzislau Hrytsau on 7.12.18
 */
public class ValidationException extends RuntimeException {

    /**
     * Instantiates a new Validation exception.
     */
    public ValidationException() {
    }

    /**
     * Instantiates a new Validation exception.
     *
     * @param msg the msg
     */
    public ValidationException(String msg) {
        super(msg);
    }
}
