package com.neighbourhoodwatch.api.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByNotificationUUID(String uuid);
}
