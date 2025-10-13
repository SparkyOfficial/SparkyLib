package com.sparky.minecraft;

import com.sparky.minecraft.math.ComplexNumber;
import com.sparky.minecraft.math.Matrix4x4;
import com.sparky.minecraft.math.TrigonometryUtils;
import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.particles.ParticleEffect3D;
import com.sparky.minecraft.particles.ParticleSystem;

/**
 * Демонстрація всіх можливостей бібліотеки Sparky для Minecraft.
 *
 * @author Андрій Будильников
 */
public class MinecraftDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрація бібліотеки Sparky для Minecraft ===\n");
        
        // Демонстрація математичних можливостей
        demonstrateMathematics();
        
        System.out.println();
        
        // Демонстрація можливостей частинок
        demonstrateParticles();
        
        System.out.println("\n=== Демонстрація завершена ===");
    }
    
    /**
     * Демонстрація математичних можливостей.
     */
    private static void demonstrateMathematics() {
        System.out.println("1. Математичні можливості:");
        
        // Векторна математика
        System.out.println("  1.1. Векторна математика:");
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(4, 5, 6);
        Vector3D sum = v1.add(v2);
        Vector3D cross = v1.cross(v2);
        double dot = v1.dot(v2);
        System.out.println("    Вектор 1: " + v1);
        System.out.println("    Вектор 2: " + v2);
        System.out.println("    Сума: " + sum);
        System.out.println("    Векторний добуток: " + cross);
        System.out.println("    Скалярний добуток: " + dot);
        
        // Тригонометрія
        System.out.println("  1.2. Тригонометричні функції:");
        double angle = TrigonometryUtils.normalizeAngle(Math.PI * 3);
        Vector3D spherical = TrigonometryUtils.sphericalToCartesian(5, Math.PI/4, Math.PI/3);
        System.out.println("    Нормалізований кут: " + angle);
        System.out.println("    Сферичні координати в декартові: " + spherical);
        
        // Комплексні числа
        System.out.println("  1.3. Комплексні числа:");
        ComplexNumber c1 = new ComplexNumber(3, 4);
        ComplexNumber c2 = new ComplexNumber(1, 2);
        ComplexNumber cSum = c1.add(c2);
        ComplexNumber cProduct = c1.multiply(c2);
        System.out.println("    Число 1: " + c1);
        System.out.println("    Число 2: " + c2);
        System.out.println("    Сума: " + cSum);
        System.out.println("    Добуток: " + cProduct);
        
        // Матриці
        System.out.println("  1.4. Матричні операції:");
        Matrix4x4 translation = Matrix4x4.translation(1, 2, 3);
        Matrix4x4 rotation = Matrix4x4.rotationY(Math.PI/2);
        Matrix4x4 transform = translation.multiply(rotation);
        Vector3D transformed = transform.transform(new Vector3D(0, 0, 1));
        System.out.println("    Матриця трансляції:\n" + translation);
        System.out.println("    Матриця обертання:\n" + rotation);
        System.out.println("    Комбінована трансформація:\n" + transform);
        System.out.println("    Трансформований вектор: " + transformed);
    }
    
    /**
     * Демонстрація можливостей частинок.
     */
    private static void demonstrateParticles() {
        System.out.println("2. Можливості частинок:");
        
        // Створення ефектів частинок
        Vector3D position = new Vector3D(0, 64, 0);
        
        System.out.println("  2.1. Створення простого ефекту:");
        ParticleEffect3D simpleEffect = new ParticleEffect3D("simple", position);
        System.out.println("    Створено: " + simpleEffect);
        
        System.out.println("  2.2. Створення ефекту з фізикою:");
        Vector3D velocity = new Vector3D(2, 5, 1);
        ParticleEffect3D physicsEffect = new ParticleEffect3D("physics", position, velocity, 3.0);
        physicsEffect.applyGravity(9.8);
        physicsEffect.applyDrag(0.05);
        System.out.println("    Створено: " + physicsEffect);
        System.out.println("    Початкова швидкість: " + physicsEffect.getVelocity());
        System.out.println("    Початкове прискорення: " + physicsEffect.getAcceleration());
        
        // Оновлення ефекту
        System.out.println("  2.3. Оновлення ефекту:");
        physicsEffect.update(0.5);
        System.out.println("    Позиція після 0.5с: " + physicsEffect.getPosition());
        System.out.println("    Швидкість після 0.5с: " + physicsEffect.getVelocity());
        
        // Створення попередньо визначених ефектів
        System.out.println("  2.4. Попередньо визначені ефекти:");
        ParticleEffect3D explosion = ParticleEffect3D.createExplosion("demo_explosion", position, 15);
        ParticleEffect3D rain = ParticleEffect3D.createRain("demo_rain", position, new Vector3D(5, 0, 5), 25);
        ParticleEffect3D fire = ParticleEffect3D.createFire("demo_fire", position, 2.0);
        System.out.println("    Вибух з " + explosion.getSpecs().size() + " частинками");
        System.out.println("    Дощ з " + rain.getSpecs().size() + " краплями");
        System.out.println("    Вогонь з " + fire.getSpecs().size() + " частинками");
        
        // Система частинок
        System.out.println("  2.5. Система частинок:");
        ParticleSystem system = new ParticleSystem();
        system.addEffect(simpleEffect);
        system.addEffect(physicsEffect);
        system.addEffect(explosion);
        System.out.println("    Створено систему з " + system.getEffects().size() + " ефектами");
        
        system.start();
        System.out.println("    Система запущена: " + system.isRunning());
        
        // Складніші системи
        System.out.println("  2.6. Складні системи:");
        ParticleSystem explosionSystem = ParticleSystem.createExplosionSystem(position, 20);
        ParticleSystem fireSystem = ParticleSystem.createFireWithSmokeSystem(position, 1.5);
        System.out.println("    Система вибуху з " + explosionSystem.getEffects().size() + " ефектами");
        System.out.println("    Система вогню з димом з " + fireSystem.getEffects().size() + " ефектами");
    }
}