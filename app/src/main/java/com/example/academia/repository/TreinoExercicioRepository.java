package com.example.academia.repository;

import com.example.academia.model.Exercicio;
import com.example.academia.model.TreinoExercicio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TreinoExercicioRepository extends CrudRepository<TreinoExercicio, Long> {

}