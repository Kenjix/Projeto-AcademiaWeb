package com.example.academia.controller;

import com.example.academia.model.*;
import com.example.academia.model.DTO.TreinoRegisterDTO;
import com.example.academia.repository.ExercicioRepository;
import com.example.academia.repository.TreinoExercicioRepository;
import com.example.academia.repository.TreinoRepository;
import com.example.academia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/treino")
public class TreinoController {

    private final TreinoRepository treinoRepository;
    private final UserRepository userRepository;
    private final ExercicioRepository exercicioRepository;
    private final TreinoExercicioRepository treinoExercicioRepository;

    @Autowired
    public TreinoController(TreinoRepository treinoRepository, UserRepository userRepository, ExercicioRepository exercicioRepository, TreinoExercicioRepository treinoExercicioRepository) {
        this.treinoRepository = treinoRepository;
        this.userRepository = userRepository;
        this.exercicioRepository = exercicioRepository;
        this.treinoExercicioRepository = treinoExercicioRepository;
    }

    @GetMapping("/cadastrar")
    public String exibirFormCadastroTreino(Model model) {
        List<Exercicio> exercicios = exercicioRepository.findAll();
        TreinoListWrapper treinoListWrapper = new TreinoListWrapper();
        treinoListWrapper.setExerciciosList(new ArrayList<>());
        model.addAttribute("treinoListWrapper", treinoListWrapper);
        model.addAttribute("exercicios", exercicios);
        return "formTreinoCadastro";
    }
    @PostMapping("/salvar")
    public String salvarTreino(@ModelAttribute TreinoRegisterDTO treinoRegisterDTO,
                               @ModelAttribute TreinoListWrapper treinoListWrapper) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authentication.getName());
            Treino treino = new Treino();
            treino.setTipoTreino(treinoRegisterDTO.getTipoTreino());
            treino.setTrocaTreino(treinoRegisterDTO.getTrocaTreino());
            treino.setObservacao(treinoRegisterDTO.getObservacao());
            treino.setUser(user);
            Treino savedTreino = treinoRepository.save(treino);
            List<TreinoExercicio> exercicios = convertExerciciosHolderList(treinoListWrapper.getExerciciosList());
            for (TreinoExercicio exercicio : exercicios) {
                exercicio.setTreino(savedTreino);
            }
            treinoExercicioRepository.saveAll(exercicios);
            return "redirect:/";
        }
        return "redirect:/error403";
    }
    private List<TreinoExercicio> convertExerciciosHolderList(List<ExerciciosHolder> exerciciosHolderList) {
        List<TreinoExercicio> treinoExercicios = new ArrayList<>();
        for (ExerciciosHolder exerciciosHolder : exerciciosHolderList) {
            TreinoExercicio treinoExercicio = new TreinoExercicio();
            treinoExercicio.setOrdem(exerciciosHolder.getOrdem());
            treinoExercicio.setSeries(exerciciosHolder.getSeries());
            treinoExercicio.setRepeticao(exerciciosHolder.getRepeticao());
            treinoExercicio.setCarga(exerciciosHolder.getCarga());
            Exercicio exercicio = exercicioRepository.findById(exerciciosHolder.getExercicioId()).orElse(null);
            treinoExercicio.setExercicio(exercicio);
            treinoExercicios.add(treinoExercicio);
        }
        return treinoExercicios;
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
        //treino.setTipoTreino(treinoRegisterDTO.tipoTreino());
        //treino.setTrocaTreino(treinoRegisterDTO.trocaTreino());
        //treino.setObservacao(treinoRegisterDTO.observacao());
        return treino;
    }
}
