package com.farmermarket.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who receives notification
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    // IMPORTANT: renamed from isRead → read
    @Column(name = "is_read", columnDefinition = "boolean default false")
    private Boolean read = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // ==========================
    // CONSTRUCTOR
    // ==========================
    public Notification() {
    }

    public Notification(Long id, User user, String title, String message, Boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
    }

    // ==========================
    // GETTERS
    // ==========================
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ==========================
    // SETTERS
    // ==========================
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ==========================
    // TO STRING
    // ==========================
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }
}