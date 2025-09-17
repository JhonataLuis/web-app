package com.bmt.webApp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bmt.webApp.dto.UsuarioDto;
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

    @GetMapping("/created")
    public String createUser(Model model){
        // Cria um novo objeto Usuario para o formulário
        Usuario user = new Usuario();
        model.addAttribute("user", user);
        model.addAttribute("funcoes", Funcao.values());
        return "usuario/create";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute Usuario user,
                           BindingResult result, RedirectAttributes redirect,
                           Model model) {

        // Verifica se há erros de validação
        if(user.getId() == null && (user.getPassword() == null || user.getPassword().isEmpty())) {
            // Se o ID não for nulo, significa que o usuário já existe
            result.rejectValue("password", "password.empty", "Senha é obrigatória.");
        }

        if(result.hasErrors()){
            // Retorna para a página de criação, mantendo os dados e os erros
            model.addAttribute("funcoes", Funcao.values());
            return "usuario/create";
        }

        try {
            // Cria o usuário através do serviço
            userService.createUser(user);
        } catch (IllegalArgumentException e) {
            // Se o usuário já existir, adiciona um erro ao resultado
            result.rejectValue("email", "error.email", e.getMessage());
            model.addAttribute("funcoes", Funcao.values());
            return "usuario/create";
        }
        // Se a criação for bem-sucedida, adiciona uma mensagem de sucesso
        redirect.addFlashAttribute("successMessage", "Employer cadastrado com Sucesso!");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        // Busca o usuário pelo ID
        Usuario user = userService.findById(id);
        if (user == null) {
            model.addAttribute("errorMessage", "Employer não encontrado.");
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        model.addAttribute("funcoes", Funcao.values());
        return "usuario/edit";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UsuarioDto userDto,
                             BindingResult result, 
                             RedirectAttributes redirect,
                             Model model) {

        // Verifica se há erros de validação
        if(result.hasErrors()){

            //Exibe os erros no console para depuração
            System.out.println("Erros de validação encontrados: " + result.getAllErrors());
            // Retorna para a página de edição, mantendo os dados e os erros
            model.addAttribute("funcoes", Funcao.values());
            model.addAttribute("user", userDto);
            return "usuario/edit";
        }

        try {
            // Atualiza o usuário através do serviço
            userService.updateUser(userDto);
            redirect.addFlashAttribute("successMessage", "Employer atualizado com Sucesso!");
        } catch (IllegalArgumentException e) {
            // Se o usuário não existir, adiciona um erro ao resultado
            result.rejectValue("email", "error.email", e.getMessage());
            model.addAttribute("funcoes", Funcao.values());
            model.addAttribute("user", userDto);
            return "usuario/edit";
        }
        
        // Se a atualização for bem-sucedida, adiciona uma mensagem de sucesso
        redirect.addFlashAttribute("successMessage", "Employer atualizado com Sucesso!");
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirect) {
        try {
            // Deleta o usuário através do serviço
            userService.deleteUser(id);
            redirect.addFlashAttribute("successMessage", "Employer deletado com Sucesso!");
        } catch (IllegalArgumentException e) {
            // Se o usuário não existir, adiciona uma mensagem de erro
            redirect.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }
    
}
