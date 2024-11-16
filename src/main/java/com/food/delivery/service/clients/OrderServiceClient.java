package com.food.delivery.service.clients;

import com.food.delivery.service.models.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderServiceClient {
    private final RestTemplate restTemplate;

    // Inject the base URL of the Order Service from application properties
    @Value("${order-service.url}")
    private String orderServiceUrl;

    public OrderServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order getOrderDetails(Long orderId) {
        String url = orderServiceUrl + "/orders/" + orderId;
        return restTemplate.getForObject(url, Order.class);
    }
}
