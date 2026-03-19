package com.farmermarket.model;

import com.farmermarket.enums.Category;
import com.farmermarket.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne means:
    // MANY products belong to ONE farmer
    // Example: Ramesh can post
    // 10 different listings
    // All linked to Ramesh's user id
    @ManyToOne
    // @JoinColumn creates foreign key
    // farmer_id column in products table
    // Links to id column in users table
    @JoinColumn(name = "farmer_id",
                nullable = false)
    private User farmer;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    // Optional detailed description
    @Column(columnDefinition = "TEXT")
    private String description;

    // Quantity available
    // Double allows decimal values
    // 100.5 kg is valid
    @Column(nullable = false)
    private Double quantity;

    // Unit of measurement
    // "kg", "pieces", "quintal", "ton"
    private String unit;

    // Price per unit in rupees
    @Column(nullable = false)
    private Double price;

    // Village or town where produce is
    private String location;

    private String district;

    // URL of product image
    // Stored on Cloudinary
    private String imageUrl;

    // Status of listing
    // Starts as ACTIVE when created
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = 
        "varchar(255) default 'ACTIVE'")
    private ProductStatus status 
        = ProductStatus.ACTIVE;

    @CreationTimestamp
    private LocalDateTime createdAt;
}