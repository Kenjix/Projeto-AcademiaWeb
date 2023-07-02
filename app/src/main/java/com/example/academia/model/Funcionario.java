package com.example.academia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Funcionario {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 60)
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

