package com.food.delivery.service.exception;

import org.springframework.http.HttpStatus;

public class FoodDeliveryException extends RuntimeException{

    private final HttpStatus status;

    public FoodDeliveryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
