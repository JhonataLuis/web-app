package com.bmt.webApp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bmt.webApp.dto.ClienteDto;
import com.bmt.webApp.model.Cliente;
import com.bmt.webApp.repository.ClienteRepository;
import com.bmt.webApp.service.ClienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClienteController {

    public static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

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
    public String createClient(@Valid @ModelAttribute ClienteDto clienteDto, 
                               BindingResult result, RedirectAttributes redirect){

        if(clienteRepository.findByEmail(clienteDto.getEmail()) != null){
            result.addError(
                new FieldError("clienteDto", "email", clienteDto.getEmail()
            , false, null, null, "Email address is already used")
            );//Verifica se o email já existe cadastrado no sistema, se houver retorna que já existe
        }

        if(result.hasErrors()){
            return "cliente/create";
        }
        clientService.CreateClient(clienteDto);
        redirect.addFlashAttribute("successMessage", "Cliente salvo com sucesso!");
        logger.info("Cliente criado no banco de dados ID{}");
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
                             BindingResult result, RedirectAttributes redirect){

        if(clientDto == null){
            return "redirect:/cliente";
        }

        if(result.hasErrors()){
            return "cliente/edit";
        }

        try{
            //may throw an exception if email is duplicated email should be unique in db
            clientService.clientUpdate(clientDto);
        }
        catch(Exception ex){
            result.addError(new FieldError("clienteDto", "email", clientDto.getEmail()
                , false, null, null, "Email address is already used")
            );

            return "cliente/edit";
        }
        model.addAttribute("client", clientDto);
        redirect.addFlashAttribute("successMessage","Cliente atualizado com Sucesso!");
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
    public String deleteClient(@RequestParam Long id, RedirectAttributes redirect){
        
        try{
            clientService.clientDelete(id);
            redirect.addFlashAttribute("successMessage", "Cliente removido com sucesso do Sistema!");
        } catch(IllegalArgumentException ex){
            redirect.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/clients";
    }
}
