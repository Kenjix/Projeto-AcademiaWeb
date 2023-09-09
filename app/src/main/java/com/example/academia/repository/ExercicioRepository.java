package com.example.academia.repository;

import com.example.academia.model.Exercicio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExercicioRepository extends CrudRepository<Exercicio, Long> {
    List<Exercicio> findAll();
}
