package com.holydev.fastcase.services.interfaces;

import com.holydev.fastcase.entities.service_entities.Notification;

public interface NotificationServiceInterface {
    void save(Notification notification);

    Notification saveAndFlush(Notification notification);
}
