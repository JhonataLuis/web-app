package com.bmt.webApp.impl.service;

import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.Tarefa;
import com.bmt.webApp.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Override
    public void adicionarTarefa(Tarefa tarefa) {
       
    }

    @Override
    public void removerTarefa(Tarefa tarefa) {
        
    }

    @Override
    public void atualizarStatus(String status) {
        
    }

    @Override
    public void detailProject(Long id) {
        
        Project project = new Project();
        project.getId();
        project.getNome();
        project.getDescricao();
        project.getTarefas();
    }

}
