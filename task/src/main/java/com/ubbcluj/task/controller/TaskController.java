package com.ubbcluj.task.controller;

import com.ubbcluj.task.config.LogToKafka;
import com.ubbcluj.task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test-kafka")
    public ResponseEntity<String> testKafka() {
        return ResponseEntity.ok("Kafka works!!!");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test-rabbitmq")
    public ResponseEntity<String> testRabbitMq() {
        taskService.testRabbitMq();
        return ResponseEntity.ok("RabbitMq works!!!");
    }
}
