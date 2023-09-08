package com.example.academia.model.DTO;
import com.example.academia.model.Exercicio;
import com.example.academia.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record TreinoDTO(int id, int ordem, String series, int repeticao, int carga, String tipoTreino,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date trocaTreino, String observacao,
                        Exercicio exercicioId) {}
