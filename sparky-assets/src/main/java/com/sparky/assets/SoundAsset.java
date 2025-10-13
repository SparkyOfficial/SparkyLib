package com.sparky.assets;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sparky.core.SparkyLogger;

/**
 * Ассет звуку.
 *
 * @author Богдан Кравчук
 */
public class SoundAsset implements Asset {
    private final SparkyLogger logger = SparkyLogger.getLogger(SoundAsset.class);
    
    private final String id;
    private final String path;
    private boolean loaded = false;
    private Clip soundData;
    
    public SoundAsset(String id, String path) {
        this.id = id;
        this.path = path;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public AssetType getType() {
        return AssetType.SOUND;
    }
    
    @Override
    public void load() {
        try {
            File file = new File(path);
            if (file.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                soundData = AudioSystem.getClip();
                soundData.open(audioInputStream);
                loaded = true;
                logger.info("Loaded sound: " + id + " from " + path);
            } else {
                logger.warn("Sound file not found: " + path);
            }
        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            logger.error("Failed to load sound: " + id + " from " + path, e);
        }
    }
    
    @Override
    public void unload() {
        if (soundData != null) {
            soundData.close();
            soundData = null;
        }
        loaded = false;
        logger.info("Unloaded sound: " + id);
    }
    
    @Override
    public boolean isLoaded() {
        return loaded;
    }
    
    /**
     * Отримує дані звуку (платформо-специфічні).
     */
    public Clip getSoundData() {
        return soundData;
    }
}