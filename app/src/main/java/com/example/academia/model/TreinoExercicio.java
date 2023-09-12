package com.example.academia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TreinoExercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "treino_id")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    private Exercicio exercicio;

    @Column(nullable = false)
    private int ordem;

    @Column(length = 20)
    private String series;

    @Column(nullable = false)
    private int repeticao;

    @Column(nullable = false)
    private int carga;

    public TreinoExercicio(Exercicio exercicio, int ordem, String series, int repeticao, int carga) {
        this.exercicio = exercicio;
        this.ordem = ordem;
        this.series = series;
        this.repeticao = repeticao;
        this.carga = carga;
    }
}
