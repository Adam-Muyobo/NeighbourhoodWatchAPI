package com.neighbourhoodwatch.api.notification;

import com.neighbourhoodwatch.api.notification.dto.NotificationDTO;
import com.neighbourhoodwatch.api.user.User;
import com.neighbourhoodwatch.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationDTO createNotification(String userUUID, String title, String message,
                                              Notification.NotificationType type) {
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .type(type)
                .status(Notification.NotificationStatus.SENT)
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        return NotificationDTO.fromEntity(savedNotification);
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotificationDTO getNotificationByUUID(String notificationUUID) {
        Notification notification = notificationRepository.findByNotificationUUID(notificationUUID)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return NotificationDTO.fromEntity(notification);
    }

    public NotificationDTO updateNotificationStatus(String notificationUUID, Notification.NotificationStatus status) {
        Notification notification = notificationRepository.findByNotificationUUID(notificationUUID)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(status);
        Notification updatedNotification = notificationRepository.save(notification);
        return NotificationDTO.fromEntity(updatedNotification);
    }

    public void deleteNotification(String notificationUUID) {
        Notification notification = notificationRepository.findByNotificationUUID(notificationUUID)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notificationRepository.delete(notification);
    }
}