package com.app.ecom.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orderItem_table")
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many order items can reference the same product in different orders.
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private  Product product;

    private Integer quantity;
    private BigDecimal price;


    // Many order items belong to one order.
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private  Order order;
}
