package com.bmt.webApp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bmt.webApp.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_projects")
    @SequenceGenerator(name = "seq_projects", sequenceName = "seq_projects", allocationSize = 1)
    private Long id;

    private String nome;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer completionPercentage;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;//New, Pendente, Em andamento, Concluído e Cancelado

    //Relacionamento 1-N com Tarefa
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //Evita recusrsividade infinita no JSON
    private List<Tarefa> tarefas = new ArrayList<>();

    // Construtor para novos projetos: ao cadastrar, a porcentage de conclusão inicia em 0 e o status em "New"
    public Project(){
        this.completionPercentage = 0;
        this.status = ProjectStatus.NEW;
    }
    
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

    public Integer getCompletionPercentage(){
        return completionPercentage;
    }

    public void setCompletionPercentage(Integer completionPercentage){
        this.completionPercentage = completionPercentage;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    //MÉTODO AUXILIAR PARA ADICIONAR UMA TAREFA AO PROJETO
    /*public void adicionarTarefa(Tarefa tarefa){
        tarefas.add(tarefa);
        tarefa.setProject(this);
    }*/
    
}
