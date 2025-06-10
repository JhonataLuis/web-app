package com.bmt.webApp.service;

import java.util.List;

import com.bmt.webApp.model.ProjectDto;

public interface ProjectService {

    void createProject(ProjectDto projectDto);

    ProjectDto getProjectById(Long id);

    void updateProject(Long id, ProjectDto dto);

    List<ProjectDto> listProject();

    void atualizarStatus(String status);

    void detailProject(Long id);
}
