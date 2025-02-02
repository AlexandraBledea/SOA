package com.ubbcluj.task.config;

import com.ubbcluj.task.client.KafkaClient;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class KafkaLoggingAspect {
    private final KafkaClient kafkaClient;
    private final RetryTemplate retryTemplate;

    public KafkaLoggingAspect(KafkaClient kafkaClient, RetryTemplate retryTemplate) {
        this.kafkaClient = kafkaClient;
        this.retryTemplate = retryTemplate;
    }

    @Around("@annotation(LogToKafka)") // Runs after the method successfully completes
    public void logMethodExecution(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String logMessage = "Executed method: " + className + "." + methodName;

        retryTemplate.execute((RetryCallback<Void, RuntimeException>) context -> {
            kafkaClient.publishMessage(logMessage);
            return null;
        });
    }
}