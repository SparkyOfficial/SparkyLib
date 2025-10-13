package com.sparky.minecraft.particles;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.particles.ParticleEffect3D;
import com.sparky.minecraft.particles.ParticleSystem;

/**
 * Приклад використання розширеної системи частинок для Minecraft.
 *
 * @author Андрій Будильников
 */
public class ParticleExample {
    
    /**
     * Демонстрація створення та використання різних ефектів частинок.
     */
    public static void demonstrateParticleEffects() {
        System.out.println("=== Демонстрація розширеної системи частинок ===");
        
        // Створюємо позицію для ефектів
        Vector3D position = new Vector3D(0, 64, 0); // Висота 64 (рівень моря в Minecraft)
        
        // 1. Створюємо простий ефект частинок
        System.out.println("1. Створення простого ефекту частинок:");
        ParticleEffect3D simpleEffect = new ParticleEffect3D("simple", position);
        System.out.println("   Створено ефект: " + simpleEffect);
        
        // 2. Створюємо ефект частинок зі швидкістю
        System.out.println("2. Створення ефекту частинок зі швидкістю:");
        Vector3D velocity = new Vector3D(1, 2, 0); // Рухається вгору та вправо
        ParticleEffect3D movingEffect = new ParticleEffect3D("moving", position, velocity, 3.0);
        System.out.println("   Створено ефект: " + movingEffect);
        
        // 3. Створюємо ефект вибуху
        System.out.println("3. Створення ефекту вибуху:");
        ParticleEffect3D explosion = ParticleEffect3D.createExplosion("explosion", position, 20);
        System.out.println("   Створено ефект вибуху з " + explosion.getSpecs().size() + " частинками");
        
        // 4. Створюємо ефект дощу
        System.out.println("4. Створення ефекту дощу:");
        Vector3D areaSize = new Vector3D(10, 5, 10); // Область 10x5x10 блоків
        ParticleEffect3D rain = ParticleEffect3D.createRain("rain", position, areaSize, 50);
        System.out.println("   Створено ефект дощу з " + rain.getSpecs().size() + " краплями");
        
        // 5. Створюємо ефект вогню
        System.out.println("5. Створення ефекту вогню:");
        ParticleEffect3D fire = ParticleEffect3D.createFire("fire", position, 3.0);
        System.out.println("   Створено ефект вогню з " + fire.getSpecs().size() + " частинками");
        
        // 6. Створюємо систему частинок
        System.out.println("6. Створення системи частинок:");
        ParticleSystem system = new ParticleSystem();
        system.addEffect(simpleEffect);
        system.addEffect(movingEffect);
        system.addEffect(explosion);
        System.out.println("   Створено систему з " + system.getEffects().size() + " ефектами");
        
        // 7. Запускаємо систему
        System.out.println("7. Запуск системи частинок:");
        system.start();
        System.out.println("   Система запущена: " + system.isRunning());
        
        // 8. Створюємо складну систему вибуху
        System.out.println("8. Створення складної системи вибуху:");
        ParticleSystem explosionSystem = ParticleSystem.createExplosionSystem(position, 30);
        System.out.println("   Створено систему вибуху з " + explosionSystem.getEffects().size() + " ефектами");
        
        // 9. Створюємо систему бурі
        System.out.println("9. Створення системи бурі:");
        ParticleSystem stormSystem = ParticleSystem.createStormSystem(position, areaSize, 100);
        System.out.println("   Створено систему бурі з " + stormSystem.getEffects().size() + " ефектами");
        
        // 10. Створюємо систему вогню з димом
        System.out.println("10. Створення системи вогню з димом:");
        ParticleSystem fireSystem = ParticleSystem.createFireWithSmokeSystem(position, 2.0);
        System.out.println("    Створено систему вогню з димом з " + fireSystem.getEffects().size() + " ефектами");
        
        // 11. Демонстрація фізики частинок
        System.out.println("11. Демонстрація фізики частинок:");
        ParticleEffect3D physicsEffect = new ParticleEffect3D("physics", position);
        physicsEffect.setVelocity(new Vector3D(5, 10, 0)); // Початкова швидкість
        physicsEffect.applyGravity(9.8); // Застосовуємо гравітацію
        physicsEffect.applyDrag(0.1); // Застосовуємо опір повітря
        System.out.println("    Створено ефект з фізикою: " + physicsEffect);
        System.out.println("    Початкова швидкість: " + physicsEffect.getVelocity());
        System.out.println("    Початкове прискорення: " + physicsEffect.getAcceleration());
        
        // 12. Оновлення ефекту
        System.out.println("12. Оновлення ефекту протягом 0.1 секунди:");
        physicsEffect.update(0.1);
        System.out.println("    Нова позиція: " + physicsEffect.getPosition());
        System.out.println("    Нова швидкість: " + physicsEffect.getVelocity());
        
        System.out.println("=== Демонстрація завершена ===");
    }
    
    public static void main(String[] args) {
        demonstrateParticleEffects();
    }
}