package com.bmt.webApp.service;

import java.util.List;

import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.Tarefa;

public interface ProjectService {

    List<Project> listProject();
    
    Tarefa adicionarTarefa(Long projectId, Tarefa tarefa);
    
    void removerTarefa(Tarefa tarefa);

    void atualizarStatus(String status);

    void detailProject(Long id);
}
