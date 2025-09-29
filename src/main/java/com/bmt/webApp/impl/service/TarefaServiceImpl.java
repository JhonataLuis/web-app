package com.bmt.webApp.impl.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class TarefaServiceImpl implements TarefaService{

    private static final Logger logger = LoggerFactory.getLogger(TarefaServiceImpl.class);
    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private UserRepository userRepository;

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
        
                Usuario usuario = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

                tarefa.setUserResponse(usuario);
                tarefaRepository.save(tarefa);
    }

    @Override
    public Long removerTarefa(Long tarefaId, Long userId){

        Tarefa tarefa = tarefaRepository.findByIdAndUserResponseId(tarefaId, userId)
            .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada ou usuário não autorizado"));
        
        Long projectId = tarefa.getProject().getId();
        tarefaRepository.delete(tarefa);

        return projectId;
    }

    @Override
    public void atualizarTarefa(TarefaDto dto){
        Tarefa tarefa = tarefaRepository.findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setStatus(dto.getStatus());
        tarefa.setDataInicio(dto.getDataInicio());
        tarefa.setDataFim(dto.getDataFim());

        // Se estiver atualizando o usuário responsável
        if(dto.getUserResponseId() != null){
            Usuario user = userRepository.findById(dto.getUserResponseId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            tarefa.setUserResponse(user);    
        }

        tarefaRepository.save(tarefa);
    }

    /**
     * Obter ID do Projeto a partir do ID da Tarefa
     * 
     * @param tarefaId dados da tarefa
     * @return O ID da tarefa associado ao ID do Projeto
     */

    @Override
    public Long obterProjetoIdDaTarefa(Long tarefaId){
        return tarefaRepository.findById(tarefaId)
                .map(tarefa -> {

                    if(tarefa.getProject() == null){
                        throw new IllegalStateException("Tarefa não está associada a um projeto.");
                    }
                    return tarefa.getProject().getId();
                })
                .orElseThrow(() -> new NoSuchElementException("Tarefa não encontrada com ID:" + tarefaId));
                
    }

    /**
     * Buscar tarefa por ID
     */

    @Override
    public TarefaDto buscarPorId(Long tarefaId){
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        TarefaDto dto = new TarefaDto();
            dto.setId(tarefa.getId());
            dto.setTitulo(tarefa.getTitulo());
            dto.setDescricao(tarefa.getDescricao());
            dto.setStatus(tarefa.getStatus());
            dto.setDataInicio(tarefa.getDataInicio());
            dto.setDataFim(tarefa.getDataFim());
            if(tarefa.getUserResponse() != null){
                dto.setUserResponseId(tarefa.getUserResponse().getId());
            }
        return dto;
    }

    /**
     * Contar tarefas atrasadas
     * @return total de tarefas atrasadas
     * 
     */
    @Override
    public Long contarTarefasAtrasadas() {
        return tarefaRepository.countByTaskAtrasada(LocalDateTime.now());

    }
}   
