package com.sparky.ecs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.System.out;

/**
 * Тест для системи колізій.
 *
 * @author Андрій Будильников
 */
class CollisionSystemTest {
    
    @Test
    void testCollisionDetection() {
        // Створюємо менеджер сутностей та систему колізій
        EntityManager entityManager = new EntityManager();
        CollisionSystem collisionSystem = new CollisionSystem();
        collisionSystem.setEntityManager(entityManager);
        
        // Створюємо дві сутності, які мають перетинатися
        Entity entity1 = entityManager.createEntity();
        PositionComponent position1 = new PositionComponent(0, 0, 0);
        CollisionComponent collision1 = new CollisionComponent(CollisionComponent.Shape.RECTANGLE, 2.0f, 2.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        
        entity1.addComponent(position1);
        entity1.addComponent(collision1);
        
        Entity entity2 = entityManager.createEntity();
        PositionComponent position2 = new PositionComponent(1, 1, 0); // Перекривається з першою
        CollisionComponent collision2 = new CollisionComponent(CollisionComponent.Shape.RECTANGLE, 2.0f, 2.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        
        entity2.addComponent(position2);
        entity2.addComponent(collision2);
        
        // Отримуємо сутності для системи колізій
        java.util.List<Entity> entities = entityManager.getEntitiesForSystem(collisionSystem);
        
        // Перевіряємо, що обидві сутності в списку
        assertEquals(2, entities.size());
        
        // Тестуємо метод перетину безпосередньо
        assertTrue(collision1.intersects(collision2, position1, position2));
        
        out.println("Collision detection test passed!");
    }
    
    @Test
    void testNoCollision() {
        // Створюємо менеджер сутностей та систему колізій
        EntityManager entityManager = new EntityManager();
        CollisionSystem collisionSystem = new CollisionSystem();
        collisionSystem.setEntityManager(entityManager);
        
        // Створюємо дві сутності, які не перетинаються
        Entity entity1 = entityManager.createEntity();
        PositionComponent position1 = new PositionComponent(0, 0, 0);
        CollisionComponent collision1 = new CollisionComponent(CollisionComponent.Shape.RECTANGLE, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        
        entity1.addComponent(position1);
        entity1.addComponent(collision1);
        
        Entity entity2 = entityManager.createEntity();
        PositionComponent position2 = new PositionComponent(5, 5, 0); // Далеко від першої
        CollisionComponent collision2 = new CollisionComponent(CollisionComponent.Shape.RECTANGLE, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        
        entity2.addComponent(position2);
        entity2.addComponent(collision2);
        
        // Тестуємо метод перетину безпосередньо
        assertFalse(collision1.intersects(collision2, position1, position2));
        
        out.println("No collision test passed!");
    }
    
    @Test
    void testRequiredComponents() {
        CollisionSystem collisionSystem = new CollisionSystem();
        java.util.Set<Class<? extends Component>> requiredComponents = collisionSystem.getRequiredComponents();
        
        assertTrue(requiredComponents.contains(CollisionComponent.class));
        assertTrue(requiredComponents.contains(PositionComponent.class));
        assertEquals(2, requiredComponents.size());
        
        out.println("Collision system required components test passed!");
    }
}