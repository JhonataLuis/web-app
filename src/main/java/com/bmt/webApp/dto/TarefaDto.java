package com.bmt.webApp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class TarefaDto {

    private Long id;
    @NotBlank
    @NotEmpty(message = "The title is required")
    private String titulo;
    @NotBlank
    @NotEmpty(message = "The desctiption is required")
    private String descricao;
    @NotBlank
    @NotEmpty(message = "The status is required")
    private String status;//Em andamento, Pendente
    @NotNull
    private LocalDateTime dataInicio;
    @NotNull
    private LocalDateTime dataFim;
    
    private Long projectId;
    private Long userResponseId;

    public void setUserResponseId(Long userResponseId) {
        this.userResponseId = userResponseId;
    }

    public Long getUserResponseId() {
        return userResponseId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }


    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

}
