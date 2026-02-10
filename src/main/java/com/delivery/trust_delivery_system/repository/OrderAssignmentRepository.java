package com.delivery.trust_delivery_system.repository;

import com.delivery.trust_delivery_system.model.OrderAssignment;
import com.delivery.trust_delivery_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional; // Add this import

@Repository
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long> {
    
    List<OrderAssignment> findByDeliveryAgentAndStatusNot(User agent, OrderAssignment.AssignmentStatus status);

    // ADD THIS LINE BELOW to fix the "undefined" error in the Controller
    Optional<OrderAssignment> findByOrderId(Long orderId);
}