package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bmt.webApp.model.Usuario;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsuarioService userService;


    @GetMapping({"", "/"})
    public String getUser(Model model){
        var user = userRepository.findAll();
        model.addAttribute("users", user);
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
                           BindingResult result, RedirectAttributes redirect){

        if(result.hasErrors()){
            return "users/index";
        }

        userService.createUser(users);
        redirect.addFlashAttribute("successMessage", "Usuário criado com Sucesso!");
        return "users/index";
    }
}
