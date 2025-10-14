package com.sparky.ecs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sparky.core.SparkyLogger;

/**
 * Менеджер сутностей ECS.
 * <p>
 * Відповідає за створення, зберігання та видалення сутностей.
 * Також відповідає за отримання сутностей для систем.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class EntityManager {
    private static final SparkyLogger logger = SparkyLogger.getLogger(EntityManager.class);
    
    private final Map<Integer, Entity> entities = new ConcurrentHashMap<>();
    private final Map<Class<? extends Component>, Set<Integer>> componentIndex = new ConcurrentHashMap<>();
    private int nextEntityId = 1;
    private boolean indexDirty = false;
    
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
            // Оновлюємо індекс компонентів
            for (Class<? extends Component> componentType : entity.getComponents().keySet()) {
                Set<Integer> entityIds = componentIndex.get(componentType);
                if (entityIds != null) {
                    entityIds.remove(id);
                }
            }
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
     * Фільтрує сутності на основі вимог системи.
     *
     * @param system система
     * @return список сутностей для системи
     */
    public List<Entity> getEntitiesForSystem(System system) {
        // Отримуємо типи компонентів, необхідних для цієї системи
        Set<Class<? extends Component>> requiredComponents = system.getRequiredComponents();
        
        // Якщо система не вимагає конкретних компонентів, повертаємо всі сутності
        if (requiredComponents.isEmpty()) {
            return getAllEntities();
        }
        
        // Використовуємо індекс для ефіцієнтного фільтрування
        return getEntitiesWithComponents(requiredComponents);
    }
    
    /**
     * Отримує сутності, які мають всі вказані компоненти.
     *
     * @param componentTypes типи компонентів
     * @return список сутностей з усіма вказаними компонентами
     */
    public List<Entity> getEntitiesWithComponents(Set<Class<? extends Component>> componentTypes) {
        if (componentTypes.isEmpty()) {
            return getAllEntities();
        }
        
        // Перевіряємо, чи індекс потрібно оновити
        ensureComponentIndexUpToDate();
        
        // Знаходимо множину ідентифікаторів сутностей, які мають всі необхідні компоненти
        Set<Integer> candidateIds = null;
        
        for (Class<? extends Component> componentType : componentTypes) {
            Set<Integer> entityIds = componentIndex.get(componentType);
            if (entityIds == null || entityIds.isEmpty()) {
                // Якщо для будь-якого типу компонентів немає сутностей, повертаємо порожній список
                return new ArrayList<>();
            }
            
            if (candidateIds == null) {
                candidateIds = new java.util.HashSet<>(entityIds);
            } else {
                // Зберігаємо лише ті ідентифікатори, які присутні в усіх множинах
                candidateIds.retainAll(entityIds);
            }
        }
        
        // Перетворюємо ідентифікатори в сутності
        List<Entity> result = new ArrayList<>();
        if (candidateIds != null) {
            for (Integer id : candidateIds) {
                Entity entity = entities.get(id);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Отримує сутності, які мають певний компонент.
     *
     * @param componentType тип компонента
     * @return список сутностей з вказаним компонентом
     */
    public <T extends Component> List<Entity> getEntitiesWithComponent(Class<T> componentType) {
        // Перевіряємо, чи індекс потрібно оновити
        ensureComponentIndexUpToDate();
        
        Set<Integer> entityIds = componentIndex.get(componentType);
        if (entityIds == null || entityIds.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Entity> result = new ArrayList<>();
        for (Integer id : entityIds) {
            Entity entity = entities.get(id);
            if (entity != null) {
                result.add(entity);
            }
        }
        
        return result;
    }
    
    /**
     * Отримує сутності, які відповідають заданому предикату.
     *
     * @param predicate предикат для фільтрації
     * @return список сутностей, які відповідають предикату
     */
    public List<Entity> getEntities(Predicate<Entity> predicate) {
        return entities.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
    
    /**
     * Додає компонент до сутності та оновлює індекс.
     *
     * @param entity сутність
     * @param component компонент
     */
    public void addComponentToEntity(Entity entity, Component component) {
        entity.addComponent(component);
        
        // Оновлюємо індекс компонентів
        Class<? extends Component> componentType = component.getClass();
        Set<Integer> entityIds = componentIndex.computeIfAbsent(componentType, k -> ConcurrentHashMap.newKeySet());
        entityIds.add(entity.getId());
        indexDirty = true;
    }
    
    /**
     * Видаляє компонент з сутності та оновлює індекс.
     *
     * @param entity сутність
     * @param componentType тип компонента
     */
    public void removeComponentFromEntity(Entity entity, Class<? extends Component> componentType) {
        entity.removeComponent(componentType);
        
        // Оновлюємо індекс компонентів
        Set<Integer> entityIds = componentIndex.get(componentType);
        if (entityIds != null) {
            entityIds.remove(entity.getId());
        }
        indexDirty = true;
    }
    
    /**
     * Оновлює індекс компонентів для всіх сутностей.
     * Використовується для ініціалізації або відновлення індексу.
     */
    public void rebuildComponentIndex() {
        componentIndex.clear();
        
        for (Entity entity : entities.values()) {
            for (Class<? extends Component> componentType : entity.getComponents().keySet()) {
                Set<Integer> entityIds = componentIndex.computeIfAbsent(componentType, k -> ConcurrentHashMap.newKeySet());
                entityIds.add(entity.getId());
            }
        }
        indexDirty = false;
    }
    
    /**
     * Переконується, що індекс компонентів актуальний.
     * Якщо індекс був змінений напряму (не через EntityManager), оновлює його.
     */
    private void ensureComponentIndexUpToDate() {
        // Якщо індекс брудний або порожній, перебудовуємо його
        if (indexDirty || componentIndex.isEmpty()) {
            rebuildComponentIndex();
        } else {
            // Перевіряємо, чи всі сутності представлені в індексі
            for (Entity entity : entities.values()) {
                Map<Class<? extends Component>, Component> entityComponents = entity.getComponents();
                for (Class<? extends Component> componentType : entityComponents.keySet()) {
                    Set<Integer> entityIds = componentIndex.get(componentType);
                    if (entityIds == null || !entityIds.contains(entity.getId())) {
                        // Якщо знайдено сутність, яка не представлена в індексі, перебудовуємо індекс
                        rebuildComponentIndex();
                        return;
                    }
                }
            }
        }
    }
    
    /**
     * Отримує кількість сутностей в менеджері.
     *
     * @return кількість сутностей
     */
    public int getEntityCount() {
        return entities.size();
    }
    
    /**
     * Отримує кількість сутностей з певним компонентом.
     *
     * @param componentType тип компонента
     * @return кількість сутностей з вказаним компонентом
     */
    public int getEntityCountWithComponent(Class<? extends Component> componentType) {
        // Перевіряємо, чи індекс потрібно оновити
        ensureComponentIndexUpToDate();
        
        Set<Integer> entityIds = componentIndex.get(componentType);
        return entityIds != null ? entityIds.size() : 0;
    }
}