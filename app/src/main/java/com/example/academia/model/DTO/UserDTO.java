package com.example.academia.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String matricula;
    private String email;
    private String celular;
}
