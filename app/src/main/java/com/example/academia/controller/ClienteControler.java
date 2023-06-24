package com.example.academia.controller;

import com.example.academia.model.Cliente;
import com.example.academia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping(path = "/cliente")
public class ClienteControler {

    @Autowired
    private ClienteRepository dao;

    @GetMapping("/cadastrar")
    public String cadastrarCliente(Model model) {
        model.addAttribute("cliente",new Cliente());
        return "";
    }

    @GetMapping("/Cliente/{id}")
    public String findCliente(Model model, @PathVariable Long id) {
        Cliente cliente = dao.findById(Math.toIntExact(id)).get();

        model.addAttribute("cliente",cliente);
        return "";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCliente(Model model, @PathVariable Long id) {
        dao.deleteById(Math.toIntExact(id));

        return "";
    }

    @PostMapping("/save")
    public String salvarCliente(@ModelAttribute Cliente cliente, Model model) {
        dao.save(cliente);

        return "";
    }

    @GetMapping("/listar")
    public String listarClientes(Model model) {

        List<Cliente> listCliente = (List<Cliente>) dao.findAll();
        model.addAttribute("clientes", listCliente);
        return "";
    }

}
