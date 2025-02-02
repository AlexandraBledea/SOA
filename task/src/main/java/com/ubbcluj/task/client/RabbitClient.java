package com.ubbcluj.task.client;

import com.ubbcluj.task.dto.EmailNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitClient {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public RabbitClient(RabbitTemplate rabbitTemplate,
                        @Value("${rabbit.exchange}") String exchangeName,
                        @Value("${rabbit.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void sendMessage(EmailNotificationDto emailNotificationDto) {
        try {
            log.info("Sending email notification to Rabbit");
            rabbitTemplate.convertAndSend(exchangeName, routingKey, emailNotificationDto);
            log.info("Finished sending email notification to Rabbit");
        } catch (AmqpException e) {
            log.warn("Error sending notification", e);
        }
    }
}