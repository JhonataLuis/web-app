package com.bmt.webApp.service;

import java.util.List;

import com.bmt.webApp.model.Tarefa;
import com.bmt.webApp.model.TarefaDto;

public interface TarefaService {

    List<Tarefa> listarTarefasPorProjeto(Long projectId);

    void creatTarefa(TarefaDto tarefaDto);
}
