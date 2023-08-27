package com.example.academia.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String titulo;
    private String detalhe;
    private HttpStatus status;
    private ZonedDateTime horario;

    public ErrorResponse(String titulo, String detalhe, HttpStatus status, ZonedDateTime horario) {
        this.titulo = titulo;
        this.detalhe = detalhe;
        this.status = status;
        this.horario = horario;
    }

    public ErrorResponse(HttpStatus status, String detalhe) {
        this.status = status;
        this.detalhe = detalhe;


    }
}