package com.sparky.assets;

/**
 * Завантажувач текстур.
 *
 * @author Богдан Кравчук
 */
public class TextureAssetLoader implements AssetLoader<TextureAsset> {
    
    @Override
    public TextureAsset loadAsset(String id, String path) {
        TextureAsset asset = new TextureAsset(id, path);
        asset.load();
        return asset;
    }
    
    @Override
    public AssetType getSupportedAssetType() {
        return AssetType.TEXTURE;
    }
}