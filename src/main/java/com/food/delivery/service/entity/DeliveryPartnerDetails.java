package com.food.delivery.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "DELIVERY_PARTNER_DETAILS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerDetails {

    @Id
    @NotNull
    private Long deliveryPartnerId;  // this is referenced from User Service's User details id

    @NotNull
    @NotEmpty
    private String currentLocLat;

    @NotNull
    @NotEmpty
    private String currentLocLong;

    @NotNull
    private boolean isAvailable;

    public double getLatitudeAsDouble() {
        return Double.parseDouble(currentLocLat);
    }

    public double getLongitudeAsDouble() {
        return Double.parseDouble(currentLocLong);
    }

}
