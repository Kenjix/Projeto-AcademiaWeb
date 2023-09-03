package com.example.academia.controller;

import com.example.academia.model.DTO.UserUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.academia.model.User;
import com.example.academia.repository.UserRepository;

import java.util.Optional;

@Controller
@RequestMapping(path = "/usuario")
public class UserController {

    private final UserRepository dao;

    public UserController(UserRepository dao) {
        this.dao = dao;
    }

    @GetMapping("/perfil")
    public String exibirPerfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = dao.findByEmail(authentication.getName());
            model.addAttribute("user", user);
            return "visualizarPerfil";
        }
        return "redirect:/error403";
    }

    @GetMapping("/perfil/atualizar")
    public String editarPerfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = dao.findByEmail(authentication.getName());
            model.addAttribute("user", user);
            return "editPerfil";
        }
        return "redirect:/error403";
    }


    @PostMapping("/atualizar")
    public String atualizarPerfil(@ModelAttribute @Valid UserUpdateDTO data, Authentication authentication) {
        try {
            if (authentication.isAuthenticated()) {
                User user = dao.findByEmail(authentication.getName());
                if (!user.getId().equals(data.userId())) {
                    return "redirect:/error403";
                }
                user.setNome(data.nome());
                user.setCpf(data.cpf().replaceAll("[\\s.-]",""));
                user.setDataNasc(data.dataNasc());
                user.setTelefone(data.telefone().replaceAll("[\\s()-]",""));
                user.setCelular(data.celular().replaceAll("[\\s()-]",""));
                user.setPeso(data.peso());
                user.setAltura(data.altura());
                user.setObjetivo(data.objetivo());
                user.setObservacoes(data.observacoes());
                dao.save(user);
                return "redirect:/usuario/perfil";
            }
            return "redirect:/error403";
        } catch (Exception e) {
            return "redirect:/error500";
        }
    }

}