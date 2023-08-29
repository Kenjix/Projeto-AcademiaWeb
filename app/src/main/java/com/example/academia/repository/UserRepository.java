package com.example.academia.repository;

import com.example.academia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u.matricula FROM User u ORDER BY u.created DESC LIMIT 1")
    String findLastMatricula();
}