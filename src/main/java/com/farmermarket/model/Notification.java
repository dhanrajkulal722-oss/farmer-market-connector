package com.farmermarket.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who receives this notification?
    @ManyToOne
    @JoinColumn(name = "user_id",
                nullable = false)
    private User user;

    // Short title
    // "New buyer interest!"
    // "Order accepted!"
    private String title;

    // Full message
    // "Suresh from Mangalore wants
    //  500 of your coconuts at ₹18"
    @Column(columnDefinition = "TEXT")
    private String message;

    // Has user seen this notification?
    // false = unread (show red dot)
    // true = already seen
    @Column(columnDefinition = 
        "boolean default false")
    private Boolean isRead = false;

    @CreationTimestamp
    private LocalDateTime createdAt;
}