package com.example.academia.controller;

import com.example.academia.model.DTO.UserUpdateDTO;
import com.example.academia.services.ImageService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.academia.model.User;
import com.example.academia.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping(path = "/usuario")
public class UserController {
    private final UserRepository dao;
    private final ImageService imageService;

    public UserController(UserRepository dao, ImageService imageService) {
        this.dao = dao;
        this.imageService = imageService;
    }

    //rota para o preenchimento do cadastro (formulário)
    @GetMapping("/cadastrar")
    public String cadastrarCliente() {
        return "formClienteCadastro";
    }

    @GetMapping("/construcao")
    public String showConstrucaoPage() {
        return "construcao";
    }

    @GetMapping("/meustreinos")
    public String showTreinosPage() {
        return "listagemTreinosUsuario";
    }

    @GetMapping("/perfil")
    public String exibirPerfil(Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = dao.findByEmail(authentication.getName());
            byte[] foto = user.getFoto();
            if (foto != null && foto.length > 0) {
                foto = imageService.decompressImage(foto);
                String imagemBase64 = Base64.getEncoder().encodeToString(foto);
                model.addAttribute("imagemBase64", imagemBase64);
            } else {
                model.addAttribute("imagemPlaceholder", "/images/avatar_placeholder.png");
            }
            model.addAttribute("user", user);
            return "visualizarPerfil";
        }
        return "redirect:/error403";
    }

    @GetMapping("/perfil/atualizar")
    public String editarPerfil(Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = dao.findByEmail(authentication.getName());
            if (user.getFoto() != null && user.getFoto().length > 0) {
                byte[] foto = imageService.decompressImage(user.getFoto());
                String imagemBase64 = Base64.getEncoder().encodeToString(foto);
                model.addAttribute("imagemBase64", imagemBase64);
            } else {
                model.addAttribute("imagemPlaceholder", "/images/avatar_placeholder.png");
            }
            model.addAttribute("user", user);
            return "editPerfil";
        }
        return "redirect:/error403";
    }

    @PostMapping("/atualizar")
    public String atualizarPerfil(@ModelAttribute @Valid UserUpdateDTO data, Authentication authentication, @RequestParam("foto") MultipartFile foto) {
        try {
            if (authentication.isAuthenticated()) {
                User user = dao.findByEmail(authentication.getName());
                if (!user.getId().equals(data.userId())) {
                    return "redirect:/error403";
                }
                if (foto != null && !foto.isEmpty()) {
                    byte[] resizedImage = imageService.resizeImage(foto, 200, 200);
                    byte[] compressedImage = imageService.compressImage(resizedImage);
                    user.setFoto(compressedImage);
                }
                user.setNome(data.nome());
                user.setCpf(data.cpf().replaceAll("[\\s.-]", ""));
                user.setDataNasc(data.dataNasc());
                user.setTelefone(data.telefone().replaceAll("[\\s()-]", ""));
                user.setCelular(data.celular().replaceAll("[\\s()-]", ""));
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