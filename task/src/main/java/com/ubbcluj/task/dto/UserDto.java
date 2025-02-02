package com.ubbcluj.task.dto;

import java.time.LocalDate;

public record UserDto(String username, String email, LocalDate dateOfBirth) {
}
