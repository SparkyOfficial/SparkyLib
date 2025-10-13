package com.sparky.assets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sparky.core.SparkyLogger;

/**
 * Ассет скрипта.
 *
 * @author Богдан Кравчук
 */
public class ScriptAsset implements Asset {
    private final SparkyLogger logger = SparkyLogger.getLogger(ScriptAsset.class);
    
    private final String id;
    private final String path;
    private boolean loaded = false;
    private String scriptContent;
    
    public ScriptAsset(String id, String path) {
        this.id = id;
        this.path = path;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public AssetType getType() {
        return AssetType.SCRIPT;
    }
    
    @Override
    public void load() {
        try {
            File file = new File(path);
            if (file.exists()) {
                scriptContent = Files.readString(Paths.get(path));
                loaded = true;
                logger.info("Loaded script: " + id + " from " + path);
            } else {
                logger.warn("Script file not found: " + path);
                // Load with sample content
                scriptContent = generateSampleScript();
                loaded = true;
            }
        } catch (IOException e) {
            logger.error("Failed to load script: " + id + " from " + path, e);
            // Load with sample content on error
            scriptContent = generateSampleScript();
            loaded = true;
        }
    }
    
    @Override
    public void unload() {
        scriptContent = null;
        loaded = false;
        logger.info("Unloaded script: " + id);
    }
    
    @Override
    public boolean isLoaded() {
        return loaded;
    }
    
    /**
     * Отримує вміст скрипта.
     */
    public String getScriptContent() {
        return scriptContent;
    }
    
    /**
     * Встановлює вміст скрипта.
     */
    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }
    
    /**
     * Generates a sample script content.
     */
    private String generateSampleScript() {
        return "// Sample script content for " + id + "\n" +
               "function init() {\n" +
               "    // Initialization code\n" +
               "}\n" +
               "\n" +
               "function update(deltaTime) {\n" +
               "    // Update logic\n" +
               "}";
    }
}