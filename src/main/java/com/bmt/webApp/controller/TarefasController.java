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

import com.bmt.webApp.model.TarefaDto;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.service.TarefaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private ProjectsRepository pRepository;

    @GetMapping("/create/{id}")
    public String CreateTarefa(Model model, @PathVariable Long id){
        TarefaDto tarefDto = new TarefaDto();
        var projectId = pRepository.findById(id).orElse(null);
        if(projectId == null){
            return "projects/index";
        }
        
        model.addAttribute("projectId", projectId);
        model.addAttribute("newtarefasDto", tarefDto);
        return "projects/newTarefa";
    }

    @PostMapping("/create/{project_id}")
    public String createTarefa(@Valid @ModelAttribute TarefaDto tarefaDto, @PathVariable Long project_id, BindingResult result){

        if(project_id == null){
            System.out.println("Erro project_id, Não deu get no id do projecto");
        }
        if(result.hasErrors()){
            return "projects/index";
        }
        
        tarefaService.adicionarTarefa(tarefaDto, project_id);
        //pService.adicionarTarefa(tarefaDto, project_id);
        return "redirect:/projects/index";

    }
}
