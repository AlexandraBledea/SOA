package com.ubbcluj.task.client;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


@Component
public class KafkaClient {
    KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaClient(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka-topic}")String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publishMessage(String message) {
        kafkaTemplate.send(topic, message);
    }
}
