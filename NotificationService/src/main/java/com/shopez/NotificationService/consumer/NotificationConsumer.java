package com.shopez.NotificationService.consumer;

import com.shopez.NotificationService.dto.EmailNotification;
import com.shopez.NotificationService.service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class NotificationConsumer {

    @Autowired
    private EmailSender emailSender;

    @KafkaListener(topics = "order_notification", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(EmailNotification notification) {
        emailSender.send(notification.getTo(), notification.getSubject(), notification.getBody());
    }


}
