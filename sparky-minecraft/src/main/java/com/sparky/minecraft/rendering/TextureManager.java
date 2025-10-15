package com.sparky.minecraft.rendering;

import java.util.Map;
import java.util.HashMap;

/**
 * Менеджер текстур для рендерингу.
 *
 * @author Андрій Будильников
 */
public class TextureManager {
    private Map<String, Texture> textures;
    
    public TextureManager() {
        this.textures = new HashMap<>();
    }
    
    // завантажує стандартні текстури
    public void loadDefaultTextures() {
        // текстура блоків
        Texture blockTexture = new Texture("blocks.png");
        textures.put("blocks", blockTexture);
        
        // текстура сутностей
        Texture entityTexture = new Texture("entities.png");
        textures.put("entities", entityTexture);
        
        // текстура інтерфейсу
        Texture uiTexture = new Texture("ui.png");
        textures.put("ui", uiTexture);
        
        System.out.println("Default textures loaded");
    }
    
    // завантажує текстуру з файлу
    public void loadTexture(String name, String filePath) {
        Texture texture = new Texture(filePath);
        textures.put(name, texture);
        System.out.println("Texture loaded: " + name + " from " + filePath);
    }
    
    // отримує текстуру за назвою
    public Texture getTexture(String name) {
        return textures.get(name);
    }
    
    // видаляє текстуру
    public void removeTexture(String name) {
        Texture texture = textures.remove(name);
        if (texture != null) {
            texture.cleanup();
        }
    }
    
    // очищує всі текстури
    public void cleanup() {
        for (Texture texture : textures.values()) {
            texture.cleanup();
        }
        textures.clear();
    }
    
    // отримує кількість завантажених текстур
    public int getTextureCount() {
        return textures.size();
    }
}