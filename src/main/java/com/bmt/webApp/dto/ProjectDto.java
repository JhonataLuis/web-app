package com.bmt.webApp.dto;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.bmt.webApp.enums.ProjectStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProjectDto {

    //@NotNull
    private Long id;
    
    @NotEmpty(message = "Informe um nome para o Projeto")
    private String nome;
    
    @NotEmpty(message = "The description is required")
    private String descricao;

    @NotNull(message = "The Date start is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataInicio;
    
    @NotNull(message = "The Date End is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataFim;
    
    @NotNull(message = "O status do projeto é obrigatório")
    private ProjectStatus status;//New, Pendente, Em andamento, Concluído e Cancelado

    @NotNull(message = "O projeto precisa de um responsável")
    @Min(value = 1, message = "O projeto precisa de um responsável válido") // evita caso de 0 seja enviado por engano
    private Long userResponseProjectId;

    private Integer completionPercentage;

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
   
    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void setCompletionPercentage(Integer completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public void setUserResponseProjectId(Long userResponseProjectId){
        this.userResponseProjectId = userResponseProjectId;
    }

    public Long getUserResponseProjectId(){
        return userResponseProjectId;
    }

    // Retorna a porcentagem de conclusão do projeto
    public Long getDiasRestantes(){
        if(dataFim == null)
            return Long.MAX_VALUE; // Retorna um valor alto se a data de fim não estiver definida
        return Duration.between(LocalDateTime.now(), dataFim).toDays();
        
    }

    // Nível de urgência baseado nos dias restantes
    public String getNivelUrgencia(){
        Long diasRestantes = getDiasRestantes();
        if(diasRestantes < 0){
            return "dark"; // Atrasado cinza
        } else if (diasRestantes <= 2){
            return "danger"; // Muito urgente vermelho
        } else if (diasRestantes <= 5){
            return "warning"; // Urgente amarelo 
        } else {
            return "info"; // Normal azul
        }
    }

    // Retorna uma string representando o status do prazo
    public String getStatusPrazo(){

        if(dataFim == null) return "Sem prazo definido";
        Long dias = getDiasRestantes();
        
        if (dias < 0){
            return "Atrasado (" + Math.abs(dias) + " dias)";
        } else if (dias == 0){
            return "Hoje";
        } else {
            return  "Vence em : " + dias + " dias";
            
        }
    }

}
