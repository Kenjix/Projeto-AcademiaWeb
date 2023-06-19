package com.example.academia.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private long matricula;

    private String telefone;
    private String celular;
    private String email;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNasc;

    @Column(nullable = false)
    private float peso;

    @Column(nullable = false)
    private float altura;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    @Column(length = 80)
    private String objetivo;

    @Column(columnDefinition = "BIT DEFAULT 1")
    private boolean ativo;

    @Lob
    private byte[] foto;

    @Lob
    private String observacoes;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;


}
