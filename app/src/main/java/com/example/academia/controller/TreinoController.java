package com.example.academia.controller;

import com.example.academia.model.DTO.TreinoDTO;
import com.example.academia.model.Exercicio;
import com.example.academia.model.Treino;
import com.example.academia.model.User;
import com.example.academia.repository.TreinoRepository;
import com.example.academia.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/treino")
public class TreinoController {

    private final TreinoRepository treinoRepository;
    private final UserRepository userRepository;

    @Autowired
    public TreinoController(TreinoRepository treinoRepository, UserRepository userRepository) {
        this.treinoRepository = treinoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/cadastrar")
    public String cadastrarTreino(Model model) {
        model.addAttribute("treino", new Treino());
        return "formTreinoCadastro";
    }

    @PostMapping("/salvar")
    public String salvarTreino(@ModelAttribute @Valid TreinoDTO treinoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authentication.getName());
            Treino treino = convertDTOToTreino(treinoDTO);
            treino.setUser(user);
            treinoRepository.save(treino);
            return "redirect:/treino/buscarPorUsuario/{userId}";
        }
        return "redirect:/error403";
    }

    @GetMapping("/editar/{id}")
    public String editarTreino(@PathVariable int id, Model model) {
        Treino treino = treinoRepository.findById(id).orElse(null);
        if (treino != null) {
            model.addAttribute("treino", treino);
        }
        return "formTreinoEdicao";
    }

    @PostMapping("/atualizar")
    public String atualizarTreino(@ModelAttribute TreinoDTO treinoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authentication.getName());
            Treino treino = convertDTOToTreino(treinoDTO);
            treino.setUser(user);
            treinoRepository.save(treino);
            return "redirect:/treino/buscarPorUsuario/{userId}";
        }
        return "redirect:/error403";
    }

    @GetMapping("/excluir/{id}")
    public String excluirTreino(@PathVariable int id) {
        treinoRepository.deleteById(id);
        return "redirect:/treino/buscarPorUsuario/{userId}";
    }

    @GetMapping("/buscarPorUsuario/{userId}")
    public String buscarTreinosPorUsuario(@PathVariable Long userId, Model model) {
        List<Treino> treinos = treinoRepository.findByUserId(userId);
        model.addAttribute("treinos", treinos);
        return "listagemTreinosUsuario";
    }

    private Treino convertDTOToTreino(TreinoDTO treinoDTO) {
        Treino treino = new Treino();
        treino.setId(treinoDTO.id());
        treino.setOrdem(treinoDTO.ordem());
        treino.setSeries(treinoDTO.series());
        treino.setRepeticao(treinoDTO.repeticao());
        treino.setCarga(treinoDTO.carga());
        treino.setTipoTreino(treinoDTO.tipoTreino());
        treino.setTrocaTreino(treinoDTO.trocaTreino());
        treino.setObservacao(treinoDTO.observacao());
        treino.setExercicioID(treinoDTO.exercicioId());

        return treino;
    }
}
