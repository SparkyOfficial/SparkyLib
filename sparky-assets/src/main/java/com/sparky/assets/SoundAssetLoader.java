package com.sparky.assets;

/**
 * Завантажувач звуків.
 *
 * @author Богдан Кравчук
 */
public class SoundAssetLoader implements AssetLoader<SoundAsset> {
    
    @Override
    public SoundAsset loadAsset(String id, String path) {
        SoundAsset asset = new SoundAsset(id, path);
        asset.load();
        return asset;
    }
    
    @Override
    public AssetType getSupportedAssetType() {
        return AssetType.SOUND;
    }
}