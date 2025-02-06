package com.ubbcluj.task.service;

import com.ubbcluj.task.client.RabbitClient;
import com.ubbcluj.task.dto.AssignUserDto;
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
import java.util.List;

@Service
@Slf4j
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final Converter converter;
    private final RabbitClient rabbitClient;
    private final WebSocketService webSocketService;

    public TaskService(UserRepository userRepository, TaskRepository taskRepository, Converter converter, RabbitClient rabbitClient,
                       WebSocketService webSocketService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.converter = converter;
        this.rabbitClient = rabbitClient;
        this.webSocketService = webSocketService;
    }

    public List<TaskDto> getAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskEntities.stream().map(converter::convertToTaskDto).toList();
    }

    public List<TaskEntity> getAllTasksEntity() {
        return taskRepository.findAll();
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

    public TaskDto updateStatus(TaskDto task) throws EntityNotFoundException, RequestNotValidException {
        if (task == null || task.id() == null) {
            throw new RequestNotValidException();
        }

        TaskEntity taskEntity = taskRepository.findById(task.id())
                .orElseThrow(EntityNotFoundException::new);

        taskEntity.setStatus(task.status());
        taskEntity = taskRepository.save(taskEntity);

        TaskDto taskDto = converter.convertToTaskDto(taskEntity);
        log.info("Task updated successfully {}", taskDto);
        return taskDto;
    }

    public List<TaskDto> getAllTasksAssignedTo(AssignUserDto assignUserDto) throws EntityNotFoundException {
        log.info("User to look for tasks assigned to {}", assignUserDto);
        UserEntity userEntity = userRepository.findByUsername(assignUserDto.username())
                .orElseThrow(EntityNotFoundException::new);
        List<TaskEntity> taskEntities = taskRepository.findByAssignedTo(userEntity.getUsername());
        return taskEntities.stream().map(converter::convertToTaskDto).toList();
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

    public TaskDto assignTask(Long taskId, AssignUserDto userDto) throws EntityNotFoundException {
        log.info("Task will be assigned to {}", userDto);
        UserEntity userEntity = userRepository.findByUsername(userDto.username())
                .orElseThrow(EntityNotFoundException::new);
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);

        taskEntity.setAssignedTo(userEntity);
        taskEntity = taskRepository.save(taskEntity);

        TaskDto taskDto = converter.convertToTaskDto(taskEntity);
        sendEmail(userEntity, taskEntity);
//        notifyFrontend("notification","New notification! ");
        log.info("Task assigned successfully {} to user {}", taskDto, userEntity);
        return taskDto;
    }

//    private void notifyFrontend(String topic, String message) {
//        webSocketService.sendMessage(topic, message);
//    }

    public void sendEmail(UserEntity userEntity, TaskEntity taskEntity) {
        String emailDescription = String.format("User %s assigned task %s to you!", taskEntity.getCreatedBy().getUsername(), taskEntity.getTitle());
        EmailNotificationDto emailNotificationDto = new EmailNotificationDto(userEntity.getEmail(), "New task assigned!", emailDescription, LocalDate.now());
        rabbitClient.sendMessage(emailNotificationDto);
    }

    public void deleteTask(Long taskId) throws EntityNotFoundException {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        taskRepository.delete(taskEntity);
    }

}
