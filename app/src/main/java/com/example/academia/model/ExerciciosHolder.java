package com.example.academia.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciciosHolder {
    private Long exercicioId;
    private int ordem;
    private String series;
    private int repeticao;
    private int carga;
}
