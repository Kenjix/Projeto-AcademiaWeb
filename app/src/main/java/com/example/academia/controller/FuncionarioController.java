package com.example.academia.controller;

import com.example.academia.model.Cliente;
import com.example.academia.model.Funcionario;
import com.example.academia.repository.ClienteRepository;
import com.example.academia.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping(path = "/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository dao;

    @GetMapping("/cadastrar")
    public String cadastrarFuncionario(Model model) {
        model.addAttribute("funcionario",new Funcionario());
        return "";
    }

    @GetMapping("/Funcionario/{id}")
    public String findFuncionario(Model model, @PathVariable Long id) {
        Funcionario funcionario = dao.findById(Math.toIntExact(id)).get();

        model.addAttribute("funcionario",funcionario);
        return "";
    }

    @GetMapping("/excluir/{id}")
    public String excluirFuncionario(Model model, @PathVariable Long id) {
        dao.deleteById(Math.toIntExact(id));

        return "";
    }

    @PostMapping("/save")
    public String salvarFuncionario(@ModelAttribute Funcionario funcionario, Model model) {
        dao.save(funcionario);

        return "";
    }

    @GetMapping("/listar")
    public String listarFuncionarios(Model model) {
        List<Funcionario> listFuncionario = (List<Funcionario>) dao.findAll();
        model.addAttribute("funcionarios", listFuncionario);

        return "";
    }
}
