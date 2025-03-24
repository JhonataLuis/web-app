package com.bmt.webApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmt.webApp.model.Project;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Long>{

}
