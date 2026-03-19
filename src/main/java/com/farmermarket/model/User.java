package com.farmermarket.model;

import com.farmermarket.enums.BuyerType;
import com.farmermarket.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

// @Entity tells JPA:
// "This class is a database table"
// JPA will create 'users' table automatically
@Entity

// @Table tells JPA the exact table name
@Table(name = "users")

// @Data from Lombok automatically generates:
// getters, setters, toString, equals, hashCode
// Saves us writing 100+ lines of boilerplate
@Data

// Generates constructor with ALL fields
@AllArgsConstructor

// Generates constructor with NO fields
// JPA requires this - it creates objects
// using empty constructor internally
@NoArgsConstructor
public class User {

    // @Id marks this as PRIMARY KEY
    @Id
    // @GeneratedValue AUTO_INCREMENT
    // Database auto assigns 1,2,3,4...
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column defines column properties
    // nullable = false means NOT NULL in DB
    @Column(nullable = false)
    private String name;

    // unique = true means no two users
    // can have same email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Phone must be unique
    // Used for WhatsApp notifications
    @Column(nullable = false, unique = true)
    private String phone;

    // @Enumerated tells JPA to store
    // enum as STRING in database
    // Stores "FARMER" not 0 or 1
    // Much more readable in database
    @Enumerated(EnumType.STRING)
    private Role role;

    // Only filled for BUYER users
    // NULL for farmers
    @Enumerated(EnumType.STRING)
    private BuyerType buyerType;

    // Business name for commercial buyers
    // "Hotel Taj Mahal Mangalore"
    private String businessName;

    // City or town
    // "Puttur", "Mangalore", "Udupi"
    private String location;

    // District name
    // "Dakshina Kannada", "Udupi"
    private String district;

    // Has admin verified this buyer?
    // Default false when registered
    @Column(columnDefinition = "boolean default false")
    private Boolean isVerified = false;

    // Is account active?
    // Default true when registered
    // Admin can set false to suspend
    @Column(columnDefinition = "boolean default true")
    private Boolean isActive = true;

    // @CreationTimestamp automatically
    // saves current time when user registers
    // No need to set this manually
    @CreationTimestamp
    private LocalDateTime createdAt;
}
