package com.food.delivery.service.repository;

import com.food.delivery.service.entity.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long> {
}
