package com.example.academia.controller;

import com.example.academia.model.Cliente;
import com.example.academia.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping(path = "/cliente")
public class ClienteControler {

    private final ClienteRepository dao;

    public ClienteControler(ClienteRepository dao) {
        this.dao = dao;
    }

    @GetMapping("/cadastrar")
    public String cadastrarCliente(Model model) {
        model.addAttribute("cliente",new Cliente());
        return "login";
    }

    @GetMapping("/Cliente/{id}")
    public String findCliente(Model model, @PathVariable int id) {
        Cliente cliente = dao.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);

        return "AlterarCliente";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCliente(Model model, @PathVariable Long id) {
        dao.deleteById(Math.toIntExact(id));

        List<Cliente> listaCliente = (List<Cliente>) dao.findAll();
        model.addAttribute("cliente", listaCliente);
        return "ListagemClientes";
    }

    @PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute Cliente cliente, Model model) {
        String telefone = cliente.getTelefone();
        telefone = telefone.replaceAll("[\\s()-]", "");
        cliente.setTelefone(telefone);

        String celular = cliente.getCelular();
        celular = celular.replaceAll("[\\s()-]", "");
        cliente.setCelular(celular);
        
        dao.save(cliente);
        List<Cliente> listaCliente = (List<Cliente>) dao.findAll();
        model.addAttribute("clientes", listaCliente);
        return "ListagemClientes";
    }    
    

    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> listCliente = (List<Cliente>) dao.findAll();
        model.addAttribute("clientes", listCliente);

        return "ListagemClientes";
    }

}
