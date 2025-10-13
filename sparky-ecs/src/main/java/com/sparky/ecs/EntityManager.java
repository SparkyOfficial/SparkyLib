package com.sparky.ecs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.sparky.core.SparkyLogger;

/**
 * Менеджер сутностей ECS.
 * <p>
 * Відповідає за створення, зберігання та видалення сутностей.
 * Також відповідає за отримання сутностей для систем.
 * <p>
 * Це спрощена реалізація - в повній версії можна використовувати анотації.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class EntityManager {
    private static final SparkyLogger logger = SparkyLogger.getLogger(EntityManager.class);
    
    private final Map<Integer, Entity> entities = new ConcurrentHashMap<>();
    private int nextEntityId = 1;
    
    /**
     * Створює нову сутність.
     *
     * @return нова сутність
     */
    public Entity createEntity() {
        int id = nextEntityId++;
        Entity entity = new Entity(id);
        entities.put(id, entity);
        logger.debug("Created entity with ID: " + id);
        return entity;
    }
    
    /**
     * Видаляє сутність за ідентифікатором.
     *
     * @param id ідентифікатор сутності
     */
    public void removeEntity(int id) {
        Entity entity = entities.remove(id);
        if (entity != null) {
            logger.debug("Removed entity with ID: " + id);
        } else {
            logger.warn("Attempted to remove non-existent entity with ID: " + id);
        }
    }
    
    /**
     * Отримує сутність за ідентифікатором.
     *
     * @param id ідентифікатор сутності
     * @return сутність або null, якщо не знайдено
     */
    public Entity getEntity(int id) {
        return entities.get(id);
    }
    
    /**
     * Отримує всі сутності.
     *
     * @return список всіх сутностей
     */
    public List<Entity> getAllEntities() {
        return new ArrayList<>(entities.values());
    }
    
    /**
     * Отримує сутності для певної системи.
     * <p>
     * У більш просунутій реалізації ми б фільтрували на основі вимог системи.
     *
     * @param system система
     * @return список сутностей для системи
     */
    public List<Entity> getEntitiesForSystem(System system) {
        // In a more advanced implementation, we would filter based on system requirements
        // For example, a MovementSystem would only want entities with Position and Velocity components
        List<Entity> filteredEntities = new ArrayList<>();
        
        // Get the component types that this system requires
        Set<Class<? extends Component>> requiredComponents = system.getRequiredComponents();
        
        // Filter entities based on required components
        for (Entity entity : entities.values()) {
            boolean hasAllRequiredComponents = true;
            for (Class<? extends Component> componentType : requiredComponents) {
                if (!entity.hasComponent(componentType)) {
                    hasAllRequiredComponents = false;
                    break;
                }
            }
            if (hasAllRequiredComponents) {
                filteredEntities.add(entity);
            }
        }
        
        return filteredEntities;
    }
}