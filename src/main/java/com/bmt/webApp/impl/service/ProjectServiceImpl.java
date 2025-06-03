package com.bmt.webApp.impl.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.ProjectDto;
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
    public void createProject(ProjectDto projectDto) {
        
        Project project = new Project();
            project.setNome(projectDto.getNome());
            project.setDescricao(projectDto.getDescricao());
            project.setStatus(projectDto.getStatus());
            project.setDataInicio(projectDto.getDataInicio());
            project.setDataFim(projectDto.getDataFim());
            pRepository.save(project);
    }

    @Override
    public void removerTarefa(Tarefa tarefa) {
        
    }

    @Override
    public void atualizarStatus(String status) {
        
    }

    @Override//método para ver detalhes dos projetos
    public void detailProject(Long id) {
        
        Project project = new Project();
        project.getId();
        project.getNome();
        project.getDescricao();
        project.getTarefas();
    }

    @Override//método para listar os projects cadastrados no sistema
    public List<Project> listProject() {
        return pRepository.findAll();
    }

    
}
