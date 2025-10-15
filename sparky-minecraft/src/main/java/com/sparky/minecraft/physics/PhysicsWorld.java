package com.sparky.minecraft.physics;

import java.util.ArrayList;
import java.util.List;

import com.sparky.minecraft.math.Vector3D;

/**
 * Світ фізики для симуляції в Minecraft.
 *
 * @author Андрій Будильников
 */
public class PhysicsWorld {
    private List<RigidBody> bodies;
    private double gravity;
    
    public PhysicsWorld() {
        this.bodies = new ArrayList<>();
        this.gravity = 9.81;
    }
    
    // додає тіло до світу
    public void addBody(RigidBody body) {
        bodies.add(body);
    }
    
    // видаляє тіло зі світу
    public void removeBody(RigidBody body) {
        bodies.remove(body);
    }
    
    // оновлює всі тіла у світі
    public void update(double deltaTime) {
        // оновлюємо всі тіла
        for (RigidBody body : bodies) {
            body.update(deltaTime);
        }
        
        // перевіряємо зіткнення
        checkCollisions();
    }
    
    // перевіряє зіткнення між тілами
    private void checkCollisions() {
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                RigidBody bodyA = bodies.get(i);
                RigidBody bodyB = bodies.get(j);
                
                // перевіряємо зіткнення
                CollisionInfo collision = checkCollision(bodyA, bodyB);
                if (collision != null) {
                    // обробляємо зіткнення
                    bodyA.handleCollision(bodyB, collision.normal);
                }
            }
        }
    }
    
    // перевіряє зіткнення між двома тілами
    private CollisionInfo checkCollision(RigidBody bodyA, RigidBody bodyB) {
        // для спрощення перевіряємо лише відстань між центрами
        Vector3D posA = bodyA.getPosition();
        Vector3D posB = bodyB.getPosition();
        double distance = posA.distance(posB);
        
        // якщо відстань менша за суму радіусів, то зіткнення
        // тут ми припускаємо, що всі тіла мають радіус 0.5
        if (distance < 1.0) {
            // нормаль зіткнення
            Vector3D normal = posB.subtract(posA).normalize();
            return new CollisionInfo(normal);
        }
        
        return null;
    }
    
    // внутрішній клас для інформації про зіткнення
    private static class CollisionInfo {
        Vector3D normal;
        
        CollisionInfo(Vector3D normal) {
            this.normal = normal;
        }
    }
    
    // встановлює гравітацію
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    
    // отримує гравітацію
    public double getGravity() {
        return gravity;
    }
}