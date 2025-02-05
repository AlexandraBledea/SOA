package com.ubbcluj.task.controller;

import com.ubbcluj.task.config.LogToKafka;
import com.ubbcluj.task.dto.TaskDto;
import com.ubbcluj.task.exception.EntityNotFoundException;
import com.ubbcluj.task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

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

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/test-rabbitmq")
    public ResponseEntity<String> testRabbitMq() {
        taskService.testRabbitMq();
        return ResponseEntity.ok("RabbitMq works!!!");
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/all",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(this.taskService.getAllTasks());
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/save", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> saveTask(@RequestBody TaskDto taskDto) throws EntityNotFoundException {
        return ResponseEntity.ok(this.taskService.createTask(taskDto));
    }
}
