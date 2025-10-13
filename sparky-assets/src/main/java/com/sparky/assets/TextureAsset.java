package com.sparky.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sparky.core.SparkyLogger;

/**
 * Ассет текстури.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class TextureAsset implements Asset {
    private final SparkyLogger logger = SparkyLogger.getLogger(TextureAsset.class);
    
    private final String id;
    private final String path;
    private boolean loaded = false;
    private BufferedImage textureData;
    
    public TextureAsset(String id, String path) {
        this.id = id;
        this.path = path;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public AssetType getType() {
        return AssetType.TEXTURE;
    }
    
    @Override
    public void load() {
        try {
            File file = new File(path);
            if (file.exists()) {
                textureData = ImageIO.read(file);
                loaded = true;
                logger.info("Loaded texture: " + id + " from " + path);
            } else {
                logger.warn("Texture file not found: " + path);
            }
        } catch (IOException e) {
            logger.error("Failed to load texture: " + id + " from " + path, e);
        }
    }
    
    @Override
    public void unload() {
        textureData = null;
        loaded = false;
        logger.info("Unloaded texture: " + id);
    }
    
    @Override
    public boolean isLoaded() {
        return loaded;
    }
    
    /**
     * Отримує дані текстури (платформо-специфічні).
     */
    public BufferedImage getTextureData() {
        return textureData;
    }
}