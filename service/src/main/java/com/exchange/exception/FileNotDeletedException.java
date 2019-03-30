package com.exchange.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type File not deleted exception.
 */
public class FileNotDeletedException extends RuntimeException {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new File not deleted exception.
     */
    public FileNotDeletedException() {
    }

    /**
     * Instantiates a new File not deleted exception.
     *
     * @param message the message
     */
    public FileNotDeletedException(String message) {
        super(message);
    }
}
