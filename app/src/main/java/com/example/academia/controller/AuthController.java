package com.example.academia.controller;

import com.example.academia.model.DTO.AuthDTO;
import com.example.academia.model.DTO.LoginResponseDTO;
import com.example.academia.model.DTO.UserRegisterDTO;
import com.example.academia.enuns.UserRole;
import com.example.academia.exceptions.EmailExistsException;
import com.example.academia.model.User;
import com.example.academia.repository.UserRepository;
import com.example.academia.services.MatriculaService;
import com.example.academia.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final MatriculaService matriculaService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            TokenService tokenService, MatriculaService matriculaService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.matriculaService = matriculaService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserRegisterDTO data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error400"; // Página de erro, se houver erros de validação
        } else if (userRepository.findByEmail(data.email()) != null) {
            throw new EmailExistsException("Email já cadastrado");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, UserRole.USER,
                matriculaService.gerarProximaMatricula(), data.nome(),
                data.celular().replaceAll("[\\s()-]", ""), data.dataNasc());
        userRepository.save(newUser);

        return "login"; // Redireciona para a página de login após o registro bem-sucedido
    }
}