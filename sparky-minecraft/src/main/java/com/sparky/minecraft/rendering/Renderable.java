package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.math.Matrix4x4;

/**
 * Інтерфейс для об'єктів, які можна рендерити.
 *
 * @author Андрій Будильников
 */
public interface Renderable {
    // отримує модельну матрицю
    Matrix4x4 getModelMatrix();
    
    // отримує позицію
    Vector3D getPosition();
    
    // отримує назву текстури
    String getTextureName();
    
    // отримує назву шейдера
    String getShaderName();
}