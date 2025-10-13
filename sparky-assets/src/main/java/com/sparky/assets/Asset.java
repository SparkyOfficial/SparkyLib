package com.sparky.assets;

/**
 * Інтерфейс для ассета.
 *
 * @author Андрій Будильников
 */
public interface Asset {
    /**
     * Отримує ідентифікатор ассета.
     */
    String getId();
    
    /**
     * Отримує тип ассета.
     */
    AssetType getType();
    
    /**
     * Завантажує ассет.
     */
    void load();
    
    /**
     * Вивантажує ассет.
     */
    void unload();
    
    /**
     * Перевіряє, чи ассет завантажено.
     */
    boolean isLoaded();
}