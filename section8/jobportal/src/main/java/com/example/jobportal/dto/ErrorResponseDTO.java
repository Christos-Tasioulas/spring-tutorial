package com.example.jobportal.dto;

import com.example.jobportal.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String apiPath, HttpStatus status, String errorMessage, LocalDateTime errorTime) {
}
