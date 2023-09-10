package com.example.academia.controller;

import com.example.academia.model.User;
import com.example.academia.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/gerenciamento")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPage(Model model) {
        List<User> listUsers = (List<User>) userRepository.findByAtivoAndRole();
        model.addAttribute("users", listUsers);
        return "adminPage";
    }
    @GetMapping("/clientes/inativos")
    @ResponseBody
    public List<User> findInactiveUsers() {
        try {
            return userRepository.findByInativoAndRole();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }
    @GetMapping("/clientes/ativos")
    @ResponseBody
    public List<User> findActiveUsers() {
        try {
            return userRepository.findByAtivoAndRole();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }
}
