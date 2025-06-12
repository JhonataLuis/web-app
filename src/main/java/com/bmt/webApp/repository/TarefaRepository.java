package com.bmt.webApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

    List<Tarefa> findByProjectId(Long projectId);//Busca tarefas por ID do projeto

    Optional<Tarefa> findByIdAndUserResponseId(Long id, Long userId);
}
