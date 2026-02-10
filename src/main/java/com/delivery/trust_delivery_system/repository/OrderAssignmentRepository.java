package com.delivery.trust_delivery_system.repository;

import com.delivery.trust_delivery_system.model.OrderAssignment;
import com.delivery.trust_delivery_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderAssignmentRepository extends JpaRepository<OrderAssignment, Long> {
    // This fixed the error: "The method findByDeliveryAgentAndStatusNot is undefined"
    List<OrderAssignment> findByDeliveryAgentAndStatusNot(User agent, OrderAssignment.AssignmentStatus status);
}