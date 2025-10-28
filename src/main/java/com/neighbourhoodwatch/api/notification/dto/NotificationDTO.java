package com.neighbourhoodwatch.api.notification.dto;

import com.neighbourhoodwatch.api.notification.Notification;
import lombok.Data;
import java.time.Instant;

@Data
public class NotificationDTO {
    private String notificationUUID;
    private String title;
    private String message;
    private Notification.NotificationType type;
    private Notification.NotificationStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private UserDTO user;

    public static NotificationDTO fromEntity(Notification entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationUUID(entity.getNotificationUUID());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUser(UserDTO.fromEntity(entity.getUser()));
        return dto;
    }
}