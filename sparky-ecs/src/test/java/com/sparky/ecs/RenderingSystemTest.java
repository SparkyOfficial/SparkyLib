package com.sparky.ecs;

import static java.lang.System.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Тест для системи візуалізації.
 *
 * @author Андрій Будильников
 */
class RenderingSystemTest {
    
    @Test
    void testRenderingSystemCreation() {
        RenderingSystem renderingSystem = new RenderingSystem();
        
        // Перевіряємо, що система була створена
        assertNotNull(renderingSystem);
        
        // Перевіряємо, що не вимагає конкретних компонентів
        assertTrue(renderingSystem.getRequiredComponents().isEmpty());
        
        out.println("Rendering system creation test passed!");
    }
    
    @Test
    void testCameraWorldToScreenConversion() {
        CameraComponent camera = new CameraComponent(10.0f, 5.0f, 0.0f, 2.0f, 0.0f, 800.0f, 600.0f, true);
        
        // Тестуємо перетворення світових координат в екранні
        float[] screenCoords = camera.worldToScreen(15.0f, 10.0f);
        
        // Розрахунок:
        // relativeX = 15.0 - 10.0 = 5.0
        // relativeY = 10.0 - 5.0 = 5.0
        // rotatedX = 5.0 (немає обертання)
        // rotatedY = 5.0 (немає обертання)
        // scaledX = 5.0 * 2.0 = 10.0
        // scaledY = 5.0 * 2.0 = 10.0
        // screenX = 10.0 + 800/2 = 410.0
        // screenY = 10.0 + 600/2 = 310.0
        
        assertEquals(410.0f, screenCoords[0], 0.001f);
        assertEquals(310.0f, screenCoords[1], 0.001f);
        
        out.println("Camera world to screen conversion test passed!");
    }
    
    @Test
    void testCameraScreenToWorldConversion() {
        CameraComponent camera = new CameraComponent(10.0f, 5.0f, 0.0f, 2.0f, 0.0f, 800.0f, 600.0f, true);
        
        // Тестуємо перетворення екранних координат в світові
        float[] worldCoords = camera.screenToWorld(410.0f, 310.0f);
        
        // Розрахунок:
        // centeredX = 410.0 - 800/2 = 10.0
        // centeredY = 310.0 - 600/2 = 10.0
        // scaledX = 10.0 / 2.0 = 5.0
        // scaledY = 10.0 / 2.0 = 5.0
        // rotatedX = 5.0 (немає обертання)
        // rotatedY = 5.0 (немає обертання)
        // worldX = 5.0 + 10.0 = 15.0
        // worldY = 5.0 + 5.0 = 10.0
        
        assertEquals(15.0f, worldCoords[0], 0.001f);
        assertEquals(10.0f, worldCoords[1], 0.001f);
        
        out.println("Camera screen to world conversion test passed!");
    }
    
    @Test
    void testRenderingSystemWithEntities() {
        // Створюємо менеджер сутностей та систему візуалізації
        EntityManager entityManager = new EntityManager();
        RenderingSystem renderingSystem = new RenderingSystem();
        renderingSystem.setEntityManager(entityManager);
        
        // Створюємо камеру
        Entity cameraEntity = entityManager.createEntity();
        CameraComponent camera = new CameraComponent(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 800.0f, 600.0f, true);
        cameraEntity.addComponent(camera);
        
        // Створюємо сутність зі спрайтом
        Entity spriteEntity = entityManager.createEntity();
        PositionComponent position = new PositionComponent(10.0f, 20.0f, 0.0f);
        SpriteComponent sprite = new SpriteComponent("player.png", 32.0f, 32.0f);
        spriteEntity.addComponent(position);
        spriteEntity.addComponent(sprite);
        
        // Отримуємо сутності для візуалізації
        java.util.List<Entity> entities = renderingSystem.getEntitiesForRendering(entityManager);
        
        // Перевіряємо, що обидві сутності в списку
        assertEquals(2, entities.size());
        
        // Оновлюємо систему візуалізації
        renderingSystem.update(entities);
        
        out.println("Rendering system with entities test passed!");
    }
}