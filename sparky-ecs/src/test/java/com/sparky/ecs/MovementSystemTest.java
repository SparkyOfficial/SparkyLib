package com.sparky.ecs;

import static java.lang.System.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Тест для системи руху.
 *
 * @author Богдан Кравчук
 */
class MovementSystemTest {
    
    @Test
    void testMovementWithDeltaTime() {
        // Create entity manager and movement system
        EntityManager entityManager = new EntityManager();
        MovementSystem movementSystem = new MovementSystem();
        movementSystem.setEntityManager(entityManager);
        
        // Create an entity with position and velocity components
        Entity entity = entityManager.createEntity();
        PositionComponent position = new PositionComponent(0, 0, 0);
        VelocityComponent velocity = new VelocityComponent(10, 5, 2); // 10 units/sec in X, 5 units/sec in Y, 2 units/sec in Z
        
        entity.addComponent(position);
        entity.addComponent(velocity);
        
        // Set delta time to 1 second
        movementSystem.setDeltaTime(1.0f);
        
        // Get entities for the movement system
        java.util.List<Entity> entities = entityManager.getEntitiesForSystem(movementSystem);
        
        // Update the movement system
        movementSystem.update(entities);
        
        // Check that position was updated correctly
        assertEquals(10.0f, position.getX(), 0.001f);
        assertEquals(5.0f, position.getY(), 0.001f);
        assertEquals(2.0f, position.getZ(), 0.001f);
        
        out.println("Movement with delta time test passed!");
    }
    
    @Test
    void testMovementWithSmallDeltaTime() {
        // Create entity manager and movement system
        EntityManager entityManager = new EntityManager();
        MovementSystem movementSystem = new MovementSystem();
        movementSystem.setEntityManager(entityManager);
        
        // Create an entity with position and velocity components
        Entity entity = entityManager.createEntity();
        PositionComponent position = new PositionComponent(0, 0, 0);
        VelocityComponent velocity = new VelocityComponent(10, 5, 2); // 10 units/sec in X, 5 units/sec in Y, 2 units/sec in Z
        
        entity.addComponent(position);
        entity.addComponent(velocity);
        
        // Set delta time to 1/60 second (approximately 60 FPS)
        movementSystem.setDeltaTime(1.0f / 60.0f);
        
        // Get entities for the movement system
        java.util.List<Entity> entities = entityManager.getEntitiesForSystem(movementSystem);
        
        // Update the movement system multiple times to simulate 1 second of movement
        for (int i = 0; i < 60; i++) {
            movementSystem.update(entities);
        }
        
        // Check that position was updated correctly (should be close to 10, 5, 2)
        assertEquals(10.0f, position.getX(), 0.01f);
        assertEquals(5.0f, position.getY(), 0.01f);
        assertEquals(2.0f, position.getZ(), 0.01f);
        
        out.println("Movement with small delta time test passed!");
    }
    
    @Test
    void testRequiredComponents() {
        MovementSystem movementSystem = new MovementSystem();
        java.util.Set<Class<? extends Component>> requiredComponents = movementSystem.getRequiredComponents();
        
        assertTrue(requiredComponents.contains(PositionComponent.class));
        assertTrue(requiredComponents.contains(VelocityComponent.class));
        assertEquals(2, requiredComponents.size());
        
        out.println("Required components test passed!");
    }
}