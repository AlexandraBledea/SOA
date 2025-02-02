package com.ubbcluj.notification.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public void receiveMessage(EmailNotificationDto message) throws JsonProcessingException {
//        EmailNotificationDto emailNotificationDto = mapper.readValue(message, EmailNotificationDto.class);
        System.out.println("Am primit mesajul!!!!!!!");
        log.info("Am primit mesajul ca log!!!! yay");
        System.out.println("Received message: " + message);
        System.out.println(message.email());
        System.out.println(message.description());
        this.emailNotificationService.sendNotificationEmail(message.email(), message.description());
        //TODO Add email send
    }
}
