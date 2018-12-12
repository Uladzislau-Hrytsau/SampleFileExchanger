package com.exchange.controller;

import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Response handleDataAccessException(DataAccessException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Response handleValidationException(ValidationException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Response handleInternalServerException(InternalServerException ex) {
        Response response = new Response();
        response.setMessage(Collections.singletonList(ex.getMessage()));
        return response;
    }

}
