package com.sparky.assets;

/**
 * Завантажувач скриптів.
 *
 * @author Богдан Кравчук
 */
public class ScriptAssetLoader implements AssetLoader<ScriptAsset> {
    
    @Override
    public ScriptAsset loadAsset(String id, String path) {
        ScriptAsset asset = new ScriptAsset(id, path);
        asset.load();
        return asset;
    }
    
    @Override
    public AssetType getSupportedAssetType() {
        return AssetType.SCRIPT;
    }
}