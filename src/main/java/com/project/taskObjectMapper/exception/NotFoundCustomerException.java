package com.project.taskObjectMapper.exception;

public class NotFoundCustomerException extends RuntimeException {
    public NotFoundCustomerException(String message) {
        super(message);
    }
}
