package com.bmt.webApp.impl.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ProjectsRepository projectRepository;

    // Contar projetos em risco
    public Long contarProjetosEmRisco(LocalDateTime dataInicio, Long projectId) {
    LocalDateTime hoje = LocalDateTime.now();
    LocalDateTime limite = hoje.plusDays(7);

    List<ProjectStatus> statusIgnorados = List.of(
            ProjectStatus.CONCLUÍDO, 
            ProjectStatus.CANCELADO
        );

    return projectRepository.countProjetosEmRisco(hoje, limite, dataInicio, projectId, statusIgnorados);
    }
}
