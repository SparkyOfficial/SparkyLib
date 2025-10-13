package com.sparky.datastore;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.sparky.core.SerializationUtils;
import com.sparky.core.SparkyLogger;

/**
 * Реалізація DataStore на основі YAML файлів.
 *
 * @author Андрій Будильников
 */
public class YamlDataStore implements DataStore {
    private final SparkyLogger logger = SparkyLogger.getLogger(YamlDataStore.class);
    
    private final File file;
    private final Map<String, Object> data = new HashMap<>();
    
    public YamlDataStore(File file) {
        this.file = file;
        load();
    }
    
    @Override
    public void put(String key, Object value) {
        data.put(key, value);
        save();
    }
    
    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = data.get(key);
        if (value == null) {
            return Optional.empty();
        }
        
        if (type.isInstance(value)) {
            return Optional.of(type.cast(value));
        }
        
        return Optional.empty();
    }
    
    @Override
    public void remove(String key) {
        data.remove(key);
        save();
    }
    
    @Override
    public boolean contains(String key) {
        return data.containsKey(key);
    }
    
    @Override
    public void close() {
        save();
    }
    
    private void load() {
        if (!file.exists()) {
            return;
        }
        
        try {
            String yaml = Files.readString(file.toPath());
            Map<String, Object> loadedData = SerializationUtils.fromYaml(yaml, Map.class);
            if (loadedData != null) {
                data.putAll(loadedData);
            }
        } catch (Exception e) {
            logger.error("Failed to load data from YAML file: " + file.getAbsolutePath(), e);
        }
    }
    
    private void save() {
        try {
            String yaml = SerializationUtils.toYaml(data);
            Files.writeString(file.toPath(), yaml, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            logger.error("Failed to save data to YAML file: " + file.getAbsolutePath(), e);
        }
    }
}