package com.sparky.assets;

/**
 * Завантажувач конфігурацій.
 *
 * @author Богдан Кравчук
 */
public class ConfigAssetLoader implements AssetLoader<ConfigAsset> {
    
    @Override
    public ConfigAsset loadAsset(String id, String path) {
        ConfigAsset asset = new ConfigAsset(id, path);
        asset.load();
        return asset;
    }
    
    @Override
    public AssetType getSupportedAssetType() {
        return AssetType.CONFIG;
    }
}