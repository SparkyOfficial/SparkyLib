package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;

/**
 * Точкове джерело світла (наприклад, ліхтар).
 *
 * @author Андрій Будильников
 */
public class PointLight extends Light {
    private Vector3D position;
    private double constant;
    private double linear;
    private double quadratic;
    
    public PointLight(Vector3D color, Vector3D position, double intensity) {
        super(color, intensity);
        this.position = new Vector3D(position);
        this.constant = 1.0;
        this.linear = 0.09;
        this.quadratic = 0.032;
    }
    
    // обчислює внесок світла в точку
    @Override
    public Vector3D calculateLighting(Vector3D pointPosition) {
        // обчислюємо відстань до точки
        double distance = position.distance(pointPosition);
        
        // обчислюємо затухання
        double attenuation = 1.0 / (constant + linear * distance + quadratic * distance * distance);
        
        // множимо колір на інтенсивність та затухання
        return color.multiply(intensity * attenuation);
    }
    
    // отримує позицію світла
    public Vector3D getPosition() {
        return new Vector3D(position);
    }
    
    // встановлює позицію світла
    public void setPosition(Vector3D position) {
        this.position = new Vector3D(position);
    }
    
    // встановлює коефіцієнти затухання
    public void setAttenuation(double constant, double linear, double quadratic) {
        this.constant = constant;
        this.linear = linear;
        this.quadratic = quadratic;
    }
}