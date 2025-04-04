package com.bmt.webApp.impl.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.Tarefa;
import com.bmt.webApp.model.TarefaDto;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.TarefaRepository;
import com.bmt.webApp.service.TarefaService;

@Service
public class TarefaServiceImpl implements TarefaService{

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjectsRepository pRepository;

    @Override//método para listar tarefas por projetos
    public List<Tarefa> listarTarefasPorProjeto(Long projectId) {
        if(projectId == null || projectId <= 0){
            throw new IllegalArgumentException("ID do projeto inválido");
        }
        return tarefaRepository.findByProjectId(projectId);
    }

    
    @Override//método para cadastrar uma tarefa no projeto
    public void adicionarTarefa(TarefaDto tarefaDto, Long projectId) {
        
        Project project = pRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        
        Tarefa taref = new Tarefa();
        if(taref != null){
            taref.setTitulo(tarefaDto.getTitulo());
            taref.setDescricao(tarefaDto.getDescricao());
            taref.setStatus(tarefaDto.getStatus());
            taref.setDataInicio(new Date());
            taref.setDataFim(new Date());
            taref.setProject(project);
            
            tarefaRepository.save(taref);
        }
    }

}
