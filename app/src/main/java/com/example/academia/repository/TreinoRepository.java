package com.example.academia.repository;

import com.example.academia.model.Treino;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreinoRepository extends CrudRepository<Treino, Integer> {
    @Query("SELECT t FROM Treino t WHERE t.clienteID.id = :userId")
    List<Treino> findByUserId(@Param("userId") Long userId);
}
