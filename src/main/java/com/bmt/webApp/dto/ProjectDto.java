package com.bmt.webApp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProjectDto {

    //@NotNull
    private Long id;
    @NotBlank
    @NotEmpty(message = "The name is required")
    private String nome;
    @NotBlank
    @NotEmpty(message = "The description is required")
    private String descricao;

    @NotNull(message = "The Date start is required")
    private LocalDateTime dataInicio;
    
    @NotNull(message = "The Date End is required")
    private LocalDateTime dataFim;
    @NotBlank
    @NotEmpty(message = "The status is required")
    private String status;//Em andamento, Concluído

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    

}
