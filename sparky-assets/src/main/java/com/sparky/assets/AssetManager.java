package com.sparky.assets;

import com.sparky.core.SparkyLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * Менеджер ассетів.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class AssetManager {
    private static final AssetManager INSTANCE = new AssetManager();
    
    private final SparkyLogger logger = SparkyLogger.getLogger(AssetManager.class);
    private final Map<String, Asset> assets = new HashMap<>();
    private final Map<AssetType, AssetLoader<? extends Asset>> loaders = new HashMap<>();
    
    private AssetManager() {
        // Register default loaders
        registerLoader(new TextureAssetLoader());
        registerLoader(new SoundAssetLoader());
        registerLoader(new ModelAssetLoader());
        registerLoader(new ConfigAssetLoader());
        registerLoader(new ScriptAssetLoader());
    }
    
    public static AssetManager getInstance() {
        return INSTANCE;
    }
    
    /**
     * Реєструє завантажувач ассетів.
     */
    public void registerLoader(AssetLoader<? extends Asset> loader) {
        loaders.put(loader.getSupportedAssetType(), loader);
        logger.info("Registered loader for asset type: " + loader.getSupportedAssetType());
    }
    
    /**
     * Реєструє ассет.
     */
    public void registerAsset(Asset asset) {
        String id = asset.getId();
        if (assets.containsKey(id)) {
            logger.warn("Asset " + id + " is already registered, overwriting");
        }
        assets.put(id, asset);
        logger.info("Registered asset: " + id);
    }
    
    /**
     * Створює та реєструє ассет за допомогою відповідного завантажувача.
     */
    public <T extends Asset> T createAsset(String id, String path, Class<T> assetClass) {
        // Determine asset type from class
        AssetType type = null;
        if (assetClass == TextureAsset.class) {
            type = AssetType.TEXTURE;
        } else if (assetClass == SoundAsset.class) {
            type = AssetType.SOUND;
        } else if (assetClass == ModelAsset.class) {
            type = AssetType.MODEL;
        } else if (assetClass == ConfigAsset.class) {
            type = AssetType.CONFIG;
        } else if (assetClass == ScriptAsset.class) {
            type = AssetType.SCRIPT;
        }
        
        if (type == null) {
            logger.error("Unsupported asset type for class: " + assetClass.getName());
            return null;
        }
        
        AssetLoader<T> loader = (AssetLoader<T>) loaders.get(type);
        if (loader == null) {
            logger.error("No loader registered for asset type: " + type);
            return null;
        }
        
        T asset = loader.loadAsset(id, path);
        registerAsset(asset);
        return asset;
    }
    
    /**
     * Видаляє ассет.
     */
    public void unregisterAsset(String id) {
        Asset asset = assets.remove(id);
        if (asset != null) {
            if (asset.isLoaded()) {
                asset.unload();
            }
            logger.info("Unregistered asset: " + id);
        }
    }
    
    /**
     * Отримує ассет за ідентифікатором.
     */
    @SuppressWarnings("unchecked")
    public <T extends Asset> T getAsset(String id, Class<T> type) {
        Asset asset = assets.get(id);
        if (asset != null && type.isInstance(asset)) {
            return (T) asset;
        }
        return null;
    }
    
    /**
     * Завантажує всі ассети.
     */
    public void loadAllAssets() {
        for (Asset asset : assets.values()) {
            if (!asset.isLoaded()) {
                try {
                    asset.load();
                    logger.info("Loaded asset: " + asset.getId());
                } catch (Exception e) {
                    logger.error("Failed to load asset: " + asset.getId(), e);
                }
            }
        }
    }
    
    /**
     * Вивантажує всі ассети.
     */
    public void unloadAllAssets() {
        for (Asset asset : assets.values()) {
            if (asset.isLoaded()) {
                try {
                    asset.unload();
                    logger.info("Unloaded asset: " + asset.getId());
                } catch (Exception e) {
                    logger.error("Failed to unload asset: " + asset.getId(), e);
                }
            }
        }
    }
}