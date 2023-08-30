package com.example.academia.model.DTO;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public record RegisterDTO(String email, String password, String nome, String celular,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataNasc) {
}
