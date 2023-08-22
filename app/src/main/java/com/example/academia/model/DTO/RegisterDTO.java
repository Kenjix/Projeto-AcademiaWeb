package com.example.academia.model.DTO;

import com.example.academia.enuns.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
