package com.bmt.webApp.converter;

import com.bmt.webApp.enums.Funcao;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // Define se o conversor deve ser aplicado automaticamente
public class FuncaoConverter implements AttributeConverter<Funcao, String> {

    @Override
    public String convertToDatabaseColumn(Funcao funcao) {
        return funcao == null ? null : funcao.getDescricao();
    }

    @Override
    public Funcao convertToEntityAttribute(String descricao) {
        System.out.println(">>> Convertendo descrição para Função: " + descricao);// degub
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
