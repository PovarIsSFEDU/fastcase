package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.service_entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepo extends JpaRepository<Notification, Long> {
}
