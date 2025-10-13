package com.sparky.assets;

/**
 * Інтерфейс для завантажувача ассетів.
 *
 * @author Богдан Кравчук
 */
public interface AssetLoader<T extends Asset> {
    /**
     * Завантажує ассет.
     */
    T loadAsset(String id, String path);
    
    /**
     * Отримує тип ассета, який підтримує цей завантажувач.
     */
    AssetType getSupportedAssetType();
}