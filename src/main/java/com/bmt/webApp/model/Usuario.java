package com.bmt.webApp.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bmt.webApp.enums.Funcao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tab_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "A senha é obrigatória")
    // A senha deve ser criptografada antes de ser salva no banco de dados
    @Column(nullable=false)
    private String password;// Será armazenada já criptografada

    @NotNull(message = "A função é obrigatória")// Validação para garantir que a função não seja nula
    // Usando Enum para definir as funções do usuário
    @Column(nullable=false)
    private Funcao funcao;//Gerente, Desenvolvedor,Analista

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        // Método chamado antes de persistir o objeto no banco de dados
        dataCriacao = LocalDateTime.now();
       dataAtualizacao = LocalDateTime.now(); 
    }

    public void preUpdate() {
        // Método chamado antes de atualizar o objeto no banco de dados
        dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    //Método seguro para definir senha criptografada
    public void setPassword(String password) {
        this.password = password;
    }
    public Funcao getFuncao() {
        return funcao;
    }
    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    // Método para retornar a classe CSS baseada na função do usuário
    public String getBadgeClass() {

        // Retorna uma classe CSS baseada na função do usuário
        return switch (this.funcao.getDescricao().toUpperCase()) {
            case "GERENTE" -> "badge bg-primary";
            case "DESENVOLVEDOR" -> "badge bg-success";
            case "ANALISTA" -> "badge bg-info";
            case "SUPORTE" -> "badge bg-warning";
            case "TESTADOR" -> "badge bg-danger";
            case "ADMINISTRADOR" -> "badge bg-dark";
            case "OUTROS" -> "badge bg-secondary";// Para outras funções
            default -> "badge bg-secondary"; // Classe padrão para funções desconhecidas    
            
        };
    }

}
