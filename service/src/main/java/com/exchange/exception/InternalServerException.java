package com.exchange.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Internal server exception.
 */
public class InternalServerException extends RuntimeException {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new Internal server exception.
     */
    public InternalServerException() {
    }

    /**
     * Instantiates a new Internal server exception.
     *
     * @param msg the msg
     */
    public InternalServerException(String msg) {
        super(msg);
        LOGGER.error(msg);
    }
}
