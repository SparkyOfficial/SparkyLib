package com.sparky.ecs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Розширений тест для EntityManager класу з акцентом на просунуті можливості фільтрації.
 *
 * @author Богдан Кравчук
 */
public class AdvancedEntityManagerTest {
    
    private EntityManager entityManager;
    
    @BeforeEach
    public void setUp() {
        entityManager = new EntityManager();
    }
    
    @Test
    public void testGetEntitiesForSystemWithDirectComponentAddition() {
        // Створюємо систему, яка вимагає певні компоненти
        TestSystem testSystem = new TestSystem();
        testSystem.setEntityManager(entityManager);
        
        // Створюємо сутності та додаємо компоненти напряму (не через EntityManager)
        Entity entity1 = entityManager.createEntity();
        entity1.addComponent(new PositionComponent(0, 0, 0));
        entity1.addComponent(new VelocityComponent(1, 1, 1));
        
        Entity entity2 = entityManager.createEntity();
        entity2.addComponent(new PositionComponent(5, 5, 5));
        // Ця сутність не має VelocityComponent, тому не повинна бути вибрана
        
        Entity entity3 = entityManager.createEntity();
        entity3.addComponent(new PositionComponent(10, 10, 10));
        entity3.addComponent(new VelocityComponent(2, 2, 2));
        
        // Отримуємо сутності для системи
        List<Entity> entities = entityManager.getEntitiesForSystem(testSystem);
        
        // Перевіряємо, що отримали правильні сутності
        assertEquals(2, entities.size());
        assertTrue(entities.contains(entity1));
        assertTrue(entities.contains(entity3));
        assertFalse(entities.contains(entity2));
    }
    
    @Test
    public void testComponentIndexRebuild() {
        // Створюємо сутність та додаємо компонент напряму
        Entity entity = entityManager.createEntity();
        entity.addComponent(new PositionComponent(0, 0, 0));
        
        // Перевіряємо, що індекс оновлюється автоматично
        List<Entity> entitiesWithPosition = entityManager.getEntitiesWithComponent(PositionComponent.class);
        assertEquals(1, entitiesWithPosition.size());
        assertTrue(entitiesWithPosition.contains(entity));
        
        // Додаємо ще одну сутність
        Entity entity2 = entityManager.createEntity();
        entity2.addComponent(new PositionComponent(5, 5, 5));
        
        // Перевіряємо, що індекс оновився
        entitiesWithPosition = entityManager.getEntitiesWithComponent(PositionComponent.class);
        assertEquals(2, entitiesWithPosition.size());
        assertTrue(entitiesWithPosition.contains(entity));
        assertTrue(entitiesWithPosition.contains(entity2));
    }
    
    @Test
    public void testMixedComponentAdditionMethods() {
        // Створюємо сутність та додаємо компонент через EntityManager
        Entity entity1 = entityManager.createEntity();
        entityManager.addComponentToEntity(entity1, new PositionComponent(0, 0, 0));
        entityManager.addComponentToEntity(entity1, new VelocityComponent(1, 1, 1));
        
        // Створюємо іншу сутність та додаємо компонент напряму
        Entity entity2 = entityManager.createEntity();
        entity2.addComponent(new PositionComponent(5, 5, 5));
        entity2.addComponent(new VelocityComponent(2, 2, 2));
        
        // Перевіряємо, що обидві сутності доступні через фільтрацію
        Set<Class<? extends Component>> requiredComponents = new HashSet<>();
        requiredComponents.add(PositionComponent.class);
        requiredComponents.add(VelocityComponent.class);
        
        List<Entity> entities = entityManager.getEntitiesWithComponents(requiredComponents);
        assertEquals(2, entities.size());
        assertTrue(entities.contains(entity1));
        assertTrue(entities.contains(entity2));
    }
    
    @Test
    public void testEntityCountWithComponent() {
        // Створюємо сутності з різними компонентами
        Entity entity1 = entityManager.createEntity();
        entity1.addComponent(new PositionComponent(0, 0, 0));
        
        Entity entity2 = entityManager.createEntity();
        entity2.addComponent(new PositionComponent(5, 5, 5));
        entity2.addComponent(new VelocityComponent(1, 1, 1));
        
        Entity entity3 = entityManager.createEntity();
        entity3.addComponent(new VelocityComponent(2, 2, 2));
        
        // Перевіряємо кількість сутностей з певним компонентом
        assertEquals(2, entityManager.getEntityCountWithComponent(PositionComponent.class));
        assertEquals(2, entityManager.getEntityCountWithComponent(VelocityComponent.class));
    }
    
    @Test
    public void testPredicateBasedFiltering() {
        // Створюємо сутності з компонентами
        Entity entity1 = entityManager.createEntity();
        PositionComponent pos1 = new PositionComponent(0, 0, 0);
        entity1.addComponent(pos1);
        
        Entity entity2 = entityManager.createEntity();
        PositionComponent pos2 = new PositionComponent(10, 10, 10);
        entity2.addComponent(pos2);
        
        Entity entity3 = entityManager.createEntity();
        PositionComponent pos3 = new PositionComponent(5, 5, 5);
        entity3.addComponent(pos3);
        
        // Фільтруємо сутності за предикатом (позиція X > 3)
        Predicate<Entity> predicate = entity -> {
            if (entity.hasComponent(PositionComponent.class)) {
                PositionComponent pos = entity.getComponent(PositionComponent.class);
                return pos.getX() > 3;
            }
            return false;
        };
        
        List<Entity> filteredEntities = entityManager.getEntities(predicate);
        assertEquals(2, filteredEntities.size());
        assertTrue(filteredEntities.contains(entity2));
        assertTrue(filteredEntities.contains(entity3));
        assertFalse(filteredEntities.contains(entity1));
    }
    
    // Тестова система для тестування
    private static class TestSystem extends System {
        @Override
        public void update(List<Entity> entities) {
            // Порожня реалізація для тестування
        }
        
        @Override
        public Set<Class<? extends Component>> getRequiredComponents() {
            Set<Class<? extends Component>> required = new HashSet<>();
            required.add(PositionComponent.class);
            required.add(VelocityComponent.class);
            return required;
        }
    }
}