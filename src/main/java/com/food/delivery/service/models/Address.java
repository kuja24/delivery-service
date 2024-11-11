package com.food.delivery.service.models;

import lombok.Data;

@Data
public class Address {
    private String addressLine;
    private String city;

    private String state;

    private String zip;

    private String latitude;
    private String longitude;
}
