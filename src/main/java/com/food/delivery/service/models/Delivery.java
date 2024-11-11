package com.food.delivery.service.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Delivery {
    private Long orderId;
    private Long deliveryPartnerId;
    private String status;
    private Address deliveryAddress;
    private Date pickedUpAt;
    private Date deliveredAt;
}
