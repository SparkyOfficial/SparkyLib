package com.sparky.minecraft.particles;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.particles.ParticleEffect;
import java.util.ArrayList;
import java.util.List;

/**
 * Система частинок для управління множиною ефектів частинок у Minecraft.
 *
 * @author Андрій Будильников
 */
public class ParticleSystem {
    private List<ParticleEffect3D> effects;
    private boolean isRunning;
    
    public ParticleSystem() {
        this.effects = new ArrayList<>();
        this.isRunning = false;
    }
    
    /**
     * Додає ефект частинок до системи.
     */
    public void addEffect(ParticleEffect3D effect) {
        effects.add(effect);
    }
    
    /**
     * Видаляє ефект частинок з системи.
     */
    public void removeEffect(ParticleEffect3D effect) {
        effects.remove(effect);
    }
    
    /**
     * Отримує список всіх ефектів частинок.
     */
    public List<ParticleEffect3D> getEffects() {
        return new ArrayList<>(effects);
    }
    
    /**
     * Запускає систему частинок.
     */
    public void start() {
        isRunning = true;
    }
    
    /**
     * Зупиняє систему частинок.
     */
    public void stop() {
        isRunning = false;
    }
    
    /**
     * Перевіряє, чи система частинок запущена.
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Оновлює всі ефекти частинок у системі.
     */
    public void update(double deltaTime) {
        if (!isRunning) return;
        
        // Оновлюємо всі ефекти
        for (int i = effects.size() - 1; i >= 0; i--) {
            ParticleEffect3D effect = effects.get(i);
            effect.update(deltaTime);
            
            // Видаляємо ефекти, які більше не живі
            if (!effect.isAlive()) {
                effects.remove(i);
            }
        }
    }
    
    /**
     * Очищує всі ефекти частинок.
     */
    public void clear() {
        effects.clear();
    }
    
    /**
     * Створює складну систему частинок для вибуху.
     */
    public static ParticleSystem createExplosionSystem(Vector3D position, int particleCount) {
        ParticleSystem system = new ParticleSystem();
        
        // Створюємо основний ефект вибуху
        ParticleEffect3D mainExplosion = ParticleEffect3D.createExplosion("main_explosion", position, particleCount);
        system.addEffect(mainExplosion);
        
        // Додаємо додаткові ефекти для більш складного вибуху
        for (int i = 0; i < 3; i++) {
            Vector3D offset = new Vector3D(
                (Math.random() - 0.5) * 2,
                (Math.random() - 0.5) * 2,
                (Math.random() - 0.5) * 2
            );
            ParticleEffect3D secondaryExplosion = ParticleEffect3D.createExplosion(
                "secondary_explosion_" + i, 
                position.add(offset), 
                particleCount / 3
            );
            system.addEffect(secondaryExplosion);
        }
        
        return system;
    }
    
    /**
     * Створює систему частинок для дощу з блискавками.
     */
    public static ParticleSystem createStormSystem(Vector3D position, Vector3D areaSize, int particleCount) {
        ParticleSystem system = new ParticleSystem();
        
        // Створюємо ефект дощу
        ParticleEffect3D rain = ParticleEffect3D.createRain("rain", position, areaSize, particleCount);
        system.addEffect(rain);
        
        // Додаємо періодичні ефекти блискавок
        // Це буде оброблено в ігровому циклі
        return system;
    }
    
    /**
     * Створює систему частинок для вогню з димом.
     */
    public static ParticleSystem createFireWithSmokeSystem(Vector3D position, double radius) {
        ParticleSystem system = new ParticleSystem();
        
        // Створюємо ефект вогню
        ParticleEffect3D fire = ParticleEffect3D.createFire("fire", position, radius);
        system.addEffect(fire);
        
        // Додаємо ефект диму
        ParticleEffect3D smoke = new ParticleEffect3D("smoke", position.add(new Vector3D(0, radius, 0)));
        smoke.setVelocity(new Vector3D(0, 2, 0)); // Дим піднімається вгору
        // Додаємо специфікації для диму
        system.addEffect(smoke);
        
        return system;
    }
    
    @Override
    public String toString() {
        return "ParticleSystem{" +
               "effectsCount=" + effects.size() +
               ", isRunning=" + isRunning +
               '}';
    }
}