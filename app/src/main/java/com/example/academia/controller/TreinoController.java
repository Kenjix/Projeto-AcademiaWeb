package com.example.academia.controller;

import com.example.academia.model.DTO.TreinoRegisterDTO;
import com.example.academia.model.Exercicio;
import com.example.academia.model.Treino;
import com.example.academia.model.TreinoExercicio;
import com.example.academia.model.User;
import com.example.academia.repository.ExercicioRepository;
import com.example.academia.repository.TreinoRepository;
import com.example.academia.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/treino")
public class TreinoController {

    private final TreinoRepository treinoRepository;
    private final UserRepository userRepository;
    private final ExercicioRepository exercicioRepository;

    @Autowired
    public TreinoController(TreinoRepository treinoRepository, UserRepository userRepository, ExercicioRepository exercicioRepository) {
        this.treinoRepository = treinoRepository;
        this.userRepository = userRepository;
        this.exercicioRepository = exercicioRepository;
    }

    @GetMapping("/cadastrar")
    public String exibirFormCadastroTreino(Model model) {
        model.addAttribute("treino", new Treino());
        List<Exercicio> exercicios = exercicioRepository.findAll();
        model.addAttribute("exercicios", exercicios);
        return "formTreinoCadastro";
    }

    @PostMapping("/salvar")
    public String salvarTreino(@ModelAttribute @Valid TreinoRegisterDTO data, @RequestParam List<TreinoExercicio> exercicios) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            for (TreinoExercicio exercicio : exercicios) {
                System.out.println("Exercício - id: " + exercicio.getId());
                System.out.println("Exercício - Ordem: " + exercicio.getOrdem());
                System.out.println("Exercício - Série: " + exercicio.getSeries());
                System.out.println("Exercício - Repetição: " + exercicio.getRepeticao());
                System.out.println("Exercício - Carga: " + exercicio.getCarga());
            }
            User user = userRepository.findByEmail(authentication.getName());
            Treino treino = convertDTOToTreino(data);
            treino.setUser(user);
            treino.setDetalhesExercicios(exercicios);
            treinoRepository.save(treino);
            return "redirect:/";
        }
        return "redirect:/error403";
    }

    @GetMapping("/editar/{id}")
    public String editarTreino(@PathVariable Long id, Model model) {
        Treino treino = treinoRepository.findById(id).orElse(null);
        if (treino != null) {
            model.addAttribute("treino", treino);
        }
        return "formTreinoEdicao";
    }

    @PostMapping("/atualizar")
    public String atualizarTreino(@ModelAttribute TreinoRegisterDTO treinoRegisterDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authentication.getName());
            Treino treino = convertDTOToTreino(treinoRegisterDTO);
            treino.setUser(user);
            treinoRepository.save(treino);
            return "redirect:/treino/buscarPorUsuario/{userId}";
        }
        return "redirect:/error403";
    }

    @GetMapping("/excluir/{id}")
    public String excluirTreino(@PathVariable Long id) {
        treinoRepository.deleteById(id);
        return "redirect:/treino/buscarPorUsuario/{userId}";
    }

    @GetMapping("/meustreinos")
    public String buscarTreinosPorUsuario(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authentication.getName());
            List<Treino> treinos = treinoRepository.findByUserId(user.getId());
            model.addAttribute("treinos", treinos);
        }
        return "listagemTreinosUsuario";
    }

    private Treino convertDTOToTreino(TreinoRegisterDTO treinoRegisterDTO) {
        Treino treino = new Treino();
        treino.setTipoTreino(treinoRegisterDTO.tipoTreino());
        treino.setTrocaTreino(treinoRegisterDTO.trocaTreino());
        treino.setObservacao(treinoRegisterDTO.observacao());
        return treino;
    }
}
