package com.ubbcluj.task.service;

import com.ubbcluj.task.client.RabbitClient;
import com.ubbcluj.task.dto.EmailNotificationDto;
import com.ubbcluj.task.dto.TaskDto;
import com.ubbcluj.task.exception.EntityNotFoundException;
import com.ubbcluj.task.exception.RequestNotValidException;
import com.ubbcluj.task.persistence.TaskRepository;
import com.ubbcluj.task.persistence.UserRepository;
import com.ubbcluj.task.persistence.entity.TaskEntity;
import com.ubbcluj.task.persistence.entity.UserEntity;
import com.ubbcluj.task.utils.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final Converter converter;
    private final RabbitClient rabbitClient;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, Converter converter, RabbitClient rabbitClient) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.converter = converter;
        this.rabbitClient = rabbitClient;
    }

    public List<TaskDto> getAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        log.info("{} tasks found", taskEntities.size());
        List<TaskDto> taskDtos = taskEntities.stream().map(converter::convertToTaskDto).toList();
        log.info("Tasks found are {}", taskDtos);
        return taskDtos;
    }

    public TaskDto createTask(TaskDto task) throws EntityNotFoundException {
        UserEntity user = userRepository.findByUsername(task.createdBy())
                .orElseThrow(EntityNotFoundException::new);
        TaskEntity taskEntity = converter.convertToSaveTaskEntity(task);
        taskEntity.setCreatedBy(user);
        taskEntity = taskRepository.save(taskEntity);
        if (taskEntity.getId() == null) {
            log.error("Task was not persisted to DB");

        }
        TaskDto taskDto = converter.convertToTaskDto(taskEntity);
        log.info("Task created successfully {}", taskDto);
        return taskDto;
    }


    public TaskDto updateTask(TaskDto task) throws EntityNotFoundException, RequestNotValidException {
        if (task == null || task.id() == null) {
            throw new RequestNotValidException();
        }

        TaskEntity taskEntity = taskRepository.findById(task.id())
                .orElseThrow(EntityNotFoundException::new);

        taskEntity.setDescription(task.description());
        taskEntity.setTitle(task.title());
        taskEntity.setDueDate(task.dueDate());

        taskEntity = taskRepository.save(taskEntity);

        TaskDto taskDto = converter.convertToTaskDto(taskEntity);
        log.info("Task updated successfully {}", taskDto);
        return taskDto;
    }

    public TaskDto getTaskById(Long id) throws EntityNotFoundException {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        log.info("Task found {}", taskEntity);
        return converter.convertToTaskDto(taskEntity);
    }

    public TaskDto assignTask(Long taskId, Long userId) throws EntityNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);

        taskEntity.setAssignedTo(userEntity);
        taskEntity = taskRepository.save(taskEntity);

        TaskDto taskDto = converter.convertToTaskDto(taskEntity);
        log.info("Task assigned successfully {} to user {}", taskDto, userEntity);
        return taskDto;
    }

    public void testRabbitMq() {
        EmailNotificationDto emailNotificationDto = new EmailNotificationDto("ale@yahoo.com", "test", "rabbit mq works", LocalDate.now());
        rabbitClient.sendMessage(emailNotificationDto);
    }

    public void deleteTask(Long taskId) throws EntityNotFoundException {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        taskRepository.delete(taskEntity);
    }

}
