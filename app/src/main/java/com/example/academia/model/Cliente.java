package com.example.academia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Getter
@Setter
public class Cliente  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(unique = true, nullable = false)
    private long matricula;

    @Column(length = 12)
    private String telefone;

    @Column(nullable = false, length = 11)
    private String celular;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNasc;

    @Column(nullable = false)
    private float peso;

    @Column(nullable = false)
    private float altura;

    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    @Column(length = 80)
    private String objetivo;

    @Column
    private boolean ativo;

    @Lob
    private byte[] foto;

    @Lob
    private String observacoes;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    @PrePersist
    private void onCreate() {
        updated = new Date();
        dataInicio = new Date();
        ativo = true;
    }
}
