package com.bmt.webApp.impl.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.ProjectDto;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.service.ProjectService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProjectServiceImpl implements ProjectService{

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectsRepository projectRepository;

    /**
     * Adiciona um novo projeto
     * 
     * @param projectDto dados do projeto
     * @return projeto criado
     */
    @Transactional
    @Override
    public void createProject(@Valid ProjectDto projectDto) {
        logger.info("Tentando adicionar um novo Projeto: {}");

        Project project = new Project();
            project.setNome(projectDto.getNome());
            project.setDescricao(projectDto.getDescricao());
            project.setStatus(projectDto.getStatus());
            project.setDataInicio(projectDto.getDataInicio());
            project.setDataFim(projectDto.getDataFim());

            Project projectSave = projectRepository.save(project);
            logger.info("Projeto salvo com o ID: {}", projectSave.getId());
    }

    /**
     * Consulta os dados do projeto para ver detalhes
     * 
     * @param id ID do projeto
     */
    @Override
    public void detailProject(Long id) {
        
        Project project = new Project();
        project.getId();
        project.getNome();
        project.getDescricao();
        project.getTarefas();
    }

    /**
     * Atualiza dados do projeto no banco de dados
     * 
     * @param projectDto objeto projeto com alguns dados
     * 
     */
    @Override
    public void updateProject(ProjectDto projectDto){
        logger.info("Tentando atualizar um projeto no banco de dados: {}", projectDto.getId());

        Project project = projectRepository.findById(projectDto.getId())
        .orElseThrow(() -> new RuntimeException("Projeto não encontrado com ID :" + projectDto.getId()));

        project.setNome(projectDto.getNome());
        project.setDescricao(projectDto.getDescricao());
        project.setDataInicio(projectDto.getDataInicio());
        project.setDataFim(projectDto.getDataFim());
        project.setStatus(projectDto.getStatus());

        Project projectUpdate = projectRepository.save(project);
        logger.info("Projeto atualizado no banco de dados com ID: {}", projectUpdate.getId());
    }

    /**
     * Lista todos os Projetos
     * 
     * @return lista de produtos
     */
    @Override
    public List<Project> listProject() {

        logger.info("Listando projetos: {}");
        return projectRepository.findAll();
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getProjectById'");
    }


    @Override
    public void atualizarStatus(String status) {
        
        throw new UnsupportedOperationException("Unimplemented method 'atualizarStatus'");
    }

    
}
