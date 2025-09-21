package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.UserRepository;



@Controller
public class HomeController {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model){

        long totalProjects = projectsRepository.count();
        long status = projectsRepository.countByStatus("Concluído");

        
        model.addAttribute("totalProjects", totalProjects);//total de projetos no dashboard (index.html)
        model.addAttribute("totalMembers", userRepository.count());//total de membros da equipe
        model.addAttribute("totalCompletedProjects", status); //total de projetos com status "Concluído"

        return "index";
    }
}
