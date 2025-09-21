package com.bmt.webApp.enums;

public enum ProjectStatusEnum {

    NEW("New"),
    IN_PROGRESS("Em andamento"),
    COMPLETED("Concluído");

    private final String descricao;

    ProjectStatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ProjectStatusEnum fromDescricao(String descricao) {
        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
            if (status.descricao.equalsIgnoreCase(descricao) || 
                status.name().equalsIgnoreCase(descricao)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status não encontrado para a descrição: " + descricao);
    }
}
