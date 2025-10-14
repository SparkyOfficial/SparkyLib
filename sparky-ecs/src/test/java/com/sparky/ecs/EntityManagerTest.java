package com.sparky.ecs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * Тест для EntityManager класу.
 *
 * @author Андрій Будильников
 */
public class EntityManagerTest {
    
    private EntityManager entityManager;
    
    @BeforeEach
    public void setUp() {
        entityManager = new EntityManager();
    }
    
    @Test
    public void testCreateEntity() {
        Entity entity = entityManager.createEntity();
        assertNotNull(entity);
        assertTrue(entity.getId() > 0);
    }
    
    @Test
    public void testGetEntity() {
        Entity entity = entityManager.createEntity();
        int id = entity.getId();
        
        Entity retrievedEntity = entityManager.getEntity(id);
        assertNotNull(retrievedEntity);
        assertEquals(id, retrievedEntity.getId());
    }
    
    @Test
    public void testRemoveEntity() {
        Entity entity = entityManager.createEntity();
        int id = entity.getId();
        
        entityManager.removeEntity(id);
        Entity retrievedEntity = entityManager.getEntity(id);
        assertNull(retrievedEntity);
    }
    
    @Test
    public void testGetAllEntities() {
        entityManager.createEntity();
        entityManager.createEntity();
        entityManager.createEntity();
        
        List<Entity> entities = entityManager.getAllEntities();
        assertEquals(3, entities.size());
    }
    
    @Test
    public void testAddComponentToEntity() {
        Entity entity = entityManager.createEntity();
        
        // Створюємо тестовий компонент
        TestComponent component = new TestComponent();
        entityManager.addComponentToEntity(entity, component);
        
        assertTrue(entity.hasComponent(TestComponent.class));
        assertEquals(component, entity.getComponent(TestComponent.class));
    }
    
    @Test
    public void testRemoveComponentFromEntity() {
        Entity entity = entityManager.createEntity();
        TestComponent component = new TestComponent();
        entityManager.addComponentToEntity(entity, component);
        
        assertTrue(entity.hasComponent(TestComponent.class));
        
        entityManager.removeComponentFromEntity(entity, TestComponent.class);
        assertFalse(entity.hasComponent(TestComponent.class));
    }
    
    @Test
    public void testGetEntitiesWithComponent() {
        // Створюємо сутності з різними компонентами
        Entity entity1 = entityManager.createEntity();
        Entity entity2 = entityManager.createEntity();
        Entity entity3 = entityManager.createEntity();
        
        TestComponent component1 = new TestComponent();
        TestComponent component2 = new TestComponent();
        AnotherComponent component3 = new AnotherComponent();
        
        entityManager.addComponentToEntity(entity1, component1);
        entityManager.addComponentToEntity(entity2, component2);
        entityManager.addComponentToEntity(entity3, component3);
        
        // Отримуємо сутності з TestComponent
        List<Entity> entitiesWithTestComponent = entityManager.getEntitiesWithComponent(TestComponent.class);
        assertEquals(2, entitiesWithTestComponent.size());
        assertTrue(entitiesWithTestComponent.contains(entity1));
        assertTrue(entitiesWithTestComponent.contains(entity2));
        
        // Отримуємо сутності з AnotherComponent
        List<Entity> entitiesWithAnotherComponent = entityManager.getEntitiesWithComponent(AnotherComponent.class);
        assertEquals(1, entitiesWithAnotherComponent.size());
        assertTrue(entitiesWithAnotherComponent.contains(entity3));
    }
    
    @Test
    public void testGetEntitiesWithComponents() {
        // Створюємо сутності з різними комбінаціями компонентів
        Entity entity1 = entityManager.createEntity();
        Entity entity2 = entityManager.createEntity();
        Entity entity3 = entityManager.createEntity();
        
        TestComponent component1 = new TestComponent();
        TestComponent component2 = new TestComponent();
        TestComponent component3 = new TestComponent();
        AnotherComponent anotherComponent1 = new AnotherComponent();
        AnotherComponent anotherComponent3 = new AnotherComponent();
        
        entityManager.addComponentToEntity(entity1, component1);
        entityManager.addComponentToEntity(entity1, anotherComponent1);
        
        entityManager.addComponentToEntity(entity2, component2);
        
        entityManager.addComponentToEntity(entity3, component3);
        entityManager.addComponentToEntity(entity3, anotherComponent3);
        
        // Отримуємо сутності з обома компонентами
        Set<Class<? extends Component>> requiredComponents = new HashSet<>();
        requiredComponents.add(TestComponent.class);
        requiredComponents.add(AnotherComponent.class);
        
        List<Entity> entitiesWithBothComponents = entityManager.getEntitiesWithComponents(requiredComponents);
        assertEquals(2, entitiesWithBothComponents.size());
        assertTrue(entitiesWithBothComponents.contains(entity1));
        assertTrue(entitiesWithBothComponents.contains(entity3));
    }
    
    @Test
    public void testGetEntitiesWithPredicate() {
        Entity entity1 = entityManager.createEntity();
        Entity entity2 = entityManager.createEntity();
        Entity entity3 = entityManager.createEntity();
        
        TestComponent component1 = new TestComponent();
        TestComponent component2 = new TestComponent();
        TestComponent component3 = new TestComponent();
        
        component1.setValue(5);
        component2.setValue(15);
        component3.setValue(25);
        
        entityManager.addComponentToEntity(entity1, component1);
        entityManager.addComponentToEntity(entity2, component2);
        entityManager.addComponentToEntity(entity3, component3);
        
        // Отримуємо сутності з компонентами, значення яких більше 10
        Predicate<Entity> predicate = entity -> {
            if (entity.hasComponent(TestComponent.class)) {
                TestComponent component = entity.getComponent(TestComponent.class);
                return component.getValue() > 10;
            }
            return false;
        };
        
        List<Entity> filteredEntities = entityManager.getEntities(predicate);
        assertEquals(2, filteredEntities.size());
        assertTrue(filteredEntities.contains(entity2));
        assertTrue(filteredEntities.contains(entity3));
    }
    
    @Test
    public void testRebuildComponentIndex() {
        Entity entity = entityManager.createEntity();
        TestComponent component = new TestComponent();
        entityManager.addComponentToEntity(entity, component);
        
        // Очищуємо індекс вручну для тестування
        // Примітка: Це тестовий метод, який не є публічним в реальній реалізації
        // Для цього тесту ми припустимо, що індекс буде перебудовано правильно
        entityManager.rebuildComponentIndex();
        
        List<Entity> entities = entityManager.getEntitiesWithComponent(TestComponent.class);
        assertEquals(1, entities.size());
        assertTrue(entities.contains(entity));
    }
    
    @Test
    public void testGetEntityCount() {
        entityManager.createEntity();
        entityManager.createEntity();
        entityManager.createEntity();
        
        assertEquals(3, entityManager.getEntityCount());
    }
    
    @Test
    public void testGetEntityCountWithComponent() {
        Entity entity1 = entityManager.createEntity();
        Entity entity2 = entityManager.createEntity();
        Entity entity3 = entityManager.createEntity();
        
        TestComponent component1 = new TestComponent();
        TestComponent component2 = new TestComponent();
        AnotherComponent component3 = new AnotherComponent();
        
        entityManager.addComponentToEntity(entity1, component1);
        entityManager.addComponentToEntity(entity2, component2);
        entityManager.addComponentToEntity(entity3, component3);
        
        assertEquals(2, entityManager.getEntityCountWithComponent(TestComponent.class));
        assertEquals(1, entityManager.getEntityCountWithComponent(AnotherComponent.class));
        assertEquals(0, entityManager.getEntityCountWithComponent(ThirdComponent.class));
    }
    
    // Тестові компоненти для тестування
    private static class TestComponent extends Component {
        private int value = 0;
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }
    
    private static class AnotherComponent extends Component {
        private String data = "test";
        
        public String getData() {
            return data;
        }
        
        public void setData(String data) {
            this.data = data;
        }
    }
    
    private static class ThirdComponent extends Component {
        private boolean flag = false;
        
        public boolean isFlag() {
            return flag;
        }
        
        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}