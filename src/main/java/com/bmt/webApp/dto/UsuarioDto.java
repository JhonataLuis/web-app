package com.bmt.webApp.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.bmt.webApp.enums.Funcao;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class UsuarioDto {

    private Long id; 

    @NotEmpty(message = "O nome é obrigatório")
    private String name;

    @NotEmpty(message = "O e-mail é obrigatório")
    private String email;

    @NotEmpty(message="O password é obrigatório")
    @Column(nullable = false)
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")// pega automática a data do sistema
    private LocalDateTime dataCriacao = LocalDateTime.now().withSecond(0).withNano(0);

    private LocalDateTime dataAtualizacao;

    private Funcao funcao;

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

    public void setPassword(String password) {
        this.password = password;
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

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

   
}
