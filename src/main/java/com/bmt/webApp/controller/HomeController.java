package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.TarefaService;



@Controller
public class HomeController {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TarefaService tarefaService;
    
    @GetMapping("/")
    public String home(Model model){

        Long totalProjects = projectsRepository.count();
        Long totalCompletedProjects = projectsRepository.countByStatus(ProjectStatus.CONCLUÍDO); //Aqui você pode passar o enum ProjectStatus.COMPLETED se estiver usando enums
        
        
        model.addAttribute("totalProjects", totalProjects);//total de projetos no dashboard (index.html)
        model.addAttribute("totalMembers", userRepository.count());//total de membros da equipe
        model.addAttribute("totalCompletedProjects", totalCompletedProjects); //total de projetos com status "Concluído"
        model.addAttribute("recentProjects", projectsRepository.findTop4ByOrderByIdDesc()); //4 últimos projetos cadastrados
        model.addAttribute("totalTarefasAtrasadas", tarefaService.contarTarefasAtrasadas());  // Placeholder para total de tarefas atrasadas
        
        return "index";
    }
}
