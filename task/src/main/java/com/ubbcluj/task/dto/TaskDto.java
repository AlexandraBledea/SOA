package com.ubbcluj.task.dto;

import com.ubbcluj.task.persistence.entity.enums.TaskStatus;

import java.time.LocalDate;

public record TaskDto(Long id, String title, String description, TaskStatus status, LocalDate dueDate,
                      String createdBy, String assignedTo) {

}
