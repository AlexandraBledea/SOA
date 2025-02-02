package com.ubbcluj.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggerListener {
    @KafkaListener(topics = "${kafka-topic}")
    public void consumeMessage(String message) {
        log.info(message);
    }
}
