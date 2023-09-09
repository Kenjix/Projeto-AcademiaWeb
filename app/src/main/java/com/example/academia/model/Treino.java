package com.example.academia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "treinos")
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int ordem;

    @Column(length = 20)
    private String series;

    @Column(nullable = false)
    private int repeticao;

    @Column(nullable = false)
    private int carga;

    @Column(length = 20)
    private String tipoTreino;

    @Column
    @Temporal(TemporalType.DATE)
    private Date trocaTreino;

    @Column
    private String observacao;

    @Column
    private boolean ativo;

    @PrePersist
    private void onCreate() {
        ativo = true;
    }

    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    private Exercicio exercicio;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}






