package com.bmt.webApp.service;

public interface ReportService {

    Double calcularProdutividade(); // % tarefas concluídas no prazo
    Double calcularTaxaConclusao(); // % projetos concluídas
    Long contarTarefasAtrasadas(); // tarefas vencidadas não concluídas
    Long contarProjetosEmRisco(java.time.LocalDateTime dataInicio, Long projectId); // projetos próximos do prazo, baixo progresso
}
