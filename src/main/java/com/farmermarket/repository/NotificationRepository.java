package com.farmermarket.repository;

import com.farmermarket.model.Notification;
import com.farmermarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    // Get all notifications of a user (latest first)
    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    // Get unread notifications
    List<Notification> findByUserAndReadFalse(User user);
}
