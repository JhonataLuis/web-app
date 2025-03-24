package com.bmt.webApp.model;

public class Equipe {

    private Long id;
    private String nome;
    private Project project;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    
}
