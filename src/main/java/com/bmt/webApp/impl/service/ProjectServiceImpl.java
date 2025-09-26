package com.bmt.webApp.impl.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bmt.webApp.dto.ProjectDto;
import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.Usuario;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.ProjectService;

import jakarta.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService{

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectsRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

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
    public void createProject(ProjectDto dto) {
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

    /**
     * Lista todos os projetos com paginação
     */

    @Override
    public Page<ProjectDto> findAllProjects(Pageable pageable) {
        var page = projectRepository.findAll(pageable);
        return page.map(this::convertToDto);
    }

    /**
     * Lista projetos por status com paginação
     * @return projetos filtrados por status
     */
    @Override
    public Page<ProjectDto> findByStatus(String status, Pageable pageable) {
        var page = projectRepository.searchProjects(status, pageable);
        return page.map(this::convertToDto);
    }

    @Override
    public Page<ProjectDto> searchProjects(String searchTerm, Pageable pageable) {
        
        return projectRepository.searchProjects(searchTerm, pageable)
            .map(this::convertToDto);
    }

    @Override
    public void atualizarStatus(String status) {
        
        throw new UnsupportedOperationException("Unimplemented method 'atualizarStatus'");
    }

    /**
     * Conta por ID do projeto
     * @return total de projetos
     * 
     */

     @Override
     public long count() {
        return projectRepository.count();
     }

     /**
      * Contar o total de projetos com o status "Concluído"
      * @return total de projetos concluídos
      */
      @Override 
      public long countByStatus(String status) {
        return projectRepository.countByStatus(ProjectStatus.CONCLUÍDO);// Aqui você pode passar o enum ProjectStatus.CONCLUÍDO
      }

    /**
    * Lista os 4 últimos projetos cadastrados no sistema
    * @return lista dos 4 últimos projetos
    */
    @Override
    public List<ProjectDto> findTop4ByOrderByIdDesc(){

        List<Project> projects = projectRepository.findTop4ByOrderByIdDesc();
        return projects.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        }

    private ProjectDto convertToDto(Project project){

        ProjectDto dto = new ProjectDto();
        
        dto.setId(project.getId());
        dto.setNome(project.getNome());
        dto.setDescricao(project.getDescricao());
        dto.setDataInicio(project.getDataInicio());
        dto.setDataFim(project.getDataFim());
        dto.setCompletionPercentage(project.getCompletionPercentage());
        dto.setStatus(project.getStatus());// Retorna a string do enum

        // Se a entidade Project tem um objeto Usuario, extraia o ID
        if(project.getUserResponseProject() != null){
            dto.setUserResponseProjectId(project.getUserResponseProject().getId());
        }
        
        return dto;
    }

    private Project convertToEntity(ProjectDto dto){

        
        Project project = new Project();
        project.setNome(dto.getNome());
        project.setDescricao(dto.getDescricao());
        project.setDataInicio(dto.getDataInicio());
        project.setDataFim(dto.getDataFim());
        
        // Buscar o Usuario pelo ID do DTO e setar o objeto Usuario na entidade
        if(dto.getUserResponseProjectId() != null){
            Usuario usuario = userRepository.findById(dto.getUserResponseProjectId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.getUserResponseProjectId()));
                project.setUserResponseProject(usuario);
        }
        
        return project;
    }

    /**
     * Atualiza a porcentagem de conclusão do projeto com base no status dos projetos
     * @param project o projeto a ser atualizado
     * @return 
     */
    @Override
    public void updateCompletionPercentagem(Long projectId, Integer completionPercentage) {

       Project project = projectRepository.findById(projectId)
       .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado com ID: " + projectId));

        project.setCompletionPercentage(completionPercentage);

        // Atualiza o status do projeto com base na porcentagem de conclusão
        if(completionPercentage == 100){
            project.setStatus(ProjectStatus.CONCLUÍDO);
        } else if (completionPercentage > 0){
            project.setStatus(ProjectStatus.ANDAMENTO);
        } else {
            project.setStatus(ProjectStatus.PENDENTE);
        }

        project = projectRepository.save(project);
        // Retorna o projeto atualizado
        convertToDto(project);
     }

     /**
      * Busca projetos com prazos próximos (dentro de 7 dias a partir da data atual)
      * @return lista de projetos com prazos próximos
      */
    @Override
    public List<ProjectDto> buscarProjetosComProximosPrazos() {

        // Filtra projetos cuja data de fim esteja dentro dos próximos 7 dias
        return projectRepository.findAll().stream()
            .filter(p -> p.getDataFim() != null)
            .filter(p -> p.getDataFim().isAfter(LocalDateTime.now().minusDays(7)))
            .sorted((p1, p2) -> p1.getDataFim().compareTo(p2.getDataFim()))
            .limit(4) // Limita a 4 projetos
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
