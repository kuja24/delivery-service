package com.food.delivery.service.entity;

import com.food.delivery.service.models.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "DELIVERY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    private Long orderId;

    private Long deliveryPartnerId;

    private DeliveryStatus status;

    private Date createdAt;

    private Date pickedUpAt;
    private Date deliveredAt;

}
