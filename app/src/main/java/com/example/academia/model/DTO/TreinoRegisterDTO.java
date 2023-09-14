package com.example.academia.model.DTO;

import com.example.academia.model.ExerciciosHolder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class TreinoRegisterDTO {
    private String tipoTreino;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date trocaTreino;

    private String observacao;
    private List<ExerciciosHolder> exerciciosList;

    // Construtor padrão
    public TreinoRegisterDTO() {
    }

    // Construtor personalizado
    public TreinoRegisterDTO(String tipoTreino, Date trocaTreino, String observacao, List<ExerciciosHolder> exerciciosList){
        this.tipoTreino = tipoTreino;
        this.trocaTreino = trocaTreino;
        this.observacao = observacao;
        this.exerciciosList = exerciciosList;
    }
}
