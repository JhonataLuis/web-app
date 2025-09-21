package com.bmt.webApp.enums;

public enum ProjectStatus {

    NEW("New"),
    PENDING("Pendente"),
    IN_PROGRESS("Em andamento"),
    COMPLETED("Concluído"),
    CANCELED("Cancelado");

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
