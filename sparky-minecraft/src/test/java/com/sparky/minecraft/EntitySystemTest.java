package com.sparky.minecraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.minecraft.math.Vector3D;

/**
 * Тест для EntitySystem класу.
 *
 * @author Андрій Будильников
 */
public class EntitySystemTest {
    
    @Test
    public void testEntitySystemCreation() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        assertNotNull(entitySystem);
    }
    
    @Test
    public void testCreateEntity() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        Vector3D position = new Vector3D(0, 64, 0);
        Entity entity = entitySystem.createEntity("test_entity", "player", position);
        
        assertNotNull(entity);
        assertTrue(entity.hasComponent(EntityComponent.class));
        
        EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
        assertEquals("test_entity", entityComponent.getMinecraftEntityId());
        assertEquals("player", entityComponent.getEntityType());
        assertEquals(position.getX(), entityComponent.getPosition().getX(), 0.001);
        assertEquals(position.getY(), entityComponent.getPosition().getY(), 0.001);
        assertEquals(position.getZ(), entityComponent.getPosition().getZ(), 0.001);
    }
    
    @Test
    public void testGetEntityById() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        Vector3D position = new Vector3D(0, 64, 0);
        entitySystem.createEntity("test_entity", "player", position);
        
        Entity entity = entitySystem.getEntityById("test_entity");
        assertNotNull(entity);
        assertTrue(entity.hasComponent(EntityComponent.class));
        
        EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
        assertEquals("test_entity", entityComponent.getMinecraftEntityId());
        
        Entity nonExistentEntity = entitySystem.getEntityById("non_existent");
        assertNull(nonExistentEntity);
    }
    
    @Test
    public void testMoveEntity() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        Vector3D position = new Vector3D(0, 64, 0);
        entitySystem.createEntity("test_entity", "player", position);
        
        Vector3D offset = new Vector3D(5, 0, 3);
        entitySystem.moveEntity("test_entity", offset);
        
        Entity entity = entitySystem.getEntityById("test_entity");
        EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
        assertEquals(5, entityComponent.getPosition().getX(), 0.001);
        assertEquals(64, entityComponent.getPosition().getY(), 0.001);
        assertEquals(3, entityComponent.getPosition().getZ(), 0.001);
    }
    
    @Test
    public void testTeleportEntity() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        Vector3D position = new Vector3D(0, 64, 0);
        entitySystem.createEntity("test_entity", "player", position);
        
        Vector3D newPosition = new Vector3D(100, 70, -50);
        entitySystem.teleportEntity("test_entity", newPosition);
        
        Entity entity = entitySystem.getEntityById("test_entity");
        EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
        assertEquals(100, entityComponent.getPosition().getX(), 0.001);
        assertEquals(70, entityComponent.getPosition().getY(), 0.001);
        assertEquals(-50, entityComponent.getPosition().getZ(), 0.001);
    }
    
    @Test
    public void testDamageEntity() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        Vector3D position = new Vector3D(0, 64, 0);
        entitySystem.createEntity("test_entity", "player", position);
        
        // Наносимо шкоду
        entitySystem.damageEntity("test_entity", 5.0);
        
        Entity entity = entitySystem.getEntityById("test_entity");
        EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
        assertEquals(15.0, entityComponent.getHealth(), 0.001); // 20 - 5 = 15
        assertTrue(entityComponent.isAlive());
        
        // Наносимо смертельну шкоду
        entitySystem.damageEntity("test_entity", 20.0);
        
        assertEquals(0.0, entityComponent.getHealth(), 0.001);
        assertFalse(entityComponent.isAlive());
    }
    
    @Test
    public void testHealEntity() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        Vector3D position = new Vector3D(0, 64, 0);
        entitySystem.createEntity("test_entity", "player", position);
        
        // Наносимо шкоду
        entitySystem.damageEntity("test_entity", 15.0);
        
        Entity entity = entitySystem.getEntityById("test_entity");
        EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
        assertEquals(5.0, entityComponent.getHealth(), 0.001);
        
        // Лікуємо
        entitySystem.healEntity("test_entity", 10.0);
        
        assertEquals(15.0, entityComponent.getHealth(), 0.001);
        
        // Перевіряємо, що здоров'я не перевищує максимальне
        entitySystem.healEntity("test_entity", 10.0);
        
        assertEquals(20.0, entityComponent.getHealth(), 0.001); // Не може бути більше 20
    }
    
    @Test
    public void testSetEntityName() {
        EntityManager entityManager = new EntityManager();
        EntitySystem entitySystem = new EntitySystem(entityManager);
        
        Vector3D position = new Vector3D(0, 64, 0);
        entitySystem.createEntity("test_entity", "player", position);
        
        entitySystem.setEntityName("test_entity", "Test Player", true);
        
        Entity entity = entitySystem.getEntityById("test_entity");
        EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
        assertEquals("Test Player", entityComponent.getCustomName());
        assertTrue(entityComponent.isCustomNameVisible());
    }
}