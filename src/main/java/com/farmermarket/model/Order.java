package com.farmermarket.model;

import com.farmermarket.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Which product was ordered?
     * Links to products table!
     */
    @ManyToOne
    @JoinColumn(
        name = "product_id",
        nullable = false)
    private Product product;

    /**
     * Who placed the order?
     * Links to users table!
     */
    @ManyToOne
    @JoinColumn(
        name = "buyer_id",
        nullable = false)
    private User buyer;

    /**
     * Which farmer received order?
     * Links to users table!
     */
    @ManyToOne
    @JoinColumn(
        name = "farmer_id",
        nullable = false)
    private User farmer;

    private Double quantity;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // ════════════════════════════
    // CONSTRUCTOR
    // ════════════════════════════
    public Order() {
    }

    // ════════════════════════════
    // GETTERS
    // ════════════════════════════

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getFarmer() {
        return farmer;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getMessage() {
        return message;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ════════════════════════════
    // SETTERS
    // ════════════════════════════

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(
            Product product) {
        this.product = product;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setFarmer(User farmer) {
        this.farmer = farmer;
    }

    public void setQuantity(
            Double quantity) {
        this.quantity = quantity;
    }

    public void setMessage(
            String message) {
        this.message = message;
    }

    public void setStatus(
            OrderStatus status) {
        this.status = status;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", quantity=" + quantity +
            ", status=" + status +
            '}';
    }
}