package com.example.academia.controller;

import com.example.academia.model.DTO.UserDTO;
import com.example.academia.model.User;
import com.example.academia.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserDTO> findInactiveUsers() {
        try {
            List<User> inactiveUsers = userRepository.findByInativoAndRole();
            List<UserDTO> inactiveUserDTOs = inactiveUsers.stream()
                    .map(User::toDTO) // Converter User para UserDTO
                    .collect(Collectors.toList());
            return inactiveUserDTOs;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }
    @GetMapping("/clientes/ativos")
    @ResponseBody
    public List<UserDTO> findActiveUsers() {
        try {
            List<User> activeUsers = userRepository.findByAtivoAndRole();
            List<UserDTO> activeUserDTOs = activeUsers.stream()
                    .map(User::toDTO) // Converter User para UserDTO
                    .collect(Collectors.toList());
            return activeUserDTOs;
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }
}
