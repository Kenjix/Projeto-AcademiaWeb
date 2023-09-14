package com.example.academia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "exercicios")
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(length = 50)
    private String grupoMuscular;

    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL)
    private List<TreinoExercicio> treinoExercicios;
}
