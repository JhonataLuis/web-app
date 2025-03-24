package com.bmt.webApp.service;

import java.util.List;

import com.bmt.webApp.model.Tarefa;

public interface TarefaService {

    List<Tarefa> listarTarefasPorProjeto(Long projectId);
}
