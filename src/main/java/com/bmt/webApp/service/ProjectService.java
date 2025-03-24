package com.bmt.webApp.service;

import com.bmt.webApp.model.Tarefa;

public interface ProjectService {

    void adicionarTarefa(Tarefa tarefa);

    void removerTarefa(Tarefa tarefa);

    void atualizarStatus(String status);

    void detailProject(Long id);
}
