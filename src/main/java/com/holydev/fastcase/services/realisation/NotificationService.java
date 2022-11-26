package com.holydev.fastcase.services.realisation;


import com.holydev.fastcase.entities.service_entities.Notification;
import com.holydev.fastcase.repos.NotificationsRepo;
import com.holydev.fastcase.services.interfaces.NotificationServiceInterface;
import com.holydev.fastcase.utilities.customs.CustomNotificationType;
import com.holydev.fastcase.utilities.primitives.SimpleNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationServiceInterface {

    private final NotificationsRepo notificationsRepo;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void save(Notification notification) {
        notificationsRepo.save(notification);
    }

    @Override
    public Notification saveAndFlush(Notification notification) {
        return notificationsRepo.saveAndFlush(notification);
    }

    @Scheduled(fixedRate = 5000)
    public void sendNotification() {
        var msg = new SimpleNotification("First try!");

        simpMessagingTemplate.convertAndSendToUser("admin", "/direct_trigger", msg);
        System.out.println("sended");
        /*var all_open = notificationsRepo.findByStatus(0);
        for (var n : all_open) {
            if (n.getType().equalsIgnoreCase(CustomNotificationType.BY_TIMER)) {
                var neg = Duration.between(Instant.now(), n.getCreated_at().toInstant()).toHours();

                switch (n.getTrigger_strategy().getTimer()) {
                    case "1h": {
                        if (neg == n.getTries() + 1) {
                            simpMessagingTemplate.convertAndSendToUser(n.getAddressant().getUsername(), "/direct_trigger", n.getContent());
                        }
                    }
                    case "1d": {
                        if ((int) (neg / 24) == n.getTries() + 1) {
                            simpMessagingTemplate.convertAndSendToUser(n.getAddressant().getUsername(), "/direct_trigger", n.getContent());
                        }
                    }
                    case "1w": {
                        if ((int) (neg / (24 * 7)) == n.getTries() + 1) {
                            simpMessagingTemplate.convertAndSendToUser(n.getAddressant().getUsername(), "/direct_trigger", n.getContent());
                        }
                    }
                }
            } else if (n.getType().equalsIgnoreCase(CustomNotificationType.TASK_OPENED)) {
                if (n.getTries() == 0 && n.getTrigger_strategy().getTarget_task().getStatus() == 0) {
                    simpMessagingTemplate.convertAndSendToUser(n.getAddressant().getUsername(), "/direct_trigger", n.getContent());
                    n.setTries(1);
                    n.setStatus(1);
                    save(n);
                }
            } else if (n.getType().equalsIgnoreCase(CustomNotificationType.TASK_CLOSED)) {
                if (n.getTries() == 0 && n.getTrigger_strategy().getTarget_task().getStatus() == 1) {
                    simpMessagingTemplate.convertAndSendToUser(n.getAddressant().getUsername(), "/direct_trigger", n.getContent());
                    n.setTries(1);
                    n.setStatus(1);
                    save(n);
                }
            } else if (n.getType().equalsIgnoreCase(CustomNotificationType.TASK_COMPLETED)) {
                if (n.getTries() == 0 && n.getTrigger_strategy().getTarget_task().getStatus() == 2) {
                    simpMessagingTemplate.convertAndSendToUser(n.getAddressant().getUsername(), "/direct_trigger", n.getContent());
                    n.setTries(1);
                    n.setStatus(1);
                    save(n);
                }
            }
        }*/
    }
}
