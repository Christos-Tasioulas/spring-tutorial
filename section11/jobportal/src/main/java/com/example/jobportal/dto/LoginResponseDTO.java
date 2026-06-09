package com.example.jobportal.dto;

public record LoginResponseDTO(String message, UserDto user, String jwToken) {
}
