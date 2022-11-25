package com.holydev.fastcase.services.realisation;


import com.holydev.fastcase.entities.service_entities.Notification;
import com.holydev.fastcase.entities.service_entities.TriggerStrategy;
import com.holydev.fastcase.repos.TriggerRepo;
import com.holydev.fastcase.utilities.customs.CustomNotificationContentSample;
import com.holydev.fastcase.utilities.customs.CustomNotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TriggerService {
    private final TriggerRepo triggerRepo;

    private final NotificationService notificationService;

    public TriggerStrategy save(TriggerStrategy strategy) {
        var notification = new Notification();
        notification.setReferrer(strategy.getAdressant());
        notification.setAddressant(strategy.getAdressant());
        notification.setCreated_at(new Date());
        notification.setStatus(0);
        notification.setContent(generateContent(strategy));
        notification.setTrigger_strategy(strategy);
        notification.setType(generateType(strategy.getTriggerType()));
        notification.setTries(0);
        notification = notificationService.saveAndFlush(notification);
        strategy.addNotification(notification);
        return triggerRepo.saveAndFlush(strategy);
    }

    private String generateType(String triggerType) {
        if (triggerType.equalsIgnoreCase("TASK_OPENED")) {
            return CustomNotificationType.TASK_OPENED;
        } else if (triggerType.equalsIgnoreCase("TASK_CLOSED")) {
            return CustomNotificationType.TASK_CLOSED;
        } else if (triggerType.equalsIgnoreCase("TASK_COMPLETED")) {
            return CustomNotificationType.TASK_COMPLETED;
        } else if (triggerType.equalsIgnoreCase("BY_TIMER")) {
            return CustomNotificationType.BY_TIMER;
        } else return "";
    }

    private String generateContent(TriggerStrategy strategy) {
        if (strategy.getTriggerType().equalsIgnoreCase("TASK_OPENED")) {
            return String.format(CustomNotificationContentSample.TASK_OPENED, strategy.getAdressant().getFio(), strategy.getTarget_task().getId(), strategy.getParent_task().getId());
        } else if (strategy.getTriggerType().equalsIgnoreCase("TASK_CLOSED")) {
            return String.format(CustomNotificationContentSample.TASK_CLOSED, strategy.getAdressant().getFio(), strategy.getTarget_task().getId(), strategy.getParent_task().getId());
        } else if (strategy.getTriggerType().equalsIgnoreCase("TASK_COMPLETED")) {
            return String.format(CustomNotificationContentSample.TASK_COMPLETED, strategy.getAdressant().getFio(), strategy.getTarget_task().getId(), strategy.getParent_task().getId());
        } else if (strategy.getTriggerType().equalsIgnoreCase("BY_TIMER")) {
            return String.format(CustomNotificationContentSample.BY_TIMER, strategy.getAdressant().getFio(), strategy.getTarget_task().getId());
        } else return "";
    }
}
