package com.example.academia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private int id;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(length = 60)
    private String email;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNasc;
    @Column(nullable = false)
    private float peso;
    @Column(nullable = false)
    private float altura;
    @Column
    private String observacoes;
    @Column
    private int cargaHoraria;
    @Column(length = 20)
    private String turno;
    @Column(length = 50)
    private String especialidade;
}
