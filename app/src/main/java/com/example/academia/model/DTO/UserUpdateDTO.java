package com.example.academia.model.DTO;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
public record UserUpdateDTO(Long userId, String nome, String cpf, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataNasc, String telefone,
                            String celular, float peso, float altura, String objetivo, String observacoes){}
