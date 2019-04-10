package com.exchange.dao.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type File not created exception.
 */
public class FileNotCreatedException extends RuntimeException {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new File not created exception.
     *
     * @param message the message
     */
    public FileNotCreatedException(final String message) {
        super(message);
        LOGGER.error(message);
    }
}
