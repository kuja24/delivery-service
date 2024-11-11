package com.food.delivery.service.models;

public enum DeliveryStatus {
    ASSIGNED,          // Delivery person assigned to the order
    PICKED_UP,         // Order picked up by the delivery person
    IN_TRANSIT,        // Order is on the way to the customer
    DELIVERED,         // Order delivered to the customer
    CANCELED,          // Delivery was canceled
    FAILED_DELIVERY    // Delivery attempt failed (e.g., customer not available)
}
