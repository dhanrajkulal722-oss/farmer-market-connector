package com.farmermarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "saved_listings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which buyer saved this?
    @ManyToOne
    @JoinColumn(name = "buyer_id",
                nullable = false)
    private User buyer;

    // Which product was saved?
    @ManyToOne
    @JoinColumn(name = "product_id",
                nullable = false)
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
