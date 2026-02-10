package com.delivery.trust_delivery_system.repository;

import com.delivery.trust_delivery_system.model.Order;
import com.delivery.trust_delivery_system.model.User; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByStatus(Order.OrderStatus status);
    
    List<Order> findByCustomer(User customer);
    
    List<Order> findByCustomerId(Long customerId);

    // FIX: Using a JOIN because deliveryAgent lives in OrderAssignment table
    @Query("SELECT a.order FROM OrderAssignment a WHERE a.deliveryAgent = :agent AND a.status != 'DELIVERED'")
    List<Order> findByDeliveryAgent(@Param("agent") User agent);

    // FIX: Filtering by agent and status via assignment table
    @Query("SELECT a.order FROM OrderAssignment a WHERE a.deliveryAgent = :agent AND a.order.status = :status")
    List<Order> findByDeliveryAgentAndStatus(@Param("agent") User agent, @Param("status") Order.OrderStatus status);
}