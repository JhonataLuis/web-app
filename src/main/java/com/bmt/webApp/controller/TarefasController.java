package com.bmt.webApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bmt.webApp.model.TarefaDto;
import com.bmt.webApp.service.TarefaService;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/create")
    public String CreateTarefa(Model model){
        TarefaDto tarefDto = new TarefaDto();
        model.addAttribute("newtarefasDto", tarefDto);
        return "projects/details";
    }
}
