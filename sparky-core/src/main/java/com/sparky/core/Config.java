package com.sparky.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Клас для роботи з конфігурацією в форматах JSON/YAML.
 *
 * @author Андрій Будильников
 */
public class Config {
    private final Map<String, Object> properties = new HashMap<>();
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    
    public Config() {}
    
    public Config loadFromJsonFile(File file) throws CoreException {
        try {
            JsonNode node = jsonMapper.readTree(file);
            loadFromJsonNode(node);
            return this;
        } catch (IOException e) {
            throw new CoreException("Failed to load config from JSON file: " + file.getAbsolutePath(), e);
        }
    }
    
    public Config loadFromYamlFile(File file) throws CoreException {
        try {
            JsonNode node = yamlMapper.readTree(file);
            loadFromJsonNode(node);
            return this;
        } catch (IOException e) {
            throw new CoreException("Failed to load config from YAML file: " + file.getAbsolutePath(), e);
        }
    }
    
    private void loadFromJsonNode(JsonNode node) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                JsonNode value = entry.getValue();
                if (value.isTextual()) {
                    properties.put(entry.getKey(), value.asText());
                } else if (value.isNumber()) {
                    properties.put(entry.getKey(), value.asInt());
                } else if (value.isBoolean()) {
                    properties.put(entry.getKey(), value.asBoolean());
                } else {
                    properties.put(entry.getKey(), value.toString());
                }
            });
        }
    }
    
    public Object getProperty(String key) {
        return properties.get(key);
    }
    
    public String getStringProperty(String key, String defaultValue) {
        Object value = properties.get(key);
        return value != null ? value.toString() : defaultValue;
    }
    
    public int getIntProperty(String key, int defaultValue) {
        Object value = properties.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value != null) {
            try {
                return Integer.parseInt(value.toString());
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
    
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        Object value = properties.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value != null) {
            return Boolean.parseBoolean(value.toString());
        }
        return defaultValue;
    }
}