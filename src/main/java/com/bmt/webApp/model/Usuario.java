package com.bmt.webApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "tab_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;
    private String funcao;//Gerente, Desenvolvedor

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
    public String getFuncao() {
        return funcao;
    }
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    
}
