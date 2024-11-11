package com.food.delivery.service.service;

import com.food.delivery.service.clients.OrderServiceClient;
import com.food.delivery.service.entity.DeliveryDetails;
import com.food.delivery.service.entity.DeliveryPartnerDetails;
import com.food.delivery.service.exception.FoodDeliveryException;
import com.food.delivery.service.models.Address;
import com.food.delivery.service.models.Delivery;
import com.food.delivery.service.models.DeliveryStatus;
import com.food.delivery.service.models.Order;
import com.food.delivery.service.repository.DeliveryDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeliveryService {

    private final DeliveryDetailsRepository deliveryDetailsRepository;
    private final OrderServiceClient orderServiceClient;
    private final DeliveryPartnerDetailsService deliveryPartnerDetailsService;

    public DeliveryService(DeliveryDetailsRepository deliveryDetailsRepository, OrderServiceClient orderServiceClient, DeliveryPartnerDetailsService deliveryPartnerDetailsService) {
        this.deliveryDetailsRepository = deliveryDetailsRepository;
        this.orderServiceClient = orderServiceClient;
        this.deliveryPartnerDetailsService = deliveryPartnerDetailsService;
    }

    /**
     * 1. Fetch order details from order service based on order details
     * 2. Fetch the closest available delivery partner to the given order address
     * 3. Assign delivery partner and save delivery details in ASSIGNED state
     * 4. Update delivery partner availability as false
     * 5. Convert to response object to be exposed via controller (as per contracts)
     * **/
    @Transactional
    public Delivery assignDeliveryPerson(Long orderId) {

        Order order = orderServiceClient.getOrderDetails(orderId);
        if(order == null) {
            throw new FoodDeliveryException("No such order found in order service with id"+ orderId, HttpStatus.BAD_REQUEST);
        }
        DeliveryPartnerDetails assignedPartner =
                deliveryPartnerDetailsService.getClosestAvailableDeliveryPartner(order.getAddress());
        DeliveryDetails deliveryDetails = deliveryDetailsRepository.save(createDeliveryPersistenceObject(orderId, assignedPartner.getDeliveryPartnerId()));
        deliveryPartnerDetailsService.updateDeliveryPartnerAvailability(assignedPartner.getDeliveryPartnerId());

        return convertToResponse(order.getAddress(), deliveryDetails);
    }

    private Delivery convertToResponse(Address address, DeliveryDetails deliveryDetails) {
        return Delivery.builder()
                .orderId(deliveryDetails.getOrderId())
                .deliveryPartnerId(deliveryDetails.getDeliveryPartnerId())
                .status(deliveryDetails.getStatus().name())
                .deliveredAt(deliveryDetails.getDeliveredAt())
                .deliveryAddress(address)
                .build();
    }
    private DeliveryDetails createDeliveryPersistenceObject(Long orderId, Long deliveryPartnerId) {
        return DeliveryDetails.builder()
                .orderId(orderId)
                .status(DeliveryStatus.ASSIGNED)
                .deliveryPartnerId(deliveryPartnerId)
                .createdAt(new Date())
                .pickedUpAt(new Date())
                .build();
    }

    public Delivery updateDeliveryStatus(Long orderId, DeliveryStatus status) {
        return null;
    }

    public String getDeliveryPersonLocationByPartnerId(Long deliveryPartnerId) {
        return null;
    }
}