package com.bmt.webApp.service;

import java.util.List;

import com.bmt.webApp.dto.TarefaDto;
import com.bmt.webApp.model.Tarefa;

public interface TarefaService {

    List<Tarefa> listarTarefasPorProjeto(Long projectId);

    void adicionarTarefa(TarefaDto tarefaDto, Long project_id);
    void atribuirResponsavel(Long tarefaId, Long userId);
    void removerTarefa(Long tarefaId, Long userId);
    void atualizarTarefa(TarefaDto tarefaDto);
    Long obterProjetoIdDaTarefa(Long tarefaId);// útil para redirecionar ao projeto correto

}
