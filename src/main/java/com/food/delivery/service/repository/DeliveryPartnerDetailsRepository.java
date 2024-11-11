package com.food.delivery.service.repository;

import com.food.delivery.service.entity.DeliveryPartnerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryPartnerDetailsRepository extends JpaRepository<DeliveryPartnerDetails, Long> {

    public DeliveryPartnerDetails findByDeliveryPartnerId(Long deliveryPartnerId);

    public List<DeliveryPartnerDetails> findByIsAvailable(boolean isAvailable);
}
