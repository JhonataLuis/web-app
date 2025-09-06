package com.bmt.webApp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bmt.webApp.dto.ProjectDto;

public interface ProjectService {

    void createProject(ProjectDto projectDto);

    ProjectDto getProjectById(Long id);

    void updateProject(Long id, ProjectDto dto);

    void deletProject(Long id);

    List<ProjectDto> listProject();

    void atualizarStatus(String status);

    void detailProject(Long id);

    Page<ProjectDto> findAll(Pageable pageable);
}
