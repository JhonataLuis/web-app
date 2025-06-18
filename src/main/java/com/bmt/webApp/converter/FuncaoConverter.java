package com.bmt.webApp.converter;

import com.bmt.webApp.enums.Funcao;

import jakarta.persistence.AttributeConverter;

public class FuncaoConverter implements AttributeConverter<Funcao, String> {

    @Override
    public String convertToDatabaseColumn(Funcao funcao) {
        return funcao == null ? null : funcao.getDescricao();
    }

    @Override
    public Funcao convertToEntityAttribute(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            return null;
        }
        try {
            return Funcao.fromDescricao(descricao);
        } catch (IllegalArgumentException e) {
            
            throw new RuntimeException("Valor inválido para a Função: " + descricao); // ou você pode lançar uma exceção personalizada
        }
    }

}
