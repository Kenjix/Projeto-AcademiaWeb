package com.example.academia.model;

import jakarta.persistence.*;


@Entity
@Table(name = "exercicios")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String nome;
    @Column(length = 50)
    private String grupoMuscular;

}