package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bmt.webApp.repository.ProjectsRepository;



@Controller
public class HomeController {

    @Autowired
    ProjectsRepository projectsRepository;

    @GetMapping("/")
    public String home(Model model){

        long totalProjects = projectsRepository.count();

        //model para mostrar o total de projetos no dashboard (index.html)
        model.addAttribute("totalProjects", totalProjects);

        return "index";
    }
}
