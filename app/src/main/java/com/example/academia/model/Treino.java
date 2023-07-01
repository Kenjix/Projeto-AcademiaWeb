package com.example.academia.model;

import jakarta.persistence.*;
import com.example.academia.model.Exercicio;
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
    private int id;
    @Column
    private int ordem;
    @Column(length = 20)
    private String series;
    @Column
    private int repeticao;
    @Column
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
    private Exercicio exercicio;

}






