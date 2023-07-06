package com.example.academia.repository;

import com.example.academia.model.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
}
