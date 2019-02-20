package com.exchange.exception;

/**
 * The type Validation exception.
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
