package com.sparky.minecraft.rendering;

/**
 * Текстура для візуалізації.
 *
 * @author Андрій Будильников
 */
public class Texture {
    private String filePath;
    private int width;
    private int height;
    private int id;
    private boolean loaded;
    
    public Texture(String filePath) {
        this.filePath = filePath;
        this.width = 0;
        this.height = 0;
        this.id = -1;
        this.loaded = false;
        
        // тут була б логіка завантаження текстури
        loadTexture();
    }
    
    // завантажує текстуру з файлу
    private void loadTexture() {
        // тут була б реальна логіка завантаження текстури
        // для прикладу просто встановлюємо фіктивні значення
        this.width = 256;
        this.height = 256;
        this.id = filePath.hashCode(); // фіктивний ID
        this.loaded = true;
        
        System.out.println("Loading texture from: " + filePath);
    }
    
    // прив'язує текстуру для використання
    public void bind() {
        if (!loaded) {
            System.err.println("Texture not loaded: " + filePath);
            return;
        }
        
        // тут була б логіка прив'язки текстури
        System.out.println("Binding texture: " + filePath);
    }
    
    // відв'язує текстуру
    public void unbind() {
        // тут була б логіка відв'язки текстури
        System.out.println("Unbinding texture: " + filePath);
    }
    
    // очищує ресурси текстури
    public void cleanup() {
        if (loaded) {
            // тут була б логіка очищення ресурсів
            System.out.println("Cleaning up texture: " + filePath);
            loaded = false;
        }
    }
    
    // отримує шлях до файлу
    public String getFilePath() {
        return filePath;
    }
    
    // отримує ширину
    public int getWidth() {
        return width;
    }
    
    // отримує висоту
    public int getHeight() {
        return height;
    }
    
    // отримує ID текстури
    public int getId() {
        return id;
    }
    
    // перевіряє, чи завантажено
    public boolean isLoaded() {
        return loaded;
    }
}