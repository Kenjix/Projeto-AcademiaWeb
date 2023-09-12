package com.example.academia.model.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
public record TreinoRegisterDTO(String tipoTreino, @DateTimeFormat(pattern = "yyyy-MM-dd") Date trocaTreino, String observacao) {}
