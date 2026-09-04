package com.farmermarket.service;

import com.farmermarket.exception.ResourceNotFoundException;
import com.farmermarket.model.Notification;
import com.farmermarket.model.User;
import com.farmermarket.repository.NotificationRepository;
import com.farmermarket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // 🔥 CREATE NOTIFICATION (used internally)
    public void createNotification(User user, String title, String message) {

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    // 🔹 GET ALL NOTIFICATIONS
    public List<Notification> getByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    // 🔹 MARK SINGLE AS READ
    public void markAsRead(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notification not found with id: " + id));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    // 🔹 MARK ALL AS READ
    public void markAllAsRead(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        List<Notification> notifications =
                notificationRepository.findByUserOrderByCreatedAtDesc(user);

        for (Notification n : notifications) {
            n.setRead(true);
        }

        notificationRepository.saveAll(notifications);
    }
}
