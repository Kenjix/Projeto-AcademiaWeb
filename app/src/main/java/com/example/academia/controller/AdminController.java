package com.example.academia.controller;

import com.example.academia.model.User;
import com.example.academia.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
