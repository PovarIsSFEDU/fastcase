package com.holydev.fastcase.repos;

import com.holydev.fastcase.entities.service_entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationsRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(int status);



}
