package com.app.ecom.Model;

import com.app.ecom.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "order_table")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many orders can belong to one user.
    // One user can place multiple orders over time.
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal totalAmount;

    // jpa stores them as String in DB instead of 0,1,2,...
    @Enumerated(EnumType.STRING)
    private OrderStatus status =  OrderStatus.PENDING;

    // One order contains many order items.
    // mappedBy tells JPA that this side is the inverse side of the relationship, and that the relationship is already owned and managed by the field specified in the other entity.

    // Real-life analogy
    // Think of a marriage.
    // Person A Married to Person B.
    // Person B married to Person A.
    // There is only one marriage, not two.tatus

    //mappedBy = <column name> tells Hibernate:
    // These two references describe the same relationship. This is the inverse side of the relationship. The other entity owns and manages it.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
