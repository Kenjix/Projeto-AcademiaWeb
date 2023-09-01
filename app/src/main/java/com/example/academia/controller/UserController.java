package com.example.academia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.academia.model.User;
import com.example.academia.repository.UserRepository;

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
            return "editPerfil";
        }
        return "redirect:/alguma_pagina_de_erro1";
    }

    @GetMapping("/{id}")
    public String exibirUser(@PathVariable Long id, Model model) {
        User user = dao.findById(id).orElse(null);

        if (user != null) {
            model.addAttribute("user", user);
            return "editPerfil";
        } else {
            return "redirect:/alguma_pagina_de_erro2";
        }
    }
}