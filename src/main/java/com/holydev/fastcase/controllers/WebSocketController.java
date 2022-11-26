package com.holydev.fastcase.controllers;

import com.holydev.fastcase.entities.service_entities.Notification;
import com.holydev.fastcase.services.realisation.NotificationService;
import com.holydev.fastcase.utilities.primitives.SimpleNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final NotificationService notificationService;


    @MessageMapping("/broadcast")
    @SendTo("/stream_all")
    public SimpleNotification receiveMessage(@Payload SimpleNotification message) {
//        TODO запись отправки броадкаста в базу
        notificationService.save(new Notification());
        return message;
    }


//    TODO Можно переделать! MessageMapping отвечает за получение сообщение по WebSocket, а на самом деле все делает simpMessagingTemplate
    @MessageMapping("/direct_trigger")
    public Notification recMessage(@Payload Notification message) {
        simpMessagingTemplate.convertAndSendToUser(message.getAddressant().getUsername(), "/private", message);
//        TODO запись в базу (если нужно)
        notificationService.save(new Notification());
        return message;
    }
}
