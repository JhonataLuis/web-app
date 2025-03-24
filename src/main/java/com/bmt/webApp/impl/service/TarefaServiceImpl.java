package com.bmt.webApp.impl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.model.Tarefa;
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

}
