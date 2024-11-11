package com.food.delivery.service.models;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private Address address;
    private String customerName;
    private String orderTotal;
}
