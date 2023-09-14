package com.example.academia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "treino_exercicio")
@Entity
public class TreinoExercicio {
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

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @PrePersist
    private void onCreate() {
        created = new Date();
        updated = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "treino_id")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    private Exercicio exercicio;
}
