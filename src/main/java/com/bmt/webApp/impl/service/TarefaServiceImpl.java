package com.bmt.webApp.impl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.dto.TarefaDto;
import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.Tarefa;
import com.bmt.webApp.model.Usuario;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.TarefaRepository;
import com.bmt.webApp.repository.UserRepository;
import com.bmt.webApp.service.TarefaService;

import jakarta.validation.Valid;

@Service
public class TarefaServiceImpl implements TarefaService{

    private static final Logger logger = LoggerFactory.getLogger(TarefaServiceImpl.class);
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private UserRepository UserRepository;

    /**
     * Lista todas as tarefas associadas a um projeto
     * 
     * @param projectId ID do projeto
     * @return lista de tarefas
     */

    @Override
    public List<Tarefa> listarTarefasPorProjeto(Long projectId) {
        if(projectId == null || projectId <= 0){
            throw new IllegalArgumentException("ID do projeto inválido");
        }
        
        logger.info("Listando tarefas para o projeto com ID: {}", projectId);
        return tarefaRepository.findByProjectId(projectId);
    }

    /**
     * Adiciona uma nova tarefa a um projeto existente.
     * 
     * @param tarefaDto dados da tarefa
     * @param projectId ID do projeto
     * @return Tarefa criada
     */

    
    @Override
    public void adicionarTarefa(@Valid TarefaDto tarefaDto, Long projectId) {
        logger.info("Tentando adicionar tarefa ao projeto com ID: {}", projectId);

        Project project = projectsRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));
        
        Tarefa taref = new Tarefa();
            taref.setTitulo(tarefaDto.getTitulo());
            taref.setDescricao(tarefaDto.getDescricao());
            taref.setStatus(tarefaDto.getStatus());
            taref.setDataInicio(tarefaDto.getDataInicio());
            taref.setDataFim(tarefaDto.getDataFim());
            taref.setProject(project);
            
            Tarefa tarefaSalva = tarefaRepository.save(taref);
            logger.info("Tarefa salva com ID: {}", tarefaSalva.getId());
    }

    /**
     * Atribui um responsável para executar a tarefa do projeto
     * 
     * @param tarefa dados da tarefa
     * @param usuario dados do usuário
     * @return Usuario responsável por tarefa
     */

    @Override
    public void atribuirResponsavel(Long tarefaId, Long userId){
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        
                Usuario usuario = UserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

                tarefa.setUserResponse(usuario);
                tarefaRepository.save(tarefa);
    }
}
