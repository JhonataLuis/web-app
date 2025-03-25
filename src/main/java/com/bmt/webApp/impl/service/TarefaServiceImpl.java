package com.bmt.webApp.impl.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Tarefa;
import com.bmt.webApp.model.TarefaDto;
import com.bmt.webApp.repository.TarefaRepository;
import com.bmt.webApp.service.TarefaService;

@Service
public class TarefaServiceImpl implements TarefaService{

    @Autowired
    private TarefaRepository tarefaRepository;

    @Override
    public List<Tarefa> listarTarefasPorProjeto(Long projectId) {
        if(projectId == null || projectId <= 0){
            throw new IllegalArgumentException("ID do projeto invólido");
        }
        return tarefaRepository.findByProjectId(projectId);
    }

    @Override
    public void creatTarefa(TarefaDto tarefaDto) {
        
        Tarefa taref = new Tarefa();
        if(taref != null){
            taref.setTitulo(tarefaDto.getTitulo());
            taref.setDescricao(tarefaDto.getDescricao());
            taref.setStatus(tarefaDto.getStatus());
            taref.setDataInicio(new Date());
            taref.setDataFim(new Date());
            tarefaDto.getProject_id();

            tarefaRepository.save(taref);
        }
    }

}
