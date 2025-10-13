package com.sparky.ecs;

import static java.lang.System.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Демонстраційний тест для модуля ECS.
 *
 * @author Богдан Кравчук
 */
class ECSDemoTest {
    
    @Test
    void testECSFunctionality() {
        // Create entity manager
        EntityManager entityManager = new EntityManager();
        
        // Create entities
        Entity entity1 = entityManager.createEntity();
        Entity entity2 = entityManager.createEntity();
        
        // Add components to entity1
        entity1.addComponent(new PositionComponent(10, 20, 30));
        entity1.addComponent(new VelocityComponent(5, 3, 1));
        
        // Add only position component to entity2
        entity2.addComponent(new PositionComponent(0, 0, 0));
        
        // Verify entities were created
        assertNotNull(entityManager.getEntity(entity1.getId()));
        assertNotNull(entityManager.getEntity(entity2.getId()));
        assertNull(entityManager.getEntity(999)); // Non-existent entity
        
        // Test component retrieval
        assertTrue(entity1.hasComponent(PositionComponent.class));
        assertTrue(entity1.hasComponent(VelocityComponent.class));
        assertTrue(entity2.hasComponent(PositionComponent.class));
        assertFalse(entity2.hasComponent(VelocityComponent.class));
        
        PositionComponent position1 = entity1.getComponent(PositionComponent.class);
        VelocityComponent velocity1 = entity1.getComponent(VelocityComponent.class);
        PositionComponent position2 = entity2.getComponent(PositionComponent.class);
        
        assertEquals(10.0f, position1.getX(), 0.001f);
        assertEquals(20.0f, position1.getY(), 0.001f);
        assertEquals(30.0f, position1.getZ(), 0.001f);
        
        assertEquals(5.0f, velocity1.getDx(), 0.001f);
        assertEquals(3.0f, velocity1.getDy(), 0.001f);
        assertEquals(1.0f, velocity1.getDz(), 0.001f);
        
        out.println("ECS basic functionality test passed!");
    }
    
    @Test
    void testMovementSystemIntegration() {
        // Create entity manager and movement system
        EntityManager entityManager = new EntityManager();
        MovementSystem movementSystem = new MovementSystem();
        movementSystem.setEntityManager(entityManager);
        
        // Set a specific delta time for predictable results
        movementSystem.setDeltaTime(5.0f); // 5 seconds
        
        // Create an entity with position and velocity components
        Entity entity = entityManager.createEntity();
        PositionComponent position = new PositionComponent(0, 0, 0);
        VelocityComponent velocity = new VelocityComponent(2, 3, 1); // 2 units/sec in X, 3 units/sec in Y, 1 unit/sec in Z
        
        entity.addComponent(position);
        entity.addComponent(velocity);
        
        // Get entities for the movement system (should filter correctly)
        java.util.List<Entity> entities = entityManager.getEntitiesForSystem(movementSystem);
        
        // Should have exactly one entity (the one with both Position and Velocity components)
        assertEquals(1, entities.size());
        assertEquals(entity, entities.get(0));
        
        // Update the movement system
        movementSystem.update(entities);
        
        // Check that position was updated correctly
        // New position should be: (0 + 2*5, 0 + 3*5, 0 + 1*5) = (10, 15, 5)
        assertEquals(10.0f, position.getX(), 0.001f);
        assertEquals(15.0f, position.getY(), 0.001f);
        assertEquals(5.0f, position.getZ(), 0.001f);
        
        out.println("ECS movement system integration test passed!");
    }
    
    @Test
    void testEntityRemoval() {
        // Create entity manager
        EntityManager entityManager = new EntityManager();
        
        // Create and verify entity
        Entity entity = entityManager.createEntity();
        entity.addComponent(new PositionComponent(0, 0, 0));
        
        assertNotNull(entityManager.getEntity(entity.getId()));
        
        // Remove entity
        entityManager.removeEntity(entity.getId());
        
        // Verify entity was removed
        assertNull(entityManager.getEntity(entity.getId()));
        
        // Try to remove non-existent entity (should not throw exception)
        entityManager.removeEntity(999);
        
        out.println("ECS entity removal test passed!");
    }
}