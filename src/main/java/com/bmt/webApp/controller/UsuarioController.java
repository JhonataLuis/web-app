package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmt.webApp.model.Usuario;
import com.bmt.webApp.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UserRepository userRepository;


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
}
