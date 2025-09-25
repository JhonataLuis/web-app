package com.bmt.webApp.service;

import java.util.Map;

import com.bmt.webApp.enums.ProjectStatus;

public interface ReportService {

    Double calcularProdutividade(); // % tarefas concluídas no prazo
    Double calcularTaxaConclusao(); // % projetos concluídas
    Long contarTarefasAtrasadas(); // tarefas vencidadas não concluídas
    Long contarProjetosEmRisco(java.time.LocalDateTime dataInicio, Long projectId); // projetos próximos do prazo, baixo progresso

    // Retorna um mapa com status do projeto e quantidade
    Map<ProjectStatus, Long> contarProjetosPorStatus();

    // Buscar quantidade de tarefas por usuário (dashboard reports)
    Map<String, Long> contarTarefasPorMembro();
}
