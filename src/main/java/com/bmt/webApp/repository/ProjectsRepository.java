package com.bmt.webApp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.model.Project;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Long>{

    @Query("SELECT p FROM Project p WHERE p.nome LIKE %:searchTerm% OR p.descricao LIKE %:searchTerm%")
    Page<Project> searchProjects(@Param("searchTerm") String searchTerm, Pageable pageable);
}
