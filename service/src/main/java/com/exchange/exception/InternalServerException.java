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
     *
     * @param message the message
     */
    public InternalServerException(final String message) {
        super(message);
        LOGGER.error(message);
    }
}
