package com.project.taskObjectMapper.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.taskObjectMapper.entity.Customer;



public class CustomerJson {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Customer customer){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Customer fromJson(String json){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, Customer.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
