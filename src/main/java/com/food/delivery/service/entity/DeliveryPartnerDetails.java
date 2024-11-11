package com.food.delivery.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Long deliveryPartnerId;  // this is referenced from User Service's User details id
    private String currentLocLat;
    private String currentLocLong;
    private boolean isAvailable;

    public double getLatitudeAsDouble() {
        return Double.parseDouble(currentLocLat);
    }

    public double getLongitudeAsDouble() {
        return Double.parseDouble(currentLocLong);
    }

}
