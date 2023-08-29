package com.example.academia.services;

import com.example.academia.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Calendar;

@Service
public class MatriculaService {

    private final UserRepository userRepository;

    public MatriculaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String gerarProximaMatricula() {
        String ultimaMatricula = userRepository.findLastMatricula();
        int proximoNumero;

        if (ultimaMatricula != null) {
            int numeroAtual = Integer.parseInt(ultimaMatricula.substring(4));
            proximoNumero = numeroAtual + 1;
        } else {
            proximoNumero = 1;
        }

        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        return String.format("%d%06d", anoAtual, proximoNumero);
    }
}