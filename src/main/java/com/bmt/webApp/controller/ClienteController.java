package com.bmt.webApp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bmt.webApp.model.Cliente;
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

        if(clienteRepository.findByEmail(clienteDto.getEmail()) != null){
            result.addError(
                new FieldError("clienteDto", "email", clienteDto.getEmail()
            , false, null, null, "Email address is already used")
            );
        }

        if(result.hasErrors()){
            return "cliente/create";
        }

        Cliente client = new Cliente();
        client.setFirstName(clienteDto.getFirstName());
        client.setLastName(clienteDto.getLastName());
        client.setEmail(clienteDto.getEmail());
        client.setPhone(clienteDto.getPhone());
        client.setAddress(clienteDto.getAddress());
        client.setStatus(clienteDto.getStatus());
        client.setCreatedAt(new Date());

        clienteRepository.save(client);

        return "redirect:/cliente";
    }

    public String editClient(Model model, @RequestParam Long id){
        Cliente client = clienteRepository.findById(id).orElse(null);
        if(client == null){
            return "redirect:/cliente";
        }

        ClienteDto clientDto = new ClienteDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());
        clientDto.setStatus(client.getStatus());
        
        model.addAttribute("client", client);
        model.addAttribute("clientDto", clientDto);
        
        return "cliente/edit";
    }
}
