package com.example.academia.controller;


import com.example.academia.model.Cliente;
import com.example.academia.repository.ClienteRepository;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteControler {
    private final ClienteRepository dao;

    public ClienteControler(ClienteRepository dao) {
        this.dao = dao;
    }

    //rota para o preenchimento do cadastro (formulário)
    @GetMapping("/cadastrar")
    public String cadastrarCliente(Model model) {
        model.addAttribute("cliente",new Cliente());
        return "formClienteCadastro";
    }

    @GetMapping("/construcao")
    public String showConstrucaoPage() {
        return "construcao";
    }

    @GetMapping("/meustreinos")
    public String showTreinosPage() {
        return "meustreinos";
    }

    //rota para pegar cliente especifico (detalhes)
    @GetMapping("/Cliente/{id}")
    public String findCliente(Model model, @PathVariable int id) {
        Cliente cliente = dao.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);

        return "editPerfil";
    }

    //rota para excluir cliente
    @GetMapping("/excluir/{id}")
    public String excluirCliente(Model model, @PathVariable Long id) {
        dao.deleteById(Math.toIntExact(id));

        List<Cliente> listaCliente = (List<Cliente>) dao.findAll();
        model.addAttribute("cliente", listaCliente);
        return "redirect:/cliente/listar";
    }

    //rota para salvar os dados do cliente preenchidos do form Cadastro
    /*@PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute("cliente") @Valid Cliente cliente,
                                BindingResult bindingResult,
                                @RequestParam("foto") MultipartFile fotoFile,
                                Model model) throws IOException {
        String telefone = cliente.getTelefone();
        telefone = telefone.replaceAll("[\\s()-]", "");
        cliente.setTelefone(telefone);
        String celular = cliente.getCelular();
        celular = celular.replaceAll("[\\s()-]", "");
        cliente.setCelular(celular);
        String cpf = cliente.getCpf();
        cpf = cpf.replaceAll("\\D", "");
        cliente.setCpf(cpf);
        try {
            //le os bytes da imagem do MultipartFile
            byte[] fotoBytes = fotoFile.getBytes();
            //carreganga a imagem em um objeto BufferedImage
            BufferedImage imagemOriginal = ImageIO.read(new ByteArrayInputStream(fotoBytes));
            //definine as dimensoes desejadas para o redimensionamento
            int larguraDesejada = 640;
            int alturaDesejada = 640;
            //calcula as dimensoes proporcionais para manter a proporção
            int larguraOriginal = imagemOriginal.getWidth();
            int alturaOriginal = imagemOriginal.getHeight();
            int novaLargura, novaAltura;
            if (larguraOriginal > alturaOriginal) {
                novaLargura = larguraDesejada;
                novaAltura = (int) (alturaOriginal / (larguraOriginal / (double) larguraDesejada));
            } else {
                novaAltura = alturaDesejada;
                novaLargura = (int) (larguraOriginal / (alturaOriginal / (double) alturaDesejada));
            }
            //cria uma nova imagem redimensionada
            BufferedImage imagemRedimensionada = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imagemRedimensionada.createGraphics();
            g.drawImage(imagemOriginal, 0, 0, novaLargura, novaAltura, null);
            g.dispose();
            //converte a imagem redimensionada para bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagemRedimensionada, "jpg", baos);
            byte[] fotoRedimensionadaBytes = baos.toByteArray();
            //converte para Base64
            String base64 = Base64.getEncoder().encodeToString(fotoRedimensionadaBytes);
            //armazena no objeto cliente
            cliente.setFoto(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dao.save(cliente);
        List<Cliente> listaCliente = (List<Cliente>) dao.findAll();
        model.addAttribute("clientes", listaCliente);
        return "redirect:/cliente/listar";
    }*/

    @PostMapping("salvar")
    public String salvarUser() {
        return null;
    }

    //rota para atualizar cliente
    @PostMapping ("/atualizar/{id}")
    public String atualizarCliente(@PathVariable int id, @ModelAttribute Cliente cliente, Model model) {
        //usa o ID recebido para buscar o cliente existente no banco de dados e atualiza-lo
        Cliente clienteExistente = dao.findById(id).orElse(null);
        if (clienteExistente != null) {
            String telefone = cliente.getTelefone();
            telefone = telefone.replaceAll("[\\s()-]", "");
            String celular = cliente.getCelular();
            celular = celular.replaceAll("[\\s()-]", "");
            String cpf = cliente.getCpf();
            cpf = cpf.replaceAll("\\D", "");
            clienteExistente.setNome(cliente.getNome());
            clienteExistente.setCpf(cpf);
            clienteExistente.setDataNasc(cliente.getDataNasc());
            clienteExistente.setEmail(cliente.getEmail());
            clienteExistente.setTelefone(telefone);
            clienteExistente.setCelular(celular);
            clienteExistente.setPeso(cliente.getPeso());
            clienteExistente.setAltura(cliente.getAltura());
            clienteExistente.setObjetivo(cliente.getObjetivo());
            clienteExistente.setObservacoes(cliente.getObservacoes());
            dao.save(clienteExistente);

            List<Cliente> listaCliente = (List<Cliente>) dao.findAll();
            model.addAttribute("clientes", listaCliente);
        }

        return "redirect:/cliente/listar";
    }

    //rota para ver ficha / detalhes do cliente
    @GetMapping("/ficha/{id}")
    public String exibirFichaCliente(@PathVariable int id, Model model) {
        // Use o ID recebido para buscar o cliente no banco de dados
        Cliente cliente = dao.findById(id).orElse(null);

        if (cliente != null) {
            model.addAttribute("cliente", cliente);
        }

        return "fichaCliente";
    }

    //rota para listar todos os clientes
    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> listCliente = (List<Cliente>) dao.findAll();
        model.addAttribute("clientes", listCliente);

        return "ListagemClientes";
    }

}
