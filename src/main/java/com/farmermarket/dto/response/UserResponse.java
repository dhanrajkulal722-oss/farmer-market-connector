package com.farmermarket.dto.response;

import com.farmermarket.enums.Role;
import com.farmermarket.enums.UserStatus;
import java.time.LocalDateTime;

/**
 * ══════════════════════════════════════
 * USER RESPONSE DTO
 * ══════════════════════════════════════
 * What we send back to client.
 * NO password field!
 * Safe to send!
 * ══════════════════════════════════════
 */
public class UserResponse {

    // ══════════════════════════════
    // FIELDS
    // ══════════════════════════════

    private Long id;
    private String name;
    private String email;

    // NO password field!
    // NEVER send password to client!

    private String phone;
    private Role role;
    private UserStatus status;
    private String location;
    private LocalDateTime createdAt;

    // ══════════════════════════════
    // CONSTRUCTORS
    // ══════════════════════════════

    /**
     * Empty constructor
     */
    public UserResponse() {
    }

    /**
     * Full constructor
     */
    public UserResponse(
            Long id,
            String name,
            String email,
            String phone,
            Role role,
            UserStatus status,
            String location,
            LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.location = location;
        this.createdAt = createdAt;
    }

    // ══════════════════════════════
    // GETTERS
    // ══════════════════════════════

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ══════════════════════════════
    // SETTERS
    // ══════════════════════════════

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setStatus(
            UserStatus status) {
        this.status = status;
    }

    public void setLocation(
            String location) {
        this.location = location;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ══════════════════════════════
    // TO STRING
    // ══════════════════════════════

    @Override
    public String toString() {
        return "UserResponse{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", role=" + role +
            ", status=" + status +
            ", location='" + location + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}