package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.minecraft.math.BoundingBox;
import com.sparky.minecraft.math.ComplexNumber;
import com.sparky.minecraft.math.Matrix4x4;
import com.sparky.minecraft.math.Quaternion;
import com.sparky.minecraft.math.TrigonometryUtils;
import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.particles.ParticleEffect3D;
import com.sparky.minecraft.particles.ParticlePhysics;
import com.sparky.minecraft.particles.ParticleSystem;
// додаємо імпорти нових функцій
import com.sparky.minecraft.world.ProceduralWorldGenerator;
import com.sparky.minecraft.world.ChunkData;
import com.sparky.minecraft.ai.BehaviorTree;
import com.sparky.minecraft.pathfinding.AStarPathfinder;
import com.sparky.minecraft.pathfinding.WorldGrid;
import com.sparky.minecraft.physics.PhysicsWorld;
import com.sparky.minecraft.physics.RigidBody;
import com.sparky.minecraft.audio.AudioManager;
import com.sparky.minecraft.rendering.RenderEngine;

/**
 * Повна демонстрація бібліотеки Sparky для Minecraft.
 *
 * @author Андрій Будильников
 */
public class MinecraftFullDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Повна демонстрація бібліотеки Sparky для Minecraft ===\n");
        
        // Демонстрація математичних можливостей
        demonstrateMathematics();
        
        System.out.println();
        
        // Демонстрація фізичних можливостей частинок
        demonstrateParticlePhysics();
        
        System.out.println();
        
        // Демонстрація ECS систем
        demonstrateECS();
        
        System.out.println();
        
        // Демонстрація нових розширених можливостей
        demonstrateAdvancedFeatures();
        
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
        System.out.println("    Вектор 1: " + v1);
        System.out.println("    Вектор 2: " + v2);
        System.out.println("    Сума: " + v1.add(v2));
        System.out.println("    Скалярний добуток: " + v1.dot(v2));
        System.out.println("    Векторний добуток: " + v1.cross(v2));
        
        // Кватерніони
        System.out.println("  1.2. Кватерніони:");
        Quaternion q1 = new Quaternion(1, 0, 0, 0);
        Quaternion q2 = Quaternion.fromAxisAngle(new Vector3D(0, 1, 0), Math.PI / 2);
        System.out.println("    Кватерніон 1: " + q1);
        System.out.println("    Кватерніон 2 (обертання 90° навколо Y): " + q2);
        System.out.println("    Добуток: " + q1.multiply(q2));
        
        // Обмежувальні коробки
        System.out.println("  1.3. Обмежувальні коробки:");
        BoundingBox box1 = new BoundingBox(0, 0, 0, 2, 2, 2);
        BoundingBox box2 = new BoundingBox(1, 1, 1, 3, 3, 3);
        System.out.println("    Коробка 1: " + box1);
        System.out.println("    Коробка 2: " + box2);
        System.out.println("    Перетинаються: " + box1.intersects(box2));
        System.out.println("    Об'єднання: " + box1.union(box2));
        
        // Тригонометрія
        System.out.println("  1.4. Тригонометрія:");
        double angle = TrigonometryUtils.normalizeAngle(Math.PI * 3);
        System.out.println("    Нормалізований кут: " + angle);
        
        // Комплексні числа
        System.out.println("  1.5. Комплексні числа:");
        ComplexNumber c1 = new ComplexNumber(3, 4);
        ComplexNumber c2 = new ComplexNumber(1, 2);
        System.out.println("    Число 1: " + c1);
        System.out.println("    Число 2: " + c2);
        System.out.println("    Сума: " + c1.add(c2));
        
        // Матриці
        System.out.println("  1.6. Матриці:");
        Matrix4x4 translation = Matrix4x4.translation(1, 2, 3);
        Matrix4x4 rotation = Matrix4x4.rotationY(Math.PI / 4);
        System.out.println("    Матриця трансляції:\n" + translation);
        System.out.println("    Матриця обертання:\n" + rotation);
    }
    
    /**
     * Демонстрація фізичних можливостей частинок.
     */
    private static void demonstrateParticlePhysics() {
        System.out.println("2. Фізичні можливості частинок:");
        
        // Створення фізичної частинки
        System.out.println("  2.1. Фізична частинка:");
        ParticlePhysics particle = new ParticlePhysics(new Vector3D(0, 10, 0));
        particle.setVelocity(new Vector3D(0, 5, 0));
        particle.setMass(2.0);
        particle.setDragCoefficient(0.1);
        System.out.println("    Початковий стан: " + particle);
        
        // Оновлення стану частинки
        System.out.println("  2.2. Оновлення стану:");
        for (int i = 0; i < 5; i++) {
            particle.update(0.1); // Оновлюємо 100 разів на секунду
            System.out.println("    Кадр " + i + ": " + particle.getPosition());
        }
        
        // Зіткнення з площиною
        System.out.println("  2.3. Зіткнення з площиною:");
        particle.setPosition(new Vector3D(0, -1, 0));
        particle.setVelocity(new Vector3D(0, -3, 0));
        particle.collideWithPlane(new Vector3D(0, 1, 0), 0); // Площина Y=0
        System.out.println("    Після зіткнення: " + particle);
        
        // Створення системи частинок
        System.out.println("  2.4. Система частинок:");
        ParticleSystem system = new ParticleSystem();
        ParticleEffect3D effect1 = new ParticleEffect3D("effect1", new Vector3D(0, 0, 0));
        ParticleEffect3D effect2 = ParticleEffect3D.createExplosion("explosion", new Vector3D(5, 5, 5), 10);
        system.addEffect(effect1);
        system.addEffect(effect2);
        system.start();
        System.out.println("    Система створена з " + system.getEffects().size() + " ефектами");
    }
    
    /**
     * Демонстрація ECS систем.
     */
    private static void demonstrateECS() {
        System.out.println("3. ECS системи:");
        
        // Створення менеджера сутностей
        System.out.println("  3.1. Менеджер сутностей:");
        EntityManager entityManager = new EntityManager();
        
        // Створення систем
        WorldSystem worldSystem = new WorldSystem(entityManager);
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        // Створення світу
        System.out.println("  3.2. Створення світу:");
        Entity worldEntity = worldSystem.createWorld("overworld", new Vector3D(0, 64, 0));
        WorldComponent worldComponent = worldEntity.getComponent(WorldComponent.class);
        System.out.println("    Світ створено: " + worldComponent.getWorldName());
        
        // Створення сутності
        System.out.println("  3.3. Створення сутності:");
        Entity playerEntity = entitySystem.createEntity("player1", "player", new Vector3D(0, 65, 0));
        EntityComponent playerComponent = playerEntity.getComponent(EntityComponent.class);
        System.out.println("    Сутність створена: " + playerComponent.getMinecraftEntityId());
        
        // Оновлення систем
        System.out.println("  3.4. Оновлення систем:");
        worldSystem.update(entityManager.getEntitiesForSystem(worldSystem));
        entitySystem.update(entityManager.getEntitiesForSystem(entitySystem));
        System.out.println("    Системи оновлено");
        
        // Взаємодія з сутністю
        System.out.println("  3.5. Взаємодія з сутністю:");
        entitySystem.moveEntity("player1", new Vector3D(1, 0, 1));
        entitySystem.damageEntity("player1", 2.0);
        System.out.println("    Сутність переміщена та пошкоджена");
        
        // Перевірка стану сутності
        Entity updatedPlayerEntity = entitySystem.getEntityById("player1");
        EntityComponent updatedPlayerComponent = updatedPlayerEntity.getComponent(EntityComponent.class);
        System.out.println("    Нова позиція: " + updatedPlayerComponent.getPosition());
        System.out.println("    Нове здоров'я: " + updatedPlayerComponent.getHealth());
    }
    
    /**
     * Демонстрація розширених можливостей.
     */
    private static void demonstrateAdvancedFeatures() {
        System.out.println("4. Розширені можливості:");
        
        // Процедурна генерація світу
        System.out.println("  4.1. Процедурна генерація світу:");
        ProceduralWorldGenerator generator = new ProceduralWorldGenerator(12345);
        ChunkData chunk = generator.generateChunk(0, 0);
        System.out.println("    Згенеровано чанк (0,0) з біомом: " + chunk.getBiome());
        
        // Пошук шляхів
        System.out.println("  4.2. Пошук шляхів:");
        WorldGrid grid = new WorldGrid();
        AStarPathfinder pathfinder = new AStarPathfinder(grid);
        System.out.println("    Створено систему пошуку шляхів A*");
        
        // Фізика
        System.out.println("  4.3. Фізика:");
        PhysicsWorld physicsWorld = new PhysicsWorld();
        RigidBody body = new RigidBody(new Vector3D(0, 64, 0), 1.0);
        physicsWorld.addBody(body);
        System.out.println("    Створено фізичне тіло з масою: " + body.getMass());
        
        // Аудіо
        System.out.println("  4.4. Аудіо система:");
        AudioManager audioManager = new AudioManager();
        System.out.println("    Створено аудіо менеджер");
        
        // Рендеринг
        System.out.println("  4.5. Рендеринг:");
        RenderEngine renderEngine = new RenderEngine();
        renderEngine.initialize();
        System.out.println("    Ініціалізовано рендер двигун");
    }
}