package com.project.taskObjectMapper.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.taskObjectMapper.entity.Product;

public class ProductJson {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Product product){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Product fromJson(String json){
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, Product.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
