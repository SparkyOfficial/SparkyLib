package com.sparky.minecraft.physics;

import com.sparky.minecraft.math.Vector3D;

/**
 * Тверде тіло для фізики в Minecraft.
 *
 * @author Андрій Будильников
 */
public class RigidBody {
    private Vector3D position;
    private Vector3D velocity;
    private Vector3D acceleration;
    private Vector3D rotation;
    private Vector3D angularVelocity;
    private double mass;
    private double restitution; // коефіцієнт відновлення
    private double friction; // коефіцієнт тертя
    private boolean affectedByGravity;
    private boolean isStatic;
    
    public RigidBody(Vector3D position, double mass) {
        this.position = new Vector3D(position);
        this.velocity = new Vector3D(0, 0, 0);
        this.acceleration = new Vector3D(0, 0, 0);
        this.rotation = new Vector3D(0, 0, 0);
        this.angularVelocity = new Vector3D(0, 0, 0);
        this.mass = mass;
        this.restitution = 0.5;
        this.friction = 0.1;
        this.affectedByGravity = true;
        this.isStatic = false;
    }
    
    // оновлює стан тіла
    public void update(double deltaTime) {
        if (isStatic) return;
        
        // застосовуємо гравітацію
        if (affectedByGravity) {
            applyForce(new Vector3D(0, -9.81 * mass, 0));
        }
        
        // оновлюємо швидкість
        velocity = velocity.add(acceleration.multiply(deltaTime));
        
        // оновлюємо позицію
        position = position.add(velocity.multiply(deltaTime));
        
        // оновлюємо обертання
        rotation = rotation.add(angularVelocity.multiply(deltaTime));
        
        // скидаємо прискорення
        acceleration = new Vector3D(0, 0, 0);
    }
    
    // застосовує силу до тіла
    public void applyForce(Vector3D force) {
        if (isStatic) return;
        
        // F = ma => a = F/m
        Vector3D forceAcceleration = force.divide(mass);
        acceleration = acceleration.add(forceAcceleration);
    }
    
    // застосовує імпульс до тіла
    public void applyImpulse(Vector3D impulse) {
        if (isStatic) return;
        
        // J = Δp = mΔv => Δv = J/m
        Vector3D deltaVelocity = impulse.divide(mass);
        velocity = velocity.add(deltaVelocity);
    }
    
    // обробляє зіткнення з іншим тілом
    public void handleCollision(RigidBody other, Vector3D collisionNormal) {
        if (isStatic && other.isStatic) return;
        
        // обчислюємо відносну швидкість
        Vector3D relativeVelocity = velocity.subtract(other.velocity);
        double velocityAlongNormal = relativeVelocity.dot(collisionNormal);
        
        // не робимо нічого, якщо тіла віддаляються
        if (velocityAlongNormal > 0) return;
        
        // обчислюємо імпульс
        double restitution = Math.min(this.restitution, other.restitution);
        double j = -(1 + restitution) * velocityAlongNormal;
        j /= (1/this.mass + 1/other.mass);
        
        // застосовуємо імпульс
        Vector3D impulse = collisionNormal.multiply(j);
        applyImpulse(impulse.multiply(-1));
        other.applyImpulse(impulse);
    }
    
    // отримує позицію
    public Vector3D getPosition() {
        return new Vector3D(position);
    }
    
    // встановлює позицію
    public void setPosition(Vector3D position) {
        this.position = new Vector3D(position);
    }
    
    // отримує швидкість
    public Vector3D getVelocity() {
        return new Vector3D(velocity);
    }
    
    // встановлює швидкість
    public void setVelocity(Vector3D velocity) {
        this.velocity = new Vector3D(velocity);
    }
    
    // отримує масу
    public double getMass() {
        return mass;
    }
    
    // встановлює масу
    public void setMass(double mass) {
        this.mass = mass;
    }
    
    // перевіряє, чи тіло статичне
    public boolean isStatic() {
        return isStatic;
    }
    
    // встановлює, чи тіло статичне
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
}