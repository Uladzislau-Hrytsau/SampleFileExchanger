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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

/**
 * The type Rest error handler.
 */
@ControllerAdvice
@ResponseBody
public class RestErrorHandler {

    /**
     * Handle data access exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleDataAccessException(DataAccessException ex) {
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
    public Response handleValidationException(ValidationException ex) {
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
    public Response handleInternalServerException(InternalServerException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle file not found exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleFileNotFoundException(FileNotFoundException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle file not found exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleFileNotFoundException(IOException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(Exception ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }


}
