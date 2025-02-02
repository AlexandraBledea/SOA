package com.ubbcluj.task.controller;

import com.ubbcluj.task.config.LogToKafka;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test-kafka")
    public ResponseEntity<String> testKafka() {
        return ResponseEntity.ok("Kafka works!!!");
    }
}
