package com.ubbcluj.task.utils;

import com.ubbcluj.task.dto.TaskDto;
import com.ubbcluj.task.dto.UserDto;
import com.ubbcluj.task.persistence.entity.TaskEntity;
import com.ubbcluj.task.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public UserDto convertToUserDto(UserEntity userEntity) {
        return new UserDto(userEntity.getUsername(), userEntity.getEmail(), userEntity.getDateOfBirth());
    }

    public TaskEntity convertToSaveTaskEntity(TaskDto taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setDescription(taskDto.description());
        taskEntity.setTitle(taskDto.title());
        taskEntity.setStatus(taskDto.status());
        taskEntity.setDueDate(taskDto.dueDate());
        return taskEntity;
    }

    public TaskDto convertToTaskDto(TaskEntity taskEntity) {
        return new TaskDto(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getDescription(), taskEntity.getStatus(),
                taskEntity.getDueDate(), taskEntity.getCreatedBy().getUsername());
    }
}
