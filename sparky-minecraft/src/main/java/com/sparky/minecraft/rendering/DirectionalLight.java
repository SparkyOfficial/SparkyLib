package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;

/**
 * Направлене джерело світла (наприклад, сонце).
 *
 * @author Андрій Будильников
 */
public class DirectionalLight extends Light {
    private Vector3D direction;
    
    public DirectionalLight(Vector3D color, Vector3D direction, double intensity) {
        super(color, intensity);
        this.direction = new Vector3D(direction).normalize();
    }
    
    // обчислює внесок світла в точку
    @Override
    public Vector3D calculateLighting(Vector3D position) {
        // для направленого світла відстань не має значення
        // просто множимо колір на інтенсивність
        return color.multiply(intensity);
    }
    
    // отримує напрямок світла
    public Vector3D getDirection() {
        return new Vector3D(direction);
    }
    
    // встановлює напрямок світла
    public void setDirection(Vector3D direction) {
        this.direction = new Vector3D(direction).normalize();
    }
}