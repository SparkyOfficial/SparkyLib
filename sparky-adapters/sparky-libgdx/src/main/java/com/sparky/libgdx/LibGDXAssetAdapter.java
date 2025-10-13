package com.sparky.libgdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.sparky.assets.Asset;
import com.sparky.assets.AssetType;
import com.sparky.assets.ModelAsset;
import com.sparky.assets.SoundAsset;
import com.sparky.assets.TextureAsset;
import com.sparky.core.SparkyLogger;

/**
 * Адаптер для завантаження ассетів LibGDX зі специфікацій SparkyLib.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class LibGDXAssetAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(LibGDXAssetAdapter.class);
    
    private final AssetManager assetManager;
    
    public LibGDXAssetAdapter() {
        this.assetManager = new AssetManager();
        logger.info("LibGDXAssetAdapter initialized");
    }
    
    /**
     * Завантажує ассет SparkyLib за допомогою LibGDX AssetManager.
     */
    public void loadSparkyAsset(Asset asset) {
        if (asset.getType() == AssetType.TEXTURE && asset instanceof TextureAsset) {
            TextureAsset textureAsset = (TextureAsset) asset;
            assetManager.load(asset.getId(), Texture.class);
            logger.debug("Loading texture asset: " + asset.getId());
        } else if (asset.getType() == AssetType.SOUND && asset instanceof SoundAsset) {
            SoundAsset soundAsset = (SoundAsset) asset;
            // For sound assets, we would use LibGDX's Sound or Music classes
            // Use Sound for short sounds, Music for longer audio files
            assetManager.load(asset.getId(), Sound.class);
            logger.debug("Loading sound asset: " + asset.getId());
        } else if (asset.getType() == AssetType.MODEL && asset instanceof ModelAsset) {
            ModelAsset modelAsset = (ModelAsset) asset;
            // For model assets, we would use LibGDX's model loading capabilities
            assetManager.load(asset.getId(), Model.class);
            logger.debug("Loading model asset: " + asset.getId());
        } else {
            logger.warn("Unsupported asset type: " + asset.getType());
        }
    }
    
    /**
     * Отримує менеджер ассетів LibGDX.
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }
    
    /**
     * Оновлює менеджер ассетів LibGDX.
     */
    public void update() {
        assetManager.update();
    }
    
    /**
     * Перевіряє, чи завантажено всі ассети.
     */
    public boolean isFinished() {
        return assetManager.isFinished();
    }
}