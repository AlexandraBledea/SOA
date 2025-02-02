package com.ubbcluj.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationService {

    private final JavaMailSender javaMailSender;
    private @Value("${sender-email-address}") String senderEmail;

    public EmailNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotificationEmail(String email, String description) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(senderEmail);
        message.setSubject("New task coming");
        message.setText(description);

        javaMailSender.send(message);
    }

}
