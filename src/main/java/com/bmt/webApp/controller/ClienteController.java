package com.bmt.webApp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bmt.webApp.service.ClienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClienteController {

    @Autowired
    private ClienteService clientService;

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping({"", "/"})
    public String getClients(Model model){
        var clients = clientService.buscarTodos();
        model.addAttribute("clients", clients);
        return "cliente/index";
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
        clientService.CreateClient(clienteDto);
        return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String editClient(Model model, @RequestParam Long id){
        Cliente client = clienteRepository.findById(id).orElse(null);
        if(client == null){
            return "redirect:/clients";
        }

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFirstName(client.getFirstName());
        clienteDto.setLastName(client.getLastName());
        clienteDto.setEmail(client.getEmail());
        clienteDto.setPhone(client.getPhone());
        clienteDto.setAddress(client.getAddress());
        clienteDto.setStatus(client.getStatus());
        
        model.addAttribute("client", client);
        model.addAttribute("clienteDto", clienteDto);

        return "cliente/edit";
    }

    @PostMapping("/edit")
    public String editClient(Model model, @RequestParam Long id, @Valid @ModelAttribute ClienteDto clientDto,
    BindingResult result){

        Cliente client = clienteRepository.findById(id).orElse(null);
        if(client == null){
            return "redirect:/cliente";
        }

        model.addAttribute("client", client);

        if(result.hasErrors()){
            return "cliente/edit";
        }

        //update client details
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setStatus(clientDto.getStatus());

        try{
            //may throw an exception if email is duplicated email should be unique in db
            clienteRepository.save(client);
            
        }
        catch(Exception ex){
            result.addError(new FieldError("clienteDto", "email", clientDto.getEmail()
                , false, null, null, "Email address is already used")
            );

            return "cliente/edit";
        }
        

        return "redirect:/clients";
    }

    @GetMapping("/details")
    public String detailsClient(Model model, @RequestParam Long id){

        Cliente client = clienteRepository.findById(id).orElse(null);
        if(client == null){
            return "redirect:/clients";
        }

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setFirstName(client.getFirstName());

        model.addAttribute("clienteDto", clienteDto);
        model.addAttribute("client", client);

        return "cliente/details";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam Long id){
        Cliente client = clienteRepository.findById(id).orElse(null);

        if(client != null){
            clienteRepository.delete(client);
        }

        return "redirect:/clients";
    }
}
