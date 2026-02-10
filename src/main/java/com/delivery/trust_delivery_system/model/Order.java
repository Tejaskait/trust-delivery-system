package com.delivery.trust_delivery_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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
}