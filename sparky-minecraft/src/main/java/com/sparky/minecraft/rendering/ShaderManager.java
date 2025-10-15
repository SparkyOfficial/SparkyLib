package com.sparky.minecraft.rendering;

import java.util.Map;
import java.util.HashMap;

/**
 * Менеджер шейдерів для рендерингу.
 *
 * @author Андрій Будильников
 */
public class ShaderManager {
    private Map<String, ShaderProgram> shaders;
    
    public ShaderManager() {
        this.shaders = new HashMap<>();
    }
    
    // завантажує стандартні шейдери
    public void loadDefaultShaders() {
        // базовий шейдер для блоків
        ShaderProgram blockShader = new ShaderProgram(
            "block_vertex.glsl",
            "block_fragment.glsl"
        );
        shaders.put("block", blockShader);
        
        // шейдер для сутностей
        ShaderProgram entityShader = new ShaderProgram(
            "entity_vertex.glsl",
            "entity_fragment.glsl"
        );
        shaders.put("entity", entityShader);
        
        // шейдер для інтерфейсу
        ShaderProgram uiShader = new ShaderProgram(
            "ui_vertex.glsl",
            "ui_fragment.glsl"
        );
        shaders.put("ui", uiShader);
        
        System.out.println("Default shaders loaded");
    }
    
    // завантажує шейдер з файлів
    public void loadShader(String name, String vertexFile, String fragmentFile) {
        ShaderProgram shader = new ShaderProgram(vertexFile, fragmentFile);
        shaders.put(name, shader);
        System.out.println("Shader loaded: " + name);
    }
    
    // отримує шейдер за назвою
    public ShaderProgram getShader(String name) {
        return shaders.get(name);
    }
    
    // видаляє шейдер
    public void removeShader(String name) {
        ShaderProgram shader = shaders.remove(name);
        if (shader != null) {
            shader.cleanup();
        }
    }
    
    // очищує всі шейдери
    public void cleanup() {
        for (ShaderProgram shader : shaders.values()) {
            shader.cleanup();
        }
        shaders.clear();
    }
    
    // отримує кількість завантажених шейдерів
    public int getShaderCount() {
        return shaders.size();
    }
}