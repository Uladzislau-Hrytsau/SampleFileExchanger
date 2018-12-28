package com.exchange.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Uladzislau Hrytsau on 8.12.18
 */
public class InternalServerException extends RuntimeException {

    private static final Logger LOGGER = LogManager.getLogger();

    public InternalServerException() {
    }

    public InternalServerException(String msg) {
        super(msg);
        LOGGER.error(msg);
    }
}
