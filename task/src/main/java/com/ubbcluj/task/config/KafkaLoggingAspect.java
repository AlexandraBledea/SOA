package com.ubbcluj.task.config;

import com.ubbcluj.task.client.KafkaClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Exception exception = null;
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String logMessage = authentication.getName() + " called " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
            if (exception != null) {
                logMessage += " with exception " + exception.getMessage();
            }
            String finalLogMessage = logMessage;
            retryTemplate.execute((RetryCallback<Void, RuntimeException>) context -> {
                kafkaClient.publishMessage(finalLogMessage);
                return null;
            });
        }
    }
}