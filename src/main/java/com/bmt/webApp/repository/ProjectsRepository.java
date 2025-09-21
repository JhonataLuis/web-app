package com.bmt.webApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.enums.ProjectStatus;
import com.bmt.webApp.model.Project;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Long>{

    @Query("SELECT p FROM Project p WHERE p.nome LIKE %:searchTerm% OR p.descricao LIKE %:searchTerm%")
    Page<Project> searchProjects(@Param("searchTerm") String searchTerm, Pageable pageable);

    //@Query("SELECT COUNT(p) FROM Project p WHERE p.projectId = :projectId")
    //Project countByProjectId(Long projetoId);

    long count();

    //Consulta personalizada para contar projetos com status "Concluído"
    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = :status")
    long countByStatus(@Param("status") ProjectStatus status);

    //ação para listar os 4 últimos projetos cadastrados no sistema
    List<Project> findTop4ByOrderByIdDesc();
}
