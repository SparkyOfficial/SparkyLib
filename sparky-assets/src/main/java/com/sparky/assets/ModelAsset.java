package com.sparky.assets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.sparky.core.SparkyLogger;

/**
 * Ассет моделі.
 *
 * @author Богдан Кравчук
 */
public class ModelAsset implements Asset {
    private final SparkyLogger logger = SparkyLogger.getLogger(ModelAsset.class);
    
    private final String id;
    private final String path;
    private boolean loaded = false;
    private List<String> modelData;
    
    public ModelAsset(String id, String path) {
        this.id = id;
        this.path = path;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public AssetType getType() {
        return AssetType.MODEL;
    }
    
    @Override
    public void load() {
        try {
            File file = new File(path);
            if (file.exists()) {
                modelData = Files.readAllLines(Paths.get(path));
                loaded = true;
                logger.info("Loaded model: " + id + " from " + path);
            } else {
                logger.warn("Model file not found: " + path);
            }
        } catch (IOException e) {
            logger.error("Failed to load model: " + id + " from " + path, e);
        }
    }
    
    @Override
    public void unload() {
        modelData = null;
        loaded = false;
        logger.info("Unloaded model: " + id);
    }
    
    @Override
    public boolean isLoaded() {
        return loaded;
    }
    
    /**
     * Отримує дані моделі (платформо-специфічні).
     */
    public List<String> getModelData() {
        return modelData;
    }
}