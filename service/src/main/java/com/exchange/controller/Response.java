package com.exchange.controller;

import java.util.List;

public class Response {

    private List<String> message;

    public Response() {
    }

    public Response(List<String> message) {
        this.message = message;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
