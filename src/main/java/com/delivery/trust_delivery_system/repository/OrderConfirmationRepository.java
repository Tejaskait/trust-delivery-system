package com.delivery.trust_delivery_system.repository;

import com.delivery.trust_delivery_system.model.OrderConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderConfirmationRepository extends JpaRepository<OrderConfirmation, Long> {
    Optional<OrderConfirmation> findByOrderId(Long orderId);
}