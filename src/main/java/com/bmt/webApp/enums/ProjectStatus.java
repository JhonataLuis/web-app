package com.bmt.webApp.enums;

public enum ProjectStatus {

    NEW("New"),
    PENDENTE("Pendente"),
    ANDAMENTO("Andamento"),
    CONCLUÍDO("Concluído"),
    CANCELADO("Cancelado");

    private final String projectStatus;

    ProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public static ProjectStatus fromDescricao(String projectStatus) {
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.projectStatus.equalsIgnoreCase(projectStatus) || 
                status.name().equalsIgnoreCase(projectStatus)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status não encontrado para a descrição: " + projectStatus);
    }
}
