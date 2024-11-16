package com.food.delivery.service.controllers;

import com.food.delivery.service.exception.ErrorDetails;
import com.food.delivery.service.models.Delivery;
import com.food.delivery.service.models.DeliveryStatus;
import com.food.delivery.service.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/delivery")
@SecurityRequirement(name = "bearerAuth")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/{deliveryId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Get Delivery for the given delivery id",
            description = "This endpoint allows fetch delivery details by delivery id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery details"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public Delivery getDeliveryByDeliveryId(@PathParam("deliveryId") Long deliveryId) {
        return deliveryService.getDeliveryByDeliveryId(deliveryId);
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Get Delivery for the given order id",
            description = "This endpoint allows fetch delivery details by order id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery details"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public Delivery getDeliveryByOrderId(@PathParam("orderId") Long orderId) {
        return deliveryService.getDeliveryByOrderId(orderId);
    }

    @PostMapping("/{orderId}/assign")
    @PreAuthorize("hasAnyAuthority('ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Assign delivery partner for the given order id",
            description = "This endpoint allows to calculate and assign available and closes delivery partner for an order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Assigned and created delivery"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public Delivery assignDeliveryPerson(@PathParam("orderId") Long orderId) {
        return deliveryService.assignDeliveryPerson(orderId);
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Update status of delivery",
            description = "This endpoint allows to update the status of delivery given the order Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "updated delivery details"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public Delivery updateDeliveryStatus(@PathVariable Long orderId, @RequestParam DeliveryStatus status) {
        return deliveryService.updateDeliveryStatus(orderId, status);
    }


}

