package com.example.academia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Cliente  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 60)
    private String nome;

    @Column(unique = true, nullable = false, length = 15)
    private String cpf;

    @Column(unique = true, nullable = false, length = 10)
    private String matricula;

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

    @Column(columnDefinition = "TEXT")
    private String foto;

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
        matricula = generateUniqueMatricula();
    }

    private String generateUniqueMatricula() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 10);
    }
}
