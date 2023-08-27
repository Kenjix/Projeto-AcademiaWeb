package com.example.academia.model.DTO;

import com.example.academia.enuns.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {
}
