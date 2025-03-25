package com.bmt.webApp.impl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.Tarefa;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.TarefaRepository;
import com.bmt.webApp.service.ProjectService;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectsRepository pRepository;

    @Autowired
    private TarefaRepository tRepository;
    
    @Transactional
    @Override
    public Tarefa adicionarTarefa(Long projectId, Tarefa tarefa) {

       Project project = pRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        project.adicionarTarefa(tarefa);
        return tRepository.save(tarefa);

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
