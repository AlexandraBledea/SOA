package com.ubbcluj.task.controller;

import com.ubbcluj.task.config.LogToKafka;
import com.ubbcluj.task.dto.AssignUserDto;
import com.ubbcluj.task.dto.TaskDto;
import com.ubbcluj.task.exception.EntityNotFoundException;
import com.ubbcluj.task.exception.RequestNotValidException;
import com.ubbcluj.task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value="/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(this.taskService.getTaskById(id));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) throws EntityNotFoundException, RequestNotValidException {
        return ResponseEntity.ok(this.taskService.updateTask(taskDto));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/update-status", produces = APPLICATION_JSON_VALUE)
     public ResponseEntity<TaskDto> updateTaskStatus(@RequestBody TaskDto taskDto) throws EntityNotFoundException, RequestNotValidException {
        return ResponseEntity.ok(this.taskService.updateStatus(taskDto));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable("id") Long id) throws EntityNotFoundException {
        this.taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/all-assigned-to")
    public ResponseEntity<List<TaskDto>> getAllTasksAssignedTo(@RequestBody AssignUserDto assignUserDto) throws EntityNotFoundException {
        return ResponseEntity.ok(this.taskService.getAllTasksAssignedTo(assignUserDto));
    }

    @LogToKafka
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/assign/{taskId}")
    public ResponseEntity<Object> assignTask(@PathVariable("taskId") Long taskId, @RequestBody AssignUserDto userDto) throws EntityNotFoundException {
        this.taskService.assignTask(taskId, userDto);
        return ResponseEntity.ok().build();
    }
}
