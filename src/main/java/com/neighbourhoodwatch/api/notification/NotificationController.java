package com.neighbourhoodwatch.api.notification;

import com.neighbourhoodwatch.api.notification.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/{userUUID}")
    public ResponseEntity<NotificationDTO> createNotification(
            @PathVariable String userUUID,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam Notification.NotificationType type
    ) {
        return ResponseEntity.ok(notificationService.createNotification(userUUID, title, message, type));
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{notificationUUID}")
    public ResponseEntity<NotificationDTO> getNotificationByUUID(@PathVariable String notificationUUID) {
        return ResponseEntity.ok(notificationService.getNotificationByUUID(notificationUUID));
    }

    @PutMapping("/{notificationUUID}")
    public ResponseEntity<NotificationDTO> updateNotificationStatus(
            @PathVariable String notificationUUID,
            @RequestParam Notification.NotificationStatus status
    ) {
        return ResponseEntity.ok(notificationService.updateNotificationStatus(notificationUUID, status));
    }

    @DeleteMapping("/{notificationUUID}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String notificationUUID) {
        notificationService.deleteNotification(notificationUUID);
        return ResponseEntity.noContent().build();
    }
}