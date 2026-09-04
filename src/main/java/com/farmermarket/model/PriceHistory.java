package com.farmermarket.model;

import com.farmermarket.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
public class PriceHistory {

    // ===============================
    // ID
    // ===============================

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    // ===============================
    // CATEGORY
    // ===============================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;


    // ===============================
    // PRICE
    // ===============================

    @Column(nullable = false)
    private Double price;


    // ===============================
    // DISTRICT
    // ===============================

    @Column(nullable = false)
    private String district;


    // ===============================
    // CREATED AT
    // ===============================

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    // ===============================
    // CONSTRUCTOR
    // ===============================

    public PriceHistory() {
    }


    // ===============================
    // GETTERS
    // ===============================

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public String getDistrict() {
        return district;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // ===============================
    // SETTERS
    // ===============================

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    // ===============================
    // TO STRING
    // ===============================

    @Override
    public String toString() {
        return "PriceHistory{" +
                "id=" + id +
                ", category=" + category +
                ", price=" + price +
                ", district='" + district + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}