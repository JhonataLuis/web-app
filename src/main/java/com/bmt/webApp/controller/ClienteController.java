package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmt.webApp.model.ClienteDto;
import com.bmt.webApp.repository.ClienteRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping({"", "/"})
    public String getClients(Model model){
        var clients = clienteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("clients", clients);
        return "cliente/cliente";
    }

    @GetMapping("/create")
    public String CreateClient(Model model){
        ClienteDto clienteDto = new ClienteDto();
        model.addAttribute("clienteDto", clienteDto);
        return "cliente/create";
    }

    @PostMapping("/create")
    public String createClient(@Valid @ModelAttribute ClienteDto clienteDto, BindingResult result){
        return "redirect:/cliente";
    }
}
