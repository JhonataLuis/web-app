package com.bmt.webApp.service;

public interface ReportService {

    
    // Contar projetos em risco
    Long contarProjetosEmRisco(java.time.LocalDateTime dataInicio, Long projectId);
}
