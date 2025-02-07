package com.ubbcluj.notification.listener;

import com.ubbcluj.notification.dto.EmailNotificationDto;
import com.ubbcluj.notification.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailNotificationListener {

    private final EmailNotificationService emailNotificationService;

    public EmailNotificationListener(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @RabbitListener(queues = "${rabbit.queue-name}")
    public void receiveMessage(EmailNotificationDto message) {
        this.emailNotificationService.sendNotificationEmail(message.email(), message.description());
    }
}
