package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmt.webApp.repository.ClienteRepository;

@Controller
@RequestMapping("/clients")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping({"", "/"})
    public String getClients(Model model){
        var clients = clienteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("clients", clients);
        return "clients/index";
    }
}
