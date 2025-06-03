package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmt.webApp.model.ProjectDto;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.service.ProjectService;
import com.bmt.webApp.service.TarefaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TarefaService tarefaService;

    @GetMapping({"", "/"})
    public String getProjects(Model model){
        //var projects = projectsRepository.findAll();
        var projects = projectService.listProject();
        model.addAttribute("projects", projects);
        return "projects/index";
    }

    @PostMapping("/created")
    public String createdProject(@Valid @ModelAttribute ProjectDto projectDto, BindingResult result){

        projectService.createProject(projectDto);
        return "redirect:/projects";
    }

    @GetMapping("/details/{id}")
    public String detailProject(Model model, @PathVariable Long id){

        var project = projectsRepository.findById(id).orElse(null);
        if(project == null){
            return "projects/index";
        }

        projectService.detailProject(id);
        model.addAttribute("project", project);
        model.addAttribute("tarefas", tarefaService.listarTarefasPorProjeto(id));

        return "projects/details";
    }

    
}
