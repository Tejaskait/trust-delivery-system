package com.delivery.trust_delivery_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_assignments")
public class OrderAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_agent_id", nullable = false)
    private User deliveryAgent;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status = AssignmentStatus.ACCEPTED;

    private LocalDateTime acceptedAt = LocalDateTime.now();
    private LocalDateTime deliveredAt;

    public enum AssignmentStatus {
        ACCEPTED, PICKED_UP, DELIVERED
    }

    // Constructors
    public OrderAssignment() {}

    public OrderAssignment(Long id, Order order, User deliveryAgent, AssignmentStatus status, LocalDateTime acceptedAt, LocalDateTime deliveredAt) {
        this.id = id;
        this.order = order;
        this.deliveryAgent = deliveryAgent;
        this.status = status;
        this.acceptedAt = acceptedAt;
        this.deliveredAt = deliveredAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public User getDeliveryAgent() { return deliveryAgent; }
    public void setDeliveryAgent(User deliveryAgent) { this.deliveryAgent = deliveryAgent; }

    public AssignmentStatus getStatus() { return status; }
    public void setStatus(AssignmentStatus status) { this.status = status; }

    public LocalDateTime getAcceptedAt() { return acceptedAt; }
    public void setAcceptedAt(LocalDateTime acceptedAt) { this.acceptedAt = acceptedAt; }

    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
}