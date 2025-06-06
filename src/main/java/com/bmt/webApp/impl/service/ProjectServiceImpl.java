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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectsRepository projectRepository;

    @Autowired
    private TarefaRepository tarefaRepository;
    
    @Transactional
    @Override
    public Tarefa adicionarTarefa(Long projectId, Tarefa tarefa) {

       Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Projeto com ID " + projectId + " não encontrado"));

        project.adicionarTarefa(tarefa);
        return tarefaRepository.save(tarefa);

    }

    @Override
    public void createProject(ProjectDto projectDto) {
        
        Project project = new Project();
            project.setNome(projectDto.getNome());
            project.setDescricao(projectDto.getDescricao());
            project.setStatus(projectDto.getStatus());
            project.setDataInicio(projectDto.getDataInicio());
            project.setDataFim(projectDto.getDataFim());

            projectRepository.save(project);
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

    @Override
    public void updateProject(ProjectDto projectDto){

        Project project = projectRepository.findById(projectDto.getId())
        .orElseThrow(() -> new RuntimeException("Project not found with id:" + projectDto.getId()));

        project.setNome(projectDto.getNome());
        project.setDescricao(projectDto.getDescricao());
        project.setDataInicio(projectDto.getDataInicio());
        project.setDataFim(projectDto.getDataFim());
        project.setStatus(projectDto.getStatus());

        projectRepository.save(project);
    }

    @Override//método para listar os projects cadastrados no sistema
    public List<Project> listProject() {
        return projectRepository.findAll();
    }

    
}
