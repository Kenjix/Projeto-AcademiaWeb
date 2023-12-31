package com.example.academia.repository;

import com.example.academia.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.ativo = true AND u.role = 2")
    List<User> findByAtivoAndRole();
    @Query("SELECT u FROM User u WHERE u.ativo = false AND u.role = 2")
    List<User> findByInativoAndRole();
    @Query("SELECT u.matricula FROM User u ORDER BY u.created DESC LIMIT 1")
    String findLastMatricula();
    Optional<User> findById(Long id);
}