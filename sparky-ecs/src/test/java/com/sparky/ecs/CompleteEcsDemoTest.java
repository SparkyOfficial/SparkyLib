package com.sparky.ecs;

import org.junit.jupiter.api.Test;
import static java.lang.System.out;

/**
 * Комплексний демонстраційний тест для всіх нових функцій ECS.
 *
 * @author Андрій Будильников
 */
class CompleteEcsDemoTest {
    
    @Test
    void testCompleteEcsDemo() {
        // Створюємо менеджер сутностей
        EntityManager entityManager = new EntityManager();
        
        // Створюємо всі системи
        MovementSystem movementSystem = new MovementSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        CollisionSystem collisionSystem = new CollisionSystem();
        RenderingSystem renderingSystem = new RenderingSystem();
        
        movementSystem.setEntityManager(entityManager);
        physicsSystem.setEntityManager(entityManager);
        collisionSystem.setEntityManager(entityManager);
        renderingSystem.setEntityManager(entityManager);
        
        // Налаштовуємо системи
        float deltaTime = 1.0f / 60.0f;
        physicsSystem.setDeltaTime(deltaTime);
        movementSystem.setDeltaTime(deltaTime);
        
        // Створюємо камеру
        Entity cameraEntity = entityManager.createEntity();
        CameraComponent camera = new CameraComponent(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 800.0f, 600.0f, true);
        cameraEntity.addComponent(camera);
        
        // Створюємо гравця
        Entity playerEntity = entityManager.createEntity();
        PositionComponent playerPosition = new PositionComponent(0.0f, 5.0f, 0.0f);
        VelocityComponent playerVelocity = new VelocityComponent(2.0f, 0.0f, 0.0f);
        PhysicsComponent playerPhysics = new PhysicsComponent(1.0f, 1.0f, 0.1f, 0.0f, true);
        CollisionComponent playerCollision = new CollisionComponent(CollisionComponent.Shape.CIRCLE, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        SpriteComponent playerSprite = new SpriteComponent("player.png", 2.0f, 2.0f, 0.0f, 1.0f, 1, 1.0f);
        
        playerEntity.addComponent(playerPosition);
        playerEntity.addComponent(playerVelocity);
        playerEntity.addComponent(playerPhysics);
        playerEntity.addComponent(playerCollision);
        playerEntity.addComponent(playerSprite);
        
        // Створюємо платформу
        Entity platformEntity = entityManager.createEntity();
        PositionComponent platformPosition = new PositionComponent(0.0f, 0.0f, 0.0f);
        CollisionComponent platformCollision = new CollisionComponent(CollisionComponent.Shape.RECTANGLE, 10.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        SpriteComponent platformSprite = new SpriteComponent("platform.png", 10.0f, 1.0f, 0.0f, 1.0f, 0, 1.0f);
        
        platformEntity.addComponent(platformPosition);
        platformEntity.addComponent(platformCollision);
        platformEntity.addComponent(platformSprite);
        
        // Симулюємо кілька кадрів гри
        out.println("Starting ECS demo simulation...");
        
        for (int frame = 0; frame < 120; frame++) { // 2 секунди при 60 FPS
            // Отримуємо сутності для кожної системи
            java.util.List<Entity> physicsEntities = entityManager.getEntitiesForSystem(physicsSystem);
            java.util.List<Entity> movementEntities = entityManager.getEntitiesForSystem(movementSystem);
            java.util.List<Entity> collisionEntities = entityManager.getEntitiesForSystem(collisionSystem);
            java.util.List<Entity> renderingEntities = renderingSystem.getEntitiesForRendering(entityManager);
            
            // Оновлюємо системи в правильному порядку
            physicsSystem.update(physicsEntities);
            movementSystem.update(movementEntities);
            collisionSystem.update(collisionEntities);
            renderingSystem.update(renderingEntities);
            
            // Виводимо інформацію кожні 30 кадрів
            if (frame % 30 == 0) {
                out.println("Frame " + frame + ": Player position: (" + 
                           playerPosition.getX() + ", " + playerPosition.getY() + ", " + playerPosition.getZ() + ")");
            }
            
            // Симулюємо стрибок на 60-му кадрі
            if (frame == 60) {
                playerPhysics.applyForce(0.0f, 50.0f, 0.0f); // Вгору
                out.println("Player jumped!");
            }
        }
        
        // Виводимо фінальну позицію
        out.println("Final player position: (" + playerPosition.getX() + ", " + playerPosition.getY() + ", " + playerPosition.getZ() + ")");
        out.println("Complete ECS demo finished successfully!");
    }
}