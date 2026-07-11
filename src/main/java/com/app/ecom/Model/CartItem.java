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
@Table(name = "cart_table")
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // why @ManyToOne needed ?
    // JPA sees:
    // This is a field of type User. How am I supposed to store this object in one database column? So Without @ManyToOne → JPA treats User as a normal field and cannot store the entire object in one column [ERROR].

    // with @ManytoOne:
    // JPA understands:
    // This field represents a relationship. I'll store only user_id in the database. So with @ManyToOne → JPA treats User as a relationship and creates/manages the foreign key automatically.

    // one user can have many items in the cart
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
