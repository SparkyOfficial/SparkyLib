package com.sparky.minecraft.rendering;

/**
 * Програма шейдера для графічного рендерингу.
 *
 * @author Андрій Будильников
 */
public class ShaderProgram {
    private String vertexShaderFile;
    private String fragmentShaderFile;
    private boolean compiled;
    
    public ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
        this.vertexShaderFile = vertexShaderFile;
        this.fragmentShaderFile = fragmentShaderFile;
        this.compiled = false;
        
        // тут була б логіка компіляції шейдерів
        compileShaders();
    }
    
    // компілює шейдери
    private void compileShaders() {
        // тут була б реальна логіка компіляції шейдерів
        // для прикладу просто встановлюємо прапорець
        this.compiled = true;
        System.out.println("Compiling shaders: " + vertexShaderFile + ", " + fragmentShaderFile);
    }
    
    // використовує програму шейдера
    public void use() {
        if (!compiled) {
            System.err.println("Shader program not compiled");
            return;
        }
        
        // тут була б логіка активації шейдера
        System.out.println("Using shader program");
    }
    
    // очищує ресурси шейдера
    public void cleanup() {
        // тут була б логіка очищення ресурсів
        System.out.println("Cleaning up shader program");
    }
    
    // встановлює цілочисельний параметр
    public void setUniform(String name, int value) {
        // тут була б логіка встановлення уніформи
        System.out.println("Setting uniform " + name + " = " + value);
    }
    
    // встановлює параметр з плаваючою комою
    public void setUniform(String name, float value) {
        // тут була б логіка встановлення уніформи
        System.out.println("Setting uniform " + name + " = " + value);
    }
    
    // встановлює векторний параметр
    public void setUniform(String name, com.sparky.minecraft.math.Vector3D value) {
        // тут була б логіка встановлення уніформи
        System.out.println("Setting uniform " + name + " = " + value);
    }
    
    // встановлює матричний параметр
    public void setUniform(String name, com.sparky.minecraft.math.Matrix4x4 value) {
        // тут була б логіка встановлення уніформи
        System.out.println("Setting uniform " + name);
    }
    
    // перевіряє, чи скомпільовано
    public boolean isCompiled() {
        return compiled;
    }
    
    // отримує файл вершинного шейдера
    public String getVertexShaderFile() {
        return vertexShaderFile;
    }
    
    // отримує файл фрагментного шейдера
    public String getFragmentShaderFile() {
        return fragmentShaderFile;
    }
}