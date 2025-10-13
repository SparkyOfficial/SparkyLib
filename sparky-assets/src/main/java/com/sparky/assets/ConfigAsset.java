package com.sparky.assets;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.sparky.core.SerializationUtils;
import com.sparky.core.SparkyLogger;

/**
 * Ассет конфігурації.
 *
 * @author Богдан Кравчук
 */
public class ConfigAsset implements Asset {
    private final SparkyLogger logger = SparkyLogger.getLogger(ConfigAsset.class);
    
    private final String id;
    private final String path;
    private boolean loaded = false;
    private Map<String, Object> configData;
    
    public ConfigAsset(String id, String path) {
        this.id = id;
        this.path = path;
        this.configData = new HashMap<>();
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public AssetType getType() {
        return AssetType.CONFIG;
    }
    
    @Override
    public void load() {
        try {
            File file = new File(path);
            if (file.exists()) {
                String content = Files.readString(Paths.get(path));
                if (path.endsWith(".json")) {
                    configData = SerializationUtils.fromJson(content, Map.class);
                } else if (path.endsWith(".yaml") || path.endsWith(".yml")) {
                    configData = SerializationUtils.fromYaml(content, Map.class);
                } else {
                    // Simple key=value format
                    configData = parseSimpleConfig(content);
                }
                loaded = true;
                logger.info("Loaded config: " + id + " from " + path);
            } else {
                logger.warn("Config file not found: " + path);
                // Load with default values
                configData.put("default.key", "default.value");
                configData.put("another.key", "another.value");
                loaded = true;
            }
        } catch (Exception e) {
            logger.error("Failed to load config: " + id + " from " + path, e);
            // Load with default values on error
            configData.put("default.key", "default.value");
            configData.put("another.key", "another.value");
            loaded = true;
        }
    }
    
    @Override
    public void unload() {
        configData.clear();
        loaded = false;
        logger.info("Unloaded config: " + id);
    }
    
    @Override
    public boolean isLoaded() {
        return loaded;
    }
    
    /**
     * Отримує значення конфігурації за ключем.
     */
    public Object getConfigValue(String key) {
        return configData.get(key);
    }
    
    /**
     * Встановлює значення конфігурації за ключем.
     */
    public void setConfigValue(String key, Object value) {
        configData.put(key, value);
    }
    
    /**
     * Отримує всі дані конфігурації.
     */
    public Map<String, Object> getConfigData() {
        return new HashMap<>(configData);
    }
    
    /**
     * Parses simple key=value configuration format.
     */
    private Map<String, Object> parseSimpleConfig(String content) {
        Map<String, Object> config = new HashMap<>();
        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty() && !line.startsWith("#")) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return config;
    }
}