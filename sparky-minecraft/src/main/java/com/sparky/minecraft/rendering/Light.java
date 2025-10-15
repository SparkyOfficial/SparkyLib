package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;

/**
 * Абстрактний клас для джерел світла.
 *
 * @author Андрій Будильников
 */
public abstract class Light {
    protected Vector3D color;
    protected double intensity;
    
    public Light(Vector3D color, double intensity) {
        this.color = new Vector3D(color);
        this.intensity = intensity;
    }
    
    // обчислює внесок світла в точку
    public abstract Vector3D calculateLighting(Vector3D position);
    
    // отримує колір світла
    public Vector3D getColor() {
        return new Vector3D(color);
    }
    
    // встановлює колір світла
    public void setColor(Vector3D color) {
        this.color = new Vector3D(color);
    }
    
    // отримує інтенсивність
    public double getIntensity() {
        return intensity;
    }
    
    // встановлює інтенсивність
    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}