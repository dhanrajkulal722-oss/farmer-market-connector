package com.farmermarket.controller;

import com.farmermarket.model.Notification;
import com.farmermarket.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // 🔹 GET ALL NOTIFICATIONS
    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getByUser(userId)
        );
    }

    // 🔹 MARK AS READ
    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long id) {

        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }

    // 🔹 MARK ALL AS READ
    @PutMapping("/read-all/{userId}")
    public ResponseEntity<String> markAllAsRead(
            @PathVariable Long userId) {

        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok("All notifications marked as read");
    }
}
