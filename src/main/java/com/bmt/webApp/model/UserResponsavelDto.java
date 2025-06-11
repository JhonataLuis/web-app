package com.bmt.webApp.model;

import jakarta.validation.constraints.NotNull;

public class UserResponsavelDto {

    @NotNull
    private Long tarefaId;
    @NotNull
    private Long usuarioId;

    public void setTarefaId(Long tarefaId) {
        this.tarefaId = tarefaId;
    }

    public Long getTarefaId() {
        return tarefaId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    
}
