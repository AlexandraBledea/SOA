package com.ubbcluj.task.dto;

public record ErrorResponseDto(String errorCode,
                               String errorName,
                               String errorMessage) {
}