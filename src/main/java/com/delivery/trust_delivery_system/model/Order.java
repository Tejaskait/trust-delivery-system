package com.delivery.trust_delivery_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_name", nullable = false)
    private String orderName;

    private String size;

    @Column(name = "ordered_from", nullable = false)
    private String orderedFrom;

    @Column(name = "ordered_to", nullable = false)
    private String orderedTo;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PLACED;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum OrderStatus {
        PLACED, OUT_FOR_DELIVERY, AWAITING_CONFIRMATION, COMPLETED, CANCELLED
    }

    // Constructors
    public Order() {}

    public Order(Long id, String orderName, String size, String orderedFrom, String orderedTo, User customer, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.orderName = orderName;
        this.size = size;
        this.orderedFrom = orderedFrom;
        this.orderedTo = orderedTo;
        this.customer = customer;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderName() { return orderName; }
    public void setOrderName(String orderName) { this.orderName = orderName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getOrderedFrom() { return orderedFrom; }
    public void setOrderedFrom(String orderedFrom) { this.orderedFrom = orderedFrom; }

    public String getOrderedTo() { return orderedTo; }
    public void setOrderedTo(String orderedTo) { this.orderedTo = orderedTo; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}