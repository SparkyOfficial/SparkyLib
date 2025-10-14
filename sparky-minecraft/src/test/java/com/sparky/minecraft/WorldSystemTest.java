package com.sparky.minecraft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.minecraft.math.Vector3D;

/**
 * Тест для WorldSystem класу.
 *
 * @author Андрій Будильников
 */
public class WorldSystemTest {
    
    @Test
    public void testWorldSystemCreation() {
        EntityManager entityManager = new EntityManager();
        WorldSystem worldSystem = new WorldSystem(entityManager);
        
        assertNotNull(worldSystem);
    }
    
    @Test
    public void testCreateWorld() {
        EntityManager entityManager = new EntityManager();
        WorldSystem worldSystem = new WorldSystem(entityManager);
        
        Vector3D spawnPoint = new Vector3D(0, 64, 0);
        Entity worldEntity = worldSystem.createWorld("test_world", spawnPoint);
        
        assertNotNull(worldEntity);
        assertTrue(worldEntity.hasComponent(WorldComponent.class));
        
        WorldComponent worldComponent = worldEntity.getComponent(WorldComponent.class);
        assertEquals("test_world", worldComponent.getWorldName());
        assertEquals(spawnPoint.getX(), worldComponent.getSpawnPoint().getX(), 0.001);
        assertEquals(spawnPoint.getY(), worldComponent.getSpawnPoint().getY(), 0.001);
        assertEquals(spawnPoint.getZ(), worldComponent.getSpawnPoint().getZ(), 0.001);
    }
    
    @Test
    public void testGetWorldByName() {
        EntityManager entityManager = new EntityManager();
        WorldSystem worldSystem = new WorldSystem(entityManager);
        
        Vector3D spawnPoint = new Vector3D(0, 64, 0);
        worldSystem.createWorld("test_world", spawnPoint);
        
        WorldComponent worldComponent = worldSystem.getWorldByName("test_world");
        assertNotNull(worldComponent);
        assertEquals("test_world", worldComponent.getWorldName());
        
        WorldComponent nonExistentWorld = worldSystem.getWorldByName("non_existent");
        assertNull(nonExistentWorld);
    }
    
    @Test
    public void testSetWeather() {
        EntityManager entityManager = new EntityManager();
        WorldSystem worldSystem = new WorldSystem(entityManager);
        
        Vector3D spawnPoint = new Vector3D(0, 64, 0);
        worldSystem.createWorld("test_world", spawnPoint);
        
        // Встановлюємо погоду
        worldSystem.setWeather("test_world", true, true);
        
        WorldComponent worldComponent = worldSystem.getWorldByName("test_world");
        assertNotNull(worldComponent);
        assertTrue(worldComponent.isRaining());
        assertTrue(worldComponent.isThundering());
    }
    
    @Test
    public void testSetWeatherStrength() {
        EntityManager entityManager = new EntityManager();
        WorldSystem worldSystem = new WorldSystem(entityManager);
        
        Vector3D spawnPoint = new Vector3D(0, 64, 0);
        worldSystem.createWorld("test_world", spawnPoint);
        
        // Встановлюємо силу погоди
        worldSystem.setRainStrength("test_world", 0.5);
        worldSystem.setThunderStrength("test_world", 0.8);
        
        WorldComponent worldComponent = worldSystem.getWorldByName("test_world");
        assertNotNull(worldComponent);
        assertEquals(0.5, worldComponent.getRainStrength(), 0.001);
        assertEquals(0.8, worldComponent.getThunderStrength(), 0.001);
    }
    
    @Test
    public void testWorldUpdate() {
        EntityManager entityManager = new EntityManager();
        WorldSystem worldSystem = new WorldSystem(entityManager);
        
        Vector3D spawnPoint = new Vector3D(0, 64, 0);
        Entity worldEntity = worldSystem.createWorld("test_world", spawnPoint);
        WorldComponent worldComponent = worldEntity.getComponent(WorldComponent.class);
        
        long initialTime = worldComponent.getTime();
        
        // Оновлюємо світ
        worldSystem.update(entityManager.getEntitiesForSystem(worldSystem));
        
        // Перевіряємо, що час оновився
        assertTrue(worldComponent.getTime() >= initialTime);
    }
}