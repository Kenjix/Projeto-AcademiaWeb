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
    private long id;

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

    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "FK_exercicios")
    private Exercicio exercicioID;

    @ManyToOne
    @JoinColumn(name = "FK_cliente")
    private Cliente clienteID;

    @ManyToOne
    @JoinColumn(name = "FK_funcionario")
    private Funcionario funcionarioID;
}






