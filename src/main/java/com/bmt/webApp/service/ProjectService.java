package com.bmt.webApp.service;

import com.bmt.webApp.model.Tarefa;

public interface ProjectService {

    Tarefa adicionarTarefa(Long projectId, Tarefa tarefa);

    void removerTarefa(Tarefa tarefa);

    void atualizarStatus(String status);

    void detailProject(Long id);
}
