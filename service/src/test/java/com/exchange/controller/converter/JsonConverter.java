package com.exchange.controller.converter;

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
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
