package com.bmt.webApp.enums;

public enum Funcao {

    GERENTE("Gerente"),
    DESENVOLVEDOR("Desenvolvedor"),
    ANALISTA("Analista"),
    SUPORTE("Suporte"),
    TESTADOR("Testador"),
    ADMINISTRADOR("Administrador"),
    OUTROS("Outros");

    private final String descricao;

    Funcao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Funcao fromDescricao(String descricao) {
        for (Funcao funcao : Funcao.values()) {
            if (funcao.descricao.equalsIgnoreCase(descricao) || 
                funcao.name().equalsIgnoreCase(descricao)) {
                return funcao;
            }
        }
        throw new IllegalArgumentException("Função não encontrada para a descrição: " + descricao);
    }

}
