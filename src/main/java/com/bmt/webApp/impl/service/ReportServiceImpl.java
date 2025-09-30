package com.bmt.webApp.impl.service;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.repository.ProjectsRepository;
import com.bmt.webApp.repository.TarefaRepository;
import com.bmt.webApp.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ProjectsRepository projectRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    /**
     * Calcular produtividade: % tarefas concluídas no prazo    
     * @return
     */
    @Override
    public Double calcularProdutividade() {
        Long totalTarefas = tarefaRepository.count();
        Long tarefasConcluidas = tarefaRepository.countByStatus("CONCLUÍDO");

        if (totalTarefas == 0) return 0.0;
            return (tarefasConcluidas * 100.0) / totalTarefas;
    }

    /**
     * Calcular taxa de conclusão: % projetos concluídos
     * @return
     */ 
    @Override
    public Double calcularTaxaConclusao() {
        Long totalProjetos = projectRepository.count();
        Long projetosConcluidos = projectRepository.countByStatus(ProjectStatus.CONCLUIDO);

        if (totalProjetos == 0) return 0.0;
            return (projetosConcluidos * 100.0) / totalProjetos;
    }

    /**
     * Contar tarefas atrasadas: tarefas vencidas não concluídas
     * @return
     */
    @Override
    public Long contarTarefasAtrasadas() {
        LocalDateTime agora = LocalDateTime.now();
        return tarefaRepository.countByDataFimBeforeAndStatusNot(agora, "CONCLUÍDO");
    }

    /**
     * Contar projetos em risco: projetos próximos do prazo, baixo progresso
     * @param dataInicio
     * @param projectId
     * @return
     */
    @Override
    public Long contarProjetosEmRisco(LocalDateTime dataInicio, Long projectId) {
    LocalDateTime hoje = LocalDateTime.now();
    LocalDateTime limite = hoje.plusDays(7);

    List<ProjectStatus> statusIgnorados = List.of(
            ProjectStatus.CONCLUIDO, 
            ProjectStatus.CANCELADO
        );

    return projectRepository.countProjetosEmRisco(hoje, limite, dataInicio, projectId, statusIgnorados);
    
    }

    /**
     * Conta projetos por status (grafico dashboard reports)
     */
    @Override
    public Map<ProjectStatus, Long> contarProjetosPorStatus() {
       
        Map<ProjectStatus, Long> result = new EnumMap<>(ProjectStatus.class);

        for(ProjectStatus status : ProjectStatus.values()){
            Long count = projectRepository.countByStatus(status);
            result.put(status, count != null ? count : 0L);
        }

        return result;
    }

    // Buscar quantidade de tarefas por usuário (dashboard reports)
    @Override
    public Map<String, Long> contarTarefasPorMembro(){

        List<Object[]> resultados = tarefaRepository.contarTarefaPorMembro();
        Map<String, Long> mapa = new HashMap<>();

        for(Object[] linha : resultados){
            String nome = (String) linha[0];
            Long total = (Long) linha[1];
            mapa.put(nome, total);
        }

        return mapa;
    }
}
