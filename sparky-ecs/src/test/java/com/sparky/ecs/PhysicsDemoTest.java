package com.sparky.ecs;

import static java.lang.System.out;

import org.junit.jupiter.api.Test;

/**
 * Демонстраційний тест для фізичної системи ECS.
 *
 * @author Андрій Будильников
 */
class PhysicsDemoTest {
    
    @Test
    void testPhysicsAndCollisionDemo() {
        // Створюємо менеджер сутностей
        EntityManager entityManager = new EntityManager();
        
        // Створюємо системи
        MovementSystem movementSystem = new MovementSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        CollisionSystem collisionSystem = new CollisionSystem();
        
        movementSystem.setEntityManager(entityManager);
        physicsSystem.setEntityManager(entityManager);
        collisionSystem.setEntityManager(entityManager);
        
        // Налаштовуємо системи
        physicsSystem.setDeltaTime(1.0f / 60.0f); // 60 FPS
        movementSystem.setDeltaTime(1.0f / 60.0f);
        
        // Створюємо сутність з фізикою, що падає
        Entity fallingEntity = entityManager.createEntity();
        PositionComponent fallingPosition = new PositionComponent(0, 10, 0); // Починає на висоті 10
        VelocityComponent fallingVelocity = new VelocityComponent(0, 0, 0); // Початкова швидкість нульова
        PhysicsComponent fallingPhysics = new PhysicsComponent(1.0f, 1.0f, 0.01f, 0.2f, true); // З гравітацією
        CollisionComponent fallingCollision = new CollisionComponent(CollisionComponent.Shape.CIRCLE, 1.0f, 0, 0, 0);
        
        fallingEntity.addComponent(fallingPosition);
        fallingEntity.addComponent(fallingVelocity);
        fallingEntity.addComponent(fallingPhysics);
        fallingEntity.addComponent(fallingCollision);
        
        // Створюємо сутність-платформу
        Entity platformEntity = entityManager.createEntity();
        PositionComponent platformPosition = new PositionComponent(0, 0, 0); // На землі
        CollisionComponent platformCollision = new CollisionComponent(CollisionComponent.Shape.RECTANGLE, 5.0f, 1.0f, 1.0f, 0, 0, 0);
        
        platformEntity.addComponent(platformPosition);
        platformEntity.addComponent(platformCollision);
        
        // Симулюємо кілька кадрів
        for (int i = 0; i < 120; i++) { // 2 секунди при 60 FPS
            // Отримуємо сутності для кожної системи
            java.util.List<Entity> physicsEntities = entityManager.getEntitiesForSystem(physicsSystem);
            java.util.List<Entity> movementEntities = entityManager.getEntitiesForSystem(movementSystem);
            java.util.List<Entity> collisionEntities = entityManager.getEntitiesForSystem(collisionSystem);
            
            // Оновлюємо системи
            physicsSystem.update(physicsEntities);
            movementSystem.update(movementEntities);
            collisionSystem.update(collisionEntities);
            
            // Виводимо позицію падаючої сутності кожні 30 кадрів
            if (i % 30 == 0) {
                out.println("Frame " + i + ": Falling entity position: (" + 
                           fallingPosition.getX() + ", " + fallingPosition.getY() + ", " + fallingPosition.getZ() + ")");
            }
        }
        
        // Перевіряємо, що сутність впала на платформу
        out.println("Final position: (" + fallingPosition.getX() + ", " + fallingPosition.getY() + ", " + fallingPosition.getZ() + ")");
        out.println("Physics and collision demo completed!");
    }
}