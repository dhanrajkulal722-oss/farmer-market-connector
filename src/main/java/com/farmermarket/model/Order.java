package com.farmermarket.model;

import com.farmermarket.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
// 'orders' because 'order' is reserved
// keyword in SQL!
// Always use plural for table names
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which product is this order for?
    // Many orders can be for one product
    // (Multiple buyers can show interest)
    @ManyToOne
    @JoinColumn(name = "product_id",
                nullable = false)
    private Product product;

    // Who is the buyer?
    @ManyToOne
    @JoinColumn(name = "buyer_id",
                nullable = false)
    private User buyer;

    // Who is the farmer?
    // Stored separately for easy access
    // No need to go through product
    // to find farmer
    @ManyToOne
    @JoinColumn(name = "farmer_id",
                nullable = false)
    private User farmer;

    // How much quantity buyer wants
    // Can be less than total available
    private Double quantity;

    // Order starts as PENDING
    // Changes based on farmer action
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = 
        "varchar(255) default 'PENDING'")
    private OrderStatus status 
        = OrderStatus.PENDING;

    // Optional message from buyer
    // "I need this by Sunday"
    // "Can you deliver to Mangalore?"
    @Column(columnDefinition = "TEXT")
    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;
}