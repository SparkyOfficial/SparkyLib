package com.sparky.minecraft.particles;

import java.util.ArrayList;
import java.util.HashMap;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.particles.ParticleEffect;
import com.sparky.particles.ParticleSpec;

/**
 * Тривимірний ефект частинок для Minecraft з розширеними можливостями.
 *
 * @author Андрій Будильников
 */
public class ParticleEffect3D extends ParticleEffect {
    private Vector3D position;
    private Vector3D velocity;
    private Vector3D acceleration;
    private double lifetime;
    private double maxLifetime;
    private boolean isAlive;
    
    public ParticleEffect3D(String name, Vector3D position) {
        super(name, new ArrayList<>());
        this.position = new Vector3D(position);
        this.velocity = new Vector3D(0, 0, 0);
        this.acceleration = new Vector3D(0, 0, 0);
        this.lifetime = 0;
        this.maxLifetime = 5.0; // 5 секунд за замовчуванням
        this.isAlive = true;
    }
    
    public ParticleEffect3D(String name, Vector3D position, Vector3D velocity, double maxLifetime) {
        super(name, new ArrayList<>());
        this.position = new Vector3D(position);
        this.velocity = new Vector3D(velocity);
        this.acceleration = new Vector3D(0, 0, 0);
        this.lifetime = 0;
        this.maxLifetime = maxLifetime;
        this.isAlive = true;
    }
    
    
    public Vector3D getPosition() {
        return position;
    }
    
    public void setPosition(Vector3D position) {
        this.position = new Vector3D(position);
    }
    
    public Vector3D getVelocity() {
        return velocity;
    }
    
    public void setVelocity(Vector3D velocity) {
        this.velocity = new Vector3D(velocity);
    }
    
    public Vector3D getAcceleration() {
        return acceleration;
    }
    
    public void setAcceleration(Vector3D acceleration) {
        this.acceleration = new Vector3D(acceleration);
    }
    
    public double getLifetime() {
        return lifetime;
    }
    
    public double getMaxLifetime() {
        return maxLifetime;
    }
    
    public void setMaxLifetime(double maxLifetime) {
        this.maxLifetime = maxLifetime;
    }
    
    public boolean isAlive() {
        return isAlive && lifetime < maxLifetime;
    }
    
    /**
     * Оновлює стан ефекту частинок.
     */
    public void update(double deltaTime) {
        if (!isAlive()) return;
        
        // Оновлюємо час життя
        lifetime += deltaTime;
        
        // Оновлюємо швидкість згідно з прискоренням
        velocity = velocity.add(acceleration.multiply(deltaTime));
        
        // Оновлюємо позицію згідно зі швидкістю
        position = position.add(velocity.multiply(deltaTime));
        
        // Перевіряємо, чи ефект ще живий
        if (lifetime >= maxLifetime) {
            isAlive = false;
        }
    }
    
    /**
     * Застосовує силу до ефекту частинок.
     */
    public void applyForce(Vector3D force) {
        // F = ma => a = F/m (припускаємо масу = 1 для спрощення)
        acceleration = acceleration.add(force);
    }
    
    /**
     * Застосовує гравітацію.
     */
    public void applyGravity(double gravity) {
        applyForce(new Vector3D(0, -gravity, 0));
    }
    
    /**
     * Застосовує опір середовища.
     */
    public void applyDrag(double dragCoefficient) {
        Vector3D dragForce = velocity.multiply(-dragCoefficient);
        applyForce(dragForce);
    }
    
    /**
     * Відбиває частинки від поверхні.
     */
    public void bounce(Vector3D normal, double bounceFactor) {
        // R = V - 2(V·N)N
        double dotProduct = velocity.dot(normal);
        Vector3D reflection = velocity.subtract(normal.multiply(2 * dotProduct));
        velocity = reflection.multiply(bounceFactor);
    }
    
    /**
     * Створює ефект вибуху.
     */
    public static ParticleEffect3D createExplosion(String name, Vector3D position, int particleCount) {
        ParticleEffect3D explosion = new ParticleEffect3D(name, position);
        
        // Додаємо специфікації для частинок вибуху
        for (int i = 0; i < particleCount; i++) {
            // Генеруємо випадкову швидкість для кожної частинки
            double angle = Math.random() * 2 * Math.PI;
            double speed = 5 + Math.random() * 10; // Від 5 до 15 блоків/сек
            
            Vector3D velocity = new Vector3D(
                Math.cos(angle) * speed,
                Math.random() * 5, // Випадкова вертикальна швидкість
                Math.sin(angle) * speed
            );
            
            ParticleSpec spec = new ParticleSpec("flame", (float)speed, 1, new HashMap<>());
            explosion.getSpecs().add(spec);
        }
        
        return explosion;
    }
    
    /**
     * Створює ефект дощу.
     */
    public static ParticleEffect3D createRain(String name, Vector3D position, Vector3D areaSize, int particleCount) {
        ParticleEffect3D rain = new ParticleEffect3D(name, position);
        rain.setMaxLifetime(10.0); // 10 секунд
        
        // Додаємо специфікації для крапель дощу
        for (int i = 0; i < particleCount; i++) {
            // Генеруємо випадкову позицію в межах області
            Vector3D particlePosition = new Vector3D(
                position.getX() + Math.random() * areaSize.getX(),
                position.getY() + areaSize.getY(), // Починаємо з верху
                position.getZ() + Math.random() * areaSize.getZ()
            );
            
            // Швидкість спрямована вниз
            Vector3D velocity = new Vector3D(0, -15 - Math.random() * 5, 0); // Від -15 до -20 блоків/сек
            
            ParticleSpec spec = new ParticleSpec("water_splash", 0.1f, 1, new HashMap<>());
            rain.getSpecs().add(spec);
        }
        
        return rain;
    }
    
    /**
     * Створює ефект вогню.
     */
    public static ParticleEffect3D createFire(String name, Vector3D position, double radius) {
        ParticleEffect3D fire = new ParticleEffect3D(name, position);
        fire.setMaxLifetime(15.0); // 15 секунд
        
        // Додаємо специфікації для частинок вогню
        int particleCount = (int) (radius * 20); // Більше частинок для більшого вогню
        for (int i = 0; i < particleCount; i++) {
            // Генеруємо випадкову позицію навколо центру
            double angle = Math.random() * 2 * Math.PI;
            double distance = Math.random() * radius;
            
            Vector3D offset = new Vector3D(
                Math.cos(angle) * distance,
                Math.random() * radius * 0.5, // Випадкова висота
                Math.sin(angle) * distance
            );
            
            // Швидкість спрямована вгору
            Vector3D velocity = new Vector3D(
                (Math.random() - 0.5) * 2, // Невелике горизонтальне переміщення
                3 + Math.random() * 5, // Від 3 до 8 блоків/сек вгору
                (Math.random() - 0.5) * 2
            );
            
            ParticleSpec spec = new ParticleSpec("flame", 0.2f, 1, new HashMap<>());
            fire.getSpecs().add(spec);
        }
        
        return fire;
    }
    
    @Override
    public String toString() {
        return "ParticleEffect3D{" +
               "name='" + getName() + '\'' +
               ", position=" + position +
               ", velocity=" + velocity +
               ", lifetime=" + lifetime +
               ", maxLifetime=" + maxLifetime +
               ", isAlive=" + isAlive +
               '}';
    }
}