package com.bmt.webApp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

    List<Tarefa> findByProjectId(Long projectId);//Busca tarefas por ID do projeto

    Optional<Tarefa> findByIdAndUserResponseId(Long id, Long userId);

    //Contar tarefas atrasadas (onde a data de fim é anterior à data atual e o status não é "COMPLETED")
    @Query("SELECT COUNT(t) FROM Tarefa t WHERE t.dataFim < :agora AND t.status <> 'COMPLETED'")
    Long countByTaskAtrasada(@Param("agora") LocalDateTime agora);

    //Contar tarefas por status
    Long countByStatus(String status);

    //Contar tarefas vencidas não concluídas
    Long countByDataFimBeforeAndStatusNot(LocalDateTime dataFim, String status);
}
