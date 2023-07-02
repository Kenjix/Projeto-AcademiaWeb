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
public class Cliente  {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
