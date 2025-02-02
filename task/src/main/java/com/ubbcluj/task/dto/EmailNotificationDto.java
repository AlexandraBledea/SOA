package com.ubbcluj.task.dto;

import java.time.LocalDate;

public record EmailNotificationDto(String email, String title, String description, LocalDate dueDate) {
}
