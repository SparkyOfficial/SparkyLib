package com.sparky.ecs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.System.out;

/**
 * Тест для системи фізики.
 *
 * @author Андрій Будильников
 */
class PhysicsSystemTest {
    
    @Test
    void testPhysicsSystemWithGravity() {
        // Створюємо менеджер сутностей та систему фізики
        EntityManager entityManager = new EntityManager();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        physicsSystem.setEntityManager(entityManager);
        physicsSystem.setDeltaTime(1.0f); // 1 секунда для простоти тестування
        
        // Створюємо сутність з фізичними властивостями
        Entity entity = entityManager.createEntity();
        PositionComponent position = new PositionComponent(0, 0, 0);
        VelocityComponent velocity = new VelocityComponent(0, 0, 0);
        PhysicsComponent physics = new PhysicsComponent(1.0f, 1.0f, 0.0f, 0.0f, true); // Маса 1, гравітація 1, без тертя
        
        entity.addComponent(position);
        entity.addComponent(velocity);
        entity.addComponent(physics);
        
        // Отримуємо сутності для системи фізики
        java.util.List<Entity> entities = entityManager.getEntitiesForSystem(physicsSystem);
        
        // Оновлюємо систему фізики
        physicsSystem.update(entities);
        
        // Перевіряємо, що швидкість змінилася через гравітацію
        // При гравітації 9.81 м/с² і delta time 1.0, швидкість має змінитися на -9.81
        assertEquals(0.0f, velocity.getDx(), 0.001f);
        assertEquals(-9.81f, velocity.getDy(), 0.001f);
        assertEquals(0.0f, velocity.getDz(), 0.001f);
        
        out.println("Physics system gravity test passed!");
    }
    
    @Test
    void testPhysicsSystemWithFriction() {
        // Створюємо менеджер сутностей та систему фізики
        EntityManager entityManager = new EntityManager();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        physicsSystem.setEntityManager(entityManager);
        physicsSystem.setDeltaTime(1.0f);
        
        // Створюємо сутність з початковою швидкістю та тертям
        Entity entity = entityManager.createEntity();
        PositionComponent position = new PositionComponent(0, 0, 0);
        VelocityComponent velocity = new VelocityComponent(10, 5, 2); // Початкова швидкість
        PhysicsComponent physics = new PhysicsComponent(1.0f, 0.0f, 0.5f, 0.0f, false); // Без гравітації, тертя 0.5
        
        entity.addComponent(position);
        entity.addComponent(velocity);
        entity.addComponent(physics);
        
        // Отримуємо сутності для системи фізики
        java.util.List<Entity> entities = entityManager.getEntitiesForSystem(physicsSystem);
        
        // Оновлюємо систему фізики
        physicsSystem.update(entities);
        
        // Перевіряємо, що швидкість зменшилася через тертя
        // При терті 0.5 і delta time 1.0, швидкість має зменшитися вдвічі
        assertEquals(5.0f, velocity.getDx(), 0.001f);
        assertEquals(2.5f, velocity.getDy(), 0.001f);
        assertEquals(1.0f, velocity.getDz(), 0.001f);
        
        out.println("Physics system friction test passed!");
    }
    
    @Test
    void testRequiredComponents() {
        PhysicsSystem physicsSystem = new PhysicsSystem();
        java.util.Set<Class<? extends Component>> requiredComponents = physicsSystem.getRequiredComponents();
        
        assertTrue(requiredComponents.contains(PhysicsComponent.class));
        assertTrue(requiredComponents.contains(VelocityComponent.class));
        assertEquals(2, requiredComponents.size());
        
        out.println("Physics system required components test passed!");
    }
}