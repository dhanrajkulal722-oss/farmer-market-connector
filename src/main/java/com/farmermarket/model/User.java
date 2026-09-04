package com.farmermarket.model;

import com.farmermarket.enums.BuyerType;
import com.farmermarket.enums.Role;
import com.farmermarket.enums.UserStatus;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(
        nullable = false,
        unique = true,
        length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(
        nullable = false,
        unique = true,
        length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private BuyerType buyerType;

    private String businessName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String location;

    private String district;

    private Boolean isVerified;

    private Boolean isActive;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // ════════════════════════════
    // CONSTRUCTORS
    // ════════════════════════════

    public User() {
    }

    // ════════════════════════════
    // GETTERS
    // ════════════════════════════

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public BuyerType getBuyerType() {
        return buyerType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getDistrict() {
        return district;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public Boolean getIsActive() {
        return isActive;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(
            String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setBuyerType(
            BuyerType buyerType) {
        this.buyerType = buyerType;
    }

    public void setBusinessName(
            String businessName) {
        this.businessName = businessName;
    }

    public void setStatus(
            UserStatus status) {
        this.status = status;
    }

    public void setLocation(
            String location) {
        this.location = location;
    }

    public void setDistrict(
            String district) {
        this.district = district;
    }

    public void setIsVerified(
            Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setIsActive(
            Boolean isActive) {
        this.isActive = isActive;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", role=" + role +
            ", status=" + status +
            '}';
    }
}