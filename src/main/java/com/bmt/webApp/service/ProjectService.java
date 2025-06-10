package com.bmt.webApp.service;

import java.util.List;

import com.bmt.webApp.model.Project;
import com.bmt.webApp.model.ProjectDto;

public interface ProjectService {

    void createProject(ProjectDto projectDto);

    ProjectDto getProjectById(Long id);

    void updateProject(ProjectDto projectDto);

    List<Project> listProject();

    void atualizarStatus(String status);

    void detailProject(Long id);
}
