package com.example.academia.repository;

import com.example.academia.model.Cliente;
import com.example.academia.model.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
}
