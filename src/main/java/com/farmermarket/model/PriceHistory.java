package com.farmermarket.model;

import com.farmermarket.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which crop?
    @Enumerated(EnumType.STRING)
    private Category category;

    // Price per unit that day
    private Double price;

    // Which district this price is for
    // Prices differ by location!
    private String district;

    // When this price was recorded
    @CreationTimestamp
    private LocalDateTime createdAt;
}