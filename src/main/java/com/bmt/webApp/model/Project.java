package com.bmt.webApp.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.bmt.webApp.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    //@Enumerated(EnumType.STRING)
    private ProjectStatus status;//New, Pendente, Em andamento, Concluído e Cancelado

    //Relacionamento 1-N com Tarefa
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //Evita recusrsividade infinita no JSON
    private List<Tarefa> tarefas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id") // o banco não permitirá projetos sem usuários responsáveis
    @JsonBackReference // Evita recursividade infinita no JSON
    private Usuario userResponseProject;

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

    public void setUserResponseProject(Usuario userResponseProject){
        this.userResponseProject = userResponseProject;
    }

    public Usuario getUserResponseProject() {
        return userResponseProject;
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
