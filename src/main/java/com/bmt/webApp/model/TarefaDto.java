package com.bmt.webApp.model;

import jakarta.validation.constraints.NotEmpty;

public class TarefaDto {

    @NotEmpty(message = "The title is required")
    private String titulo;
    @NotEmpty(message = "The desctiption is required")
    private String descricao;
    @NotEmpty(message = "The status is required")
    private String status;//Em andamento, Pendente
    
    private Long project_id;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getProject_id() {
        return project_id;
    }
}
