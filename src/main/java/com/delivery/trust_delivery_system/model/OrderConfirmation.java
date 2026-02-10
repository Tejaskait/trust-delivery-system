package com.delivery.trust_delivery_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_confirmation")
public class OrderConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "delivery_agent_id", nullable = false)
    private User deliveryAgent;

    private boolean deliveryConfirmed = false;
    private boolean customerConfirmed = false;
    private LocalDateTime confirmedAt;

    // Standard Getters and Setters (Important to fix "undefined" errors)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
    public User getDeliveryAgent() { return deliveryAgent; }
    public void setDeliveryAgent(User deliveryAgent) { this.deliveryAgent = deliveryAgent; }
    public boolean isDeliveryConfirmed() { return deliveryConfirmed; }
    public void setDeliveryConfirmed(boolean deliveryConfirmed) { this.deliveryConfirmed = deliveryConfirmed; }
    public boolean isCustomerConfirmed() { return customerConfirmed; }
    public void setCustomerConfirmed(boolean customerConfirmed) { this.customerConfirmed = customerConfirmed; }
    public LocalDateTime getConfirmedAt() { return confirmedAt; }
    public void setConfirmedAt(LocalDateTime confirmedAt) { this.confirmedAt = confirmedAt; }
}