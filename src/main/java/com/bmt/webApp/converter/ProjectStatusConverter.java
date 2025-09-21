package com.bmt.webApp.converter;

import com.bmt.webApp.enums.ProjectStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

public class ProjectStatusConverter {

    @Converter(autoApply = true) // Define se o conversor deve ser aplicado automaticamente
    public static class ProjectStatusAttributeConverter implements AttributeConverter<ProjectStatus, String> {

        @Override
        public String convertToDatabaseColumn(ProjectStatus status) {
            return status == null ? null : status.getProjectStatus();
        }

        @Override
        public ProjectStatus convertToEntityAttribute(String descricao) {
            System.out.println(">>> Convertendo descrição para ProjectStatus: " + descricao);// degub
            if (descricao == null || descricao.isEmpty()) {
                return null;
            }
            try {
                return ProjectStatus.fromDescricao(descricao);
            } catch (IllegalArgumentException e) {
                
                throw new RuntimeException("Valor inválido para o ProjectStatus: " + descricao); // ou você pode lançar uma exceção personalizada
            }
        }

    }
}
