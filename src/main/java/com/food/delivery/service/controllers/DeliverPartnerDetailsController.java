package com.food.delivery.service.controllers;

import com.food.delivery.service.entity.DeliveryPartnerDetails;
import com.food.delivery.service.exception.ErrorDetails;
import com.food.delivery.service.service.DeliveryPartnerDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/delivery-partner")
public class DeliverPartnerDetailsController {

    private final DeliveryPartnerDetailsService deliveryPartnerDetailsService;

    public DeliverPartnerDetailsController(DeliveryPartnerDetailsService deliveryPartnerDetailsService) {
        this.deliveryPartnerDetailsService = deliveryPartnerDetailsService;
    }

    @GetMapping("/{deliveryPartnerId}/location")
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Get delivery partner location using delivery partner Id",
            description = "This endpoint allows to fetch delivery partner location given delivery partner id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "String containing delivery partner's location coordinates"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public String getDeliveryPersonLocation(@PathVariable Long deliveryPartnerId) {
        return deliveryPartnerDetailsService.getDeliveryPersonLocationByPartnerId(deliveryPartnerId);
    }

    @PostMapping
    public ResponseEntity addDeliveryPartnerDetails(@RequestBody DeliveryPartnerDetails deliveryPartnerDetails) {
        deliveryPartnerDetailsService.addDeliveryPartnerDetails(deliveryPartnerDetails);
        return ResponseEntity.ok().build();
    }
}