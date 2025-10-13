package com.sparky.assets;

/**
 * Тип ассета.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public enum AssetType {
    TEXTURE(TextureAsset.class),
    MODEL(ModelAsset.class),
    SOUND(SoundAsset.class),
    CONFIG(ConfigAsset.class),
    SCRIPT(ScriptAsset.class);
    
    private final Class<? extends Asset> assetClass;
    
    AssetType(Class<? extends Asset> assetClass) {
        this.assetClass = assetClass;
    }
    
    public Class<? extends Asset> getAssetClass() {
        return assetClass;
    }
}