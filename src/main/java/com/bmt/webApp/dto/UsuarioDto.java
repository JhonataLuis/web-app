package com.bmt.webApp.dto;

import java.time.LocalDateTime;

import com.bmt.webApp.enums.Funcao;
import com.bmt.webApp.model.Usuario;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UsuarioDto {

    private Long id;
    @NotBlank       
    @NotEmpty(message = "The name is required")
    private String name;
    @NotBlank
    @NotEmpty(message = "The email is required")
    private String email;
    @Column(nullable = false)
    private String password;
    private LocalDateTime dataCriacao;
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
