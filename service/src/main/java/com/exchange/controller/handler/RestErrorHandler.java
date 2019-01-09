package com.exchange.controller.handler;

import com.exchange.controller.response.Response;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

/**
 * The type Rest error handler.
 */
@ControllerAdvice
public class RestErrorHandler {

    /**
     * Handle data access exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Response handleDataAccessException(DataAccessException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle validation exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Response handleValidationException(ValidationException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle internal server exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Response handleInternalServerException(InternalServerException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

}
