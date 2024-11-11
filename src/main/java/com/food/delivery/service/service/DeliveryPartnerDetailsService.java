package com.food.delivery.service.service;

import com.food.delivery.service.entity.DeliveryPartnerDetails;
import com.food.delivery.service.exception.FoodDeliveryException;
import com.food.delivery.service.models.Address;
import com.food.delivery.service.repository.DeliveryPartnerDetailsRepository;
import com.food.delivery.service.util.DistanceCalculator;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryPartnerDetailsService {
    private final DeliveryPartnerDetailsRepository repository;

    public DeliveryPartnerDetailsService(DeliveryPartnerDetailsRepository repository) {
        this.repository = repository;
    }

    public String getDeliveryPersonLocationByPartnerId(Long deliveryPartnerId) {
        DeliveryPartnerDetails details =  repository.findByDeliveryPartnerId(deliveryPartnerId);
        if(details == null) {
            throw new FoodDeliveryException("Delivery partner not found with id: " + deliveryPartnerId, HttpStatus.NOT_FOUND);
        }
        return details.getCurrentLocLat() + details.getCurrentLocLong();
    }

    public DeliveryPartnerDetails getClosestAvailableDeliveryPartner(Address address) {
        List<DeliveryPartnerDetails> availableDeliveryPartners = repository.findByIsAvailable(true);
        double addressLat = Double.parseDouble(address.getLatitude());
        double addressLon = Double.parseDouble(address.getLongitude());

        DeliveryPartnerDetails closestPartner = null;
        double closestDistance = Double.MAX_VALUE;

        for (DeliveryPartnerDetails partner : availableDeliveryPartners) {
            double distance = DistanceCalculator.calculateDistance(
                    addressLat, addressLon,
                    partner.getLatitudeAsDouble(),
                    partner.getLongitudeAsDouble()
            );

            if (distance < closestDistance) {
                closestDistance = distance;
                closestPartner = partner;
            }
        }

        return closestPartner;
    }

    @Transactional
    public void updateDeliveryPartnerAvailability(Long deliveryPartnerId) {
        // Retrieve the delivery partner by ID
        DeliveryPartnerDetails deliveryPartner = repository.findById(deliveryPartnerId)
                .orElseThrow(() -> new FoodDeliveryException("Delivery partner not found with ID: " + deliveryPartnerId, HttpStatus.BAD_REQUEST));

        // Toggle the isAvailable field
        deliveryPartner.setAvailable(!deliveryPartner.isAvailable());

        // Save the updated delivery partner
        repository.save(deliveryPartner);
    }

    public void addDeliveryPartnerDetails(DeliveryPartnerDetails deliveryPartnerDetails) {
        DeliveryPartnerDetails details = repository.findByDeliveryPartnerId(deliveryPartnerDetails.getDeliveryPartnerId());
        if(details != null) {
            throw new FoodDeliveryException("A delivery partner with this id already Exists", HttpStatus.BAD_REQUEST);
        }
        try {
            repository.save(deliveryPartnerDetails);
        } catch (Exception e ) {
            throw new FoodDeliveryException("Unable to insert delivery partner details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
