package com.sparky.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

/**
 * Утилітарний клас для серіалізації/десеріалізації об'єктів.
 *
 * @author Андрій Будильников
 */
public class SerializationUtils {
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());
    
    /**
     * Серіалізує об'єкт в JSON.
     */
    public static String toJson(Object obj) throws CoreException {
        try {
            return jsonMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new CoreException("Failed to serialize object to JSON", e);
        }
    }
    
    /**
     * Десеріалізує JSON в об'єкт.
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws CoreException {
        try {
            return jsonMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new CoreException("Failed to deserialize object from JSON", e);
        }
    }
    
    /**
     * Серіалізує об'єкт в YAML.
     */
    public static String toYaml(Object obj) throws CoreException {
        try {
            return yamlMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new CoreException("Failed to serialize object to YAML", e);
        }
    }
    
    /**
     * Десеріалізує YAML в об'єкт.
     */
    public static <T> T fromYaml(String yaml, Class<T> clazz) throws CoreException {
        try {
            return yamlMapper.readValue(yaml, clazz);
        } catch (JsonProcessingException e) {
            throw new CoreException("Failed to deserialize object from YAML", e);
        }
    }
}