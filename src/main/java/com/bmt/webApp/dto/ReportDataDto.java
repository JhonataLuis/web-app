package com.bmt.webApp.dto;

import java.util.Map;

public class ReportDataDto {

    private Integer prdutividade;
    private Integer taxaConclusao;
    private Integer atrasos;
    private Integer riscos;
    private Map<String, Object> statusProjects;
    private Map<String, Object> progressoMensal;
    private Map<String, Object> tarefasPorMembro;

    public ReportDataDto() {
    }

    public ReportDataDto(Integer prdutividade, Integer taxaConclusao, Integer atrasos, 
            Integer riscos,
            Map<String, Object> statusProjects, Map<String, Object> progressoMensal,
            Map<String, Object> tarefasPorMembro) {
        this.prdutividade = prdutividade;
        this.taxaConclusao = taxaConclusao;
        this.atrasos = atrasos;
        this.riscos = riscos;
        this.statusProjects = statusProjects;
        this.progressoMensal = progressoMensal;
        this.tarefasPorMembro = tarefasPorMembro;
    }

    // Getters and Setters
    public Integer getPrdutividade() {
        return prdutividade;
    }
    public void setPrdutividade(Integer prdutividade) {
        this.prdutividade = prdutividade;
    }
    public Integer getTaxaConclusao() {
        return taxaConclusao;
    }
    public void setTaxaConclusao(Integer taxaConclusao) {
        this.taxaConclusao = taxaConclusao;
    }
    public Integer getAtrasos() {
        return atrasos;
    }
    public void setAtrasos(Integer atrasos) {
        this.atrasos = atrasos;
    }
    public Integer getRiscos() {
        return riscos;
    }
    public void setRiscos(Integer riscos) {
        this.riscos = riscos;
    }
    public Map<String, Object> getStatusProjects() {
        return statusProjects;
    }
    public void setStatusProjects(Map<String, Object> statusProjects) {
        this.statusProjects = statusProjects;
    }
    public Map<String, Object> getProgressoMensal() {
        return progressoMensal;
    }
    public void setProgressoMensal(Map<String, Object> progressoMensal) {
        this.progressoMensal = progressoMensal;
    }

    public Map<String, Object> getTarefasPorMembro() {
        return tarefasPorMembro;
    }
    public void setTarefasPorMembro(Map<String, Object> tarefasPorMembro) {
        this.tarefasPorMembro = tarefasPorMembro;
    }
}
