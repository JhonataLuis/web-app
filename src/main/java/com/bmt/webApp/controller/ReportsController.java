package com.bmt.webApp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.model.Project;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.service.ReportService;



@Controller
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ProjectsRepository projectsRepository;


    @GetMapping({"", "/"})  
    public String getReportsPage(@RequestParam(value = "periodo", defaultValue = "30") int periodo,
                                 @RequestParam(value = "projectId", required = false) Long projectId,
                                 Model model) {

        Double produtividade = reportService.calcularProdutividade();
        Map<ProjectStatus, Long> statusProjetos = reportService.contarProjetosPorStatus();
        Map<String, Long> totalTarefasPorMembro = reportService.contarTarefasPorMembro();
        List<Project> projects = projectsRepository.findAll();

        // Limita para mostrar somente 4 projetos em andamento no dashboard reports
        if(projects.size() > 4){
            projects = projects.subList(0,4);
        }

        // Estatísticas principais
         model.addAttribute("produtividade", produtividade);
         model.addAttribute("taxaConclusao", reportService.calcularTaxaConclusao());
         model.addAttribute("tarefasAtrasadas", reportService.contarTarefasAtrasadas());
         model.addAttribute("riscos", reportService.contarProjetosEmRisco(java.time.LocalDateTime.now().minusDays(periodo), projectId));
         model.addAttribute("statusProjetos", statusProjetos);
         model.addAttribute("totalTarefasPorMembro", totalTarefasPorMembro);
         model.addAttribute("projects", projects);

        return "relatorios/reports";
    }
}
