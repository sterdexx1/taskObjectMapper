package com.project.taskObjectMapper.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.taskObjectMapper.entity.Order;

public class OrderJson {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Order order){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Order fromJson(String json){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
