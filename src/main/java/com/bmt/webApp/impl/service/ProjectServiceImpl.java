package com.bmt.webApp.impl.service;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.dto.ProjectDto;
import com.bmt.webApp.model.Project;
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
     * Lista todos os Projetos
     * 
     * @return lista de produtos
     */
    @Override
    public List<ProjectDto> listProject() {
        logger.info("Listando projetos: {}");

        List<Project> projects = projectRepository.findAll();
        return projects.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
            
    }

    /**
     * Adiciona um novo projeto
     * 
     * @param projectDto dados do projeto
     * @return projeto criado
     */
    @Transactional
    @Override
    public void createProject(@Valid ProjectDto dto) {
        logger.info("Tentando adicionar um novo Projeto: {}");

        Project project = convertToEntity(dto);
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
     * Obtem o ID do projeto que vai realizar a atualização
     * 
     * @param ID do projeto
     * @return retorna o projecto realizado os set
     */
    @Override
    public ProjectDto getProjectById(Long id) {
         
        Project project = projectRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Project Id: " + id));
        return convertToDto(project);
    }

    /**
     * Atualiza dados do projeto no banco de dados
     * 
     * @param projectDto objeto projeto com alguns dados
     * 
     */
    @Override
    public void updateProject(Long id, ProjectDto dto){
        logger.info("Tentando atualizar um projeto no banco de dados: {}" + id);

        Project project = projectRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado com ID :" + id));

        project.setNome(dto.getNome());
        project.setDescricao(dto.getDescricao());
        project.setDataInicio(dto.getDataInicio());
        project.setDataFim(dto.getDataFim());
        project.setStatus(dto.getStatus());

        projectRepository.save(project);
        logger.info("Projeto atualizado no banco de dados com ID: {}" + id);
    }

    /**
     * Ação para deletar um projeto do banco de dados
     */
    @Override
    public void deletProject(Long id){
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Projeto com ID: {}" + id + "não encontrado"));
        projectRepository.delete(project);    
    }

    @Override
    public void atualizarStatus(String status) {
        
        throw new UnsupportedOperationException("Unimplemented method 'atualizarStatus'");
    }

    private ProjectDto convertToDto(Project project){

        ProjectDto dto = new ProjectDto();
        
        dto.setId(project.getId());
        dto.setNome(project.getNome());
        dto.setDescricao(project.getDescricao());
        dto.setDataInicio(project.getDataInicio());
        dto.setDataFim(project.getDataFim());
        dto.setStatus(project.getStatus());
        return dto;
    }

    private Project convertToEntity(ProjectDto dto){

        Project project = new Project();
        project.setNome(dto.getNome());
        project.setDescricao(dto.getDescricao());
        project.setDataInicio(dto.getDataInicio());
        project.setDataFim(dto.getDataFim());
        project.setStatus(dto.getStatus());
        return project;
    }
    
}
