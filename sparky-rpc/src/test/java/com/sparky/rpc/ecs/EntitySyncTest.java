package com.sparky.rpc.ecs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.System.out;

import com.sparky.ecs.EntityManager;
import com.sparky.ecs.Entity;

/**
 * Тест для синхронізації сутностей ECS.
 *
 * @author Андрій Будильников
 */
class EntitySyncTest {
    
    @Test
    void testEntitySyncMessageCreation() {
        // Тестуємо створення повідомлення для створення сутності
        EntitySyncMessage createMessage = EntitySyncMessage.createEntityMessage(123, "test-id-1");
        assertEquals(EntitySyncMessage.METHOD_CREATE_ENTITY, createMessage.getMethod());
        assertEquals(123, createMessage.getEntityId());
        assertEquals("test-id-1", createMessage.getId());
        
        // Тестуємо створення повідомлення для видалення сутності
        EntitySyncMessage removeMessage = EntitySyncMessage.removeEntityMessage(456, "test-id-2");
        assertEquals(EntitySyncMessage.METHOD_REMOVE_ENTITY, removeMessage.getMethod());
        assertEquals(456, removeMessage.getEntityId());
        assertEquals("test-id-2", removeMessage.getId());
        
        out.println("Entity sync message creation test passed!");
    }
    
    @Test
    void testEntitySyncManagerCreation() {
        EntityManager entityManager = new EntityManager();
        EntitySyncManager syncManager = new EntitySyncManager(entityManager);
        
        // Перевіряємо, що менеджер було створено
        assertNotNull(syncManager);
        
        out.println("Entity sync manager creation test passed!");
    }
    
    @Test
    void testEntitySyncManagerWithEntityManager() {
        EntityManager entityManager = new EntityManager();
        EntitySyncManager syncManager = new EntitySyncManager(entityManager);
        
        // Створюємо сутність через EntityManager
        Entity entity = entityManager.createEntity();
        int entityId = entity.getId();
        
        // Перевіряємо, що сутність була створена
        assertNotNull(entityManager.getEntity(entityId));
        
        // Спробуємо синхронізувати створення сутності
        // Оскільки немає підключеного RPC клієнта, нічого не має статися
        syncManager.syncEntityCreation(entity);
        
        // Видаляємо сутність
        entityManager.removeEntity(entityId);
        
        // Перевіряємо, що сутність була видалена
        assertNull(entityManager.getEntity(entityId));
        
        out.println("Entity sync manager with entity manager test passed!");
    }
}