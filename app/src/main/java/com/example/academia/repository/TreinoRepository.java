package com.example.academia.repository;

import com.example.academia.model.Treino;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreinoRepository extends CrudRepository<Treino, Long> {
    List<Treino> findByUserId(Long userId);
}
