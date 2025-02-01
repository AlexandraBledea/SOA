package com.ubbcluj.authentication.dto;

import java.time.LocalDate;

public record RegisterUserDto(String username,
                              String email,
                              String password,
                              LocalDate dateOfBirth) {
}
