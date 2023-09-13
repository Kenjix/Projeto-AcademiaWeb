package com.example.academia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "treinos")
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
    private List<TreinoExercicio> treinoExercicios;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}






