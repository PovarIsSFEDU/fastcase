package com.holydev.fastcase.services.realisation;


import com.holydev.fastcase.entities.service_entities.Notification;
import com.holydev.fastcase.repos.NotificationsRepo;
import com.holydev.fastcase.services.interfaces.NotificationServiceInterface;
import com.holydev.fastcase.utilities.customs.CustomNotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationServiceInterface {

    private final NotificationsRepo notificationsRepo;

    @Override
    public void send(Notification notification) {
        notification.setCreated_at(new Date());
        notification.setStatus(CustomNotificationStatus.SENDED);
        notificationsRepo.save(notification);
    }
}
