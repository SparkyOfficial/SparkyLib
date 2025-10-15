package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.math.Matrix4x4;

/**
 * Камера для перегляду 3D світу.
 *
 * @author Андрій Будильников
 */
public class Camera {
    private Vector3D position;
    private Vector3D rotation;
    private double fov; // поле зору в радіанах
    private double aspectRatio;
    private double nearPlane;
    private double farPlane;
    
    public Camera() {
        this.position = new Vector3D(0, 0, 0);
        this.rotation = new Vector3D(0, 0, 0);
        this.fov = Math.toRadians(50); // 50 градусів
        this.aspectRatio = 16.0 / 9.0;
        this.nearPlane = 0.1;
        this.farPlane = 1000.0;
    }
    
    // оновлює камеру
    public void update() {
        // тут була б логіка оновлення камери
        // наприклад, обробка вводу користувача
    }
    
    // отримує матрицю вигляду
    public Matrix4x4 getViewMatrix() {
        // створюємо матрицю перетворення камери
        Matrix4x4 translation = Matrix4x4.translation(-position.getX(), -position.getY(), -position.getZ());
        Matrix4x4 rotationX = Matrix4x4.rotationX(-rotation.getX());
        Matrix4x4 rotationY = Matrix4x4.rotationY(-rotation.getY());
        
        // комбінуємо перетворення
        return rotationY.multiply(rotationX).multiply(translation);
    }
    
    // отримує матрицю проекції
    public Matrix4x4 getProjectionMatrix() {
        return Matrix4x4.perspective(fov, aspectRatio, nearPlane, farPlane);
    }
    
    // встановлює позицію
    public void setPosition(Vector3D position) {
        this.position = new Vector3D(position);
    }
    
    // отримує позицію
    public Vector3D getPosition() {
        return new Vector3D(position);
    }
    
    // встановлює обертання
    public void setRotation(Vector3D rotation) {
        this.rotation = new Vector3D(rotation);
    }
    
    // отримує обертання
    public Vector3D getRotation() {
        return new Vector3D(rotation);
    }
    
    // встановлює поле зору
    public void setFov(double fovDegrees) {
        this.fov = Math.toRadians(fovDegrees);
    }
    
    // отримує поле зору в градусах
    public double getFov() {
        return Math.toDegrees(fov);
    }
    
    // встановлює співвідношення сторін
    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
    
    // отримує співвідношення сторін
    public double getAspectRatio() {
        return aspectRatio;
    }
    
    // встановлює ближню площину
    public void setNearPlane(double nearPlane) {
        this.nearPlane = nearPlane;
    }
    
    // отримує ближню площину
    public double getNearPlane() {
        return nearPlane;
    }
    
    // встановлює дальню площину
    public void setFarPlane(double farPlane) {
        this.farPlane = farPlane;
    }
    
    // отримує дальню площину
    public double getFarPlane() {
        return farPlane;
    }
    
    // переміщує камеру вперед
    public void moveForward(double distance) {
        // обчислюємо вектор напрямку
        double yaw = rotation.getY();
        Vector3D forward = new Vector3D(
            Math.sin(yaw),
            0,
            Math.cos(yaw)
        );
        
        position = position.add(forward.multiply(distance));
    }
    
    // переміщує камеру вправо
    public void moveRight(double distance) {
        // обчислюємо вектор напрямку
        double yaw = rotation.getY();
        Vector3D right = new Vector3D(
            Math.sin(yaw + Math.PI/2),
            0,
            Math.cos(yaw + Math.PI/2)
        );
        
        position = position.add(right.multiply(distance));
    }
    
    // переміщує камеру вгору
    public void moveUp(double distance) {
        position = position.add(new Vector3D(0, distance, 0));
    }
}