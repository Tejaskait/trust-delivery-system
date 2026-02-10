package com.delivery.trust_delivery_system.repository;

import com.delivery.trust_delivery_system.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Add this line to fix the error in DeliveryController
    List<Order> findByStatus(Order.OrderStatus status);
    
    List<Order> findByCustomerId(Long customerId);
}