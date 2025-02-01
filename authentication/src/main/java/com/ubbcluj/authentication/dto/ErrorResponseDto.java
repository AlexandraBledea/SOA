package com.ubbcluj.authentication.dto;

public record ErrorResponseDto(String errorCode,
                               String errorName,
                               String errorMessage) {
}