package com.bmt.webApp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.service.ReportService;



@Controller
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;


    @GetMapping({"", "/"})  
    public String getReportsPage(@RequestParam(value = "periodo", defaultValue = "30") int periodo,
                                 @RequestParam(value = "projectId", required = false) Long projectId,
                                 Model model) {

        Double produtividade = reportService.calcularProdutividade();
        Map<ProjectStatus, Long> statusProjetos = reportService.contarProjetosPorStatus();

        // Estatísticas principais
         model.addAttribute("statusProjetos", statusProjetos);
         model.addAttribute("produtividade", produtividade);
         model.addAttribute("taxaConclusao", reportService.calcularTaxaConclusao());
         model.addAttribute("tarefasAtrasadas", reportService.contarTarefasAtrasadas());
         model.addAttribute("riscos", reportService.contarProjetosEmRisco(java.time.LocalDateTime.now().minusDays(periodo), projectId));
         

        return "relatorios/reports";
    }
}
