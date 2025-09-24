package com.bmt.webApp.repository;

import java.time.LocalDateTime;
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

    //conta todos os projetos
    long count();

    //Consulta personalizada para contar projetos com status "Concluído"
    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = :status")
    Long countByStatus(@Param("status") ProjectStatus status);

    //ação para listar os 4 últimos projetos cadastrados no sistema, para mostrar no index do sistema
    List<Project> findTop4ByOrderByIdDesc();

    // Projetos em risco: aqueles com prazos próximos (dentro de 7 dias) e com menos de 50% de conclusão
    @Query("SELECT COUNT(p) FROM Project p " +
       "WHERE p.dataFim BETWEEN :hoje AND :limite " +
       "AND p.completionPercentage < 50 " +
       "AND p.status NOT IN (:statusIgnorados) " +
       "AND p.dataInicio >= :dataInicio " +
       "AND (:projectId IS NULL OR p.id = :projectId)")
Long countProjetosEmRisco(
        @Param("hoje") LocalDateTime hoje,
        @Param("limite") LocalDateTime limite,
        @Param("dataInicio") LocalDateTime dataInicio,
        @Param("projectId") Long projectId,
        @Param("statusIgnorados") List<ProjectStatus> statusIgnorados
);


}
