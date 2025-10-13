package com.sparky.assets;

/**
 * Завантажувач моделей.
 *
 * @author Богдан Кравчук
 */
public class ModelAssetLoader implements AssetLoader<ModelAsset> {
    
    @Override
    public ModelAsset loadAsset(String id, String path) {
        ModelAsset asset = new ModelAsset(id, path);
        asset.load();
        return asset;
    }
    
    @Override
    public AssetType getSupportedAssetType() {
        return AssetType.MODEL;
    }
}