package com.bmt.webApp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bmt.webApp.enums.Funcao;
import com.bmt.webApp.model.Usuario;
import com.bmt.webApp.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsuarioController {


    @Autowired
    private UsuarioService userService;


    @GetMapping({"", "/"})
    public String getUser(Model model){

        model.addAttribute("users", userService.listUsers());
        model.addAttribute("today", LocalDateTime.now());
        return "usuario/index";
    }

    @GetMapping("/create")
    public String createUser(Model model){
        Usuario user = new Usuario();
        model.addAttribute("users", user);
        return "usuario/create";
    }

    @PostMapping("/create/save")
    public String saveUser(@Valid @ModelAttribute Usuario users,
                           BindingResult result, RedirectAttributes redirect,
                           Model model) {

        if(result.hasErrors()){
            return "users/index";
        }

        try {
            // Cria o usuário através do serviço
            userService.createUser(users);
        } catch (IllegalArgumentException e) {
            // Se o usuário já existir, adiciona um erro ao resultado
            result.rejectValue("email", "error.email", e.getMessage());
            model.addAttribute("funcoes", Funcao.values());
            return "usuario/create";
        }
        // Se a criação for bem-sucedida, adiciona uma mensagem de sucesso
        redirect.addFlashAttribute("successMessage", "Usuário criado com Sucesso!");
        return "users/index";
    }
}
