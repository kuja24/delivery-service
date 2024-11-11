package com.food.delivery.service.controllers;

import com.food.delivery.service.exception.ErrorDetails;
import com.food.delivery.service.models.Delivery;
import com.food.delivery.service.models.DeliveryStatus;
import com.food.delivery.service.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/{orderId}/assign")
    public Delivery assignDeliveryPerson(@PathParam("orderId") Long orderId) {
        return deliveryService.assignDeliveryPerson(orderId);
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Update status of delivery",
            description = "This endpoint allows to update the status of delivery given the order Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "String containing delivery partner's location coordinates"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public Delivery updateDeliveryStatus(@PathVariable Long orderId, @RequestParam DeliveryStatus status) {
        return deliveryService.updateDeliveryStatus(orderId, status);
    }


}

