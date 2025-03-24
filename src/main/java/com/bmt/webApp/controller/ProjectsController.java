package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmt.webApp.repository.ProjectsRepository;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsRepository projectsRepository;

    @GetMapping({"", "/"})
    public String getProjects(Model model){
        var projects = projectsRepository.findAll();
        model.addAttribute("projects", projects);
        return "projects/index";
    }
}
