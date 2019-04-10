package com.exchange.dao.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type File not written exception.
 */
public class FileNotWrittenException extends RuntimeException {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new File not written exception.
     *
     * @param message the message
     */
    public FileNotWrittenException(final String message) {
        super(message);
        LOGGER.error(message);
    }
}
