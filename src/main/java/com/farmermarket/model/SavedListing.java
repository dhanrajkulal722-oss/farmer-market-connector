package com.farmermarket.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_listings")
public class SavedListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which buyer saved this?
    @ManyToOne
    @JoinColumn(
            name = "buyer_id",
            nullable = false
    )
    private User buyer;

    // Which product was saved?
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdAt;


    // ==============================
    // DEFAULT CONSTRUCTOR
    // ==============================

    public SavedListing() {
    }


    // ==============================
    // ALL ARGUMENTS CONSTRUCTOR
    // ==============================

    public SavedListing(
            Long id,
            User buyer,
            Product product,
            LocalDateTime createdAt) {

        this.id = id;
        this.buyer = buyer;
        this.product = product;
        this.createdAt = createdAt;
    }


    // ==============================
    // GETTERS
    // ==============================

    public Long getId() {
        return id;
    }

    public User getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // ==============================
    // SETTERS
    // ==============================

    public void setId(Long id) {
        this.id = id;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }
}