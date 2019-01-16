package com.exchange.controller.response;

import java.util.List;

/**
 * The type Response.
 */
public class Response {

    private List<String> message;

    /**
     * Instantiates a new Response.
     */
    public Response() {
    }

    /**
     * Instantiates a new Response.
     *
     * @param message the message
     */
    public Response(List<String> message) {
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public List<String> getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(List<String> message) {
        this.message = message;
    }
}
