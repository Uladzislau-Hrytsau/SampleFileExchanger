package com.exchange.controller.handler;

import com.exchange.controller.response.Response;
import com.exchange.dao.exception.FileNotCreatedException;
import com.exchange.dao.exception.FileNotDeletedException;
import com.exchange.dao.exception.FileNotExistException;
import com.exchange.dao.exception.FileNotWrittenException;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.Collections;

/**
 * The type Rest error handler.
 */
@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle validation exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleValidationException(final ValidationException ex) {
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
    public Response handleInternalServerException(final InternalServerException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle file not deleted exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(FileNotDeletedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleFileNotDeletedException(final FileNotDeletedException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle file not created exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(FileNotCreatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleFileNotCreatedException(final FileNotCreatedException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle file not written exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(FileNotWrittenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleFileNotWrittenException(final FileNotWrittenException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle file not exist exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(FileNotExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleFileNotExistException(final FileNotExistException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    /**
     * Handle data access exception response.
     *
     * @param ex the ex
     * @return the response
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleDataAccessException(final DataAccessException ex) {
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
    public Response handleFileNotFoundException(final FileNotFoundException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

}
