package com.exchange.dao.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type File not exist exception.
 */
public class FileNotExistException extends RuntimeException {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new File not exist exception.
     *
     * @param message the message
     */
    public FileNotExistException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
