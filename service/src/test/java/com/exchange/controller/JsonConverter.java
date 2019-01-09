package com.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The type Json converter.
 */
public class JsonConverter {

    /**
     * As json string string.
     *
     * @param obj the obj
     * @return the string
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
