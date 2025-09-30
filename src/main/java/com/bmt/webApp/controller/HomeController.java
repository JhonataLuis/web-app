package com.bmt.webApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bmt.webApp.dto.ProjectDto;
import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.ProjectService;
import com.bmt.webApp.service.TarefaService;



@Controller
public class HomeController {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TarefaService tarefaService;

    @Autowired
    ProjectService projectService;
    
    @GetMapping("/")
    public String home(Model model){

        Long totalProjects = projectsRepository.count();
        Long totalCompletedProjects = projectsRepository.countByStatus(ProjectStatus.CONCLUIDO); //Aqui você pode passar o enum ProjectStatus.COMPLETED se estiver usando enums
        List<ProjectDto> proximosPrazos = projectService.buscarProjetosComProximosPrazos(); //método para buscar projetos com prazos próximos (7 dias) dashboard (index.html)
        
        
        model.addAttribute("totalProjects", totalProjects);//total de projetos no dashboard (index.html)
        model.addAttribute("totalMembers", userRepository.count());//total de membros da equipe
        model.addAttribute("totalCompletedProjects", totalCompletedProjects); //total de projetos com status "Concluído" no dashboard (index.html)
        model.addAttribute("recentProjects", projectsRepository.findTop4ByOrderByIdDesc()); //4 últimos projetos cadastrados no sistema (dashboard)
        model.addAttribute("totalTarefasAtrasadas", tarefaService.contarTarefasAtrasadas()); //total de tarefas atrasadas no sistema (dashboard)
        model.addAttribute("proximosPrazos", proximosPrazos); //projetos com prazos próximos (dashboard)
        
        return "index";
    }
}
