package com.ggallici.tenpo.models.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class TransactionResultConverter implements AttributeConverter<Object, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String string) {
        try {
            return objectMapper.readValue(string, Object.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
