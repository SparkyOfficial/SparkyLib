package com.sparky.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sparky.ecs.Component;
import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.ecs.System;
import com.sparky.minecraft.DimensionComponent.DimensionType;

/**
 * Система для управління вимірами в Minecraft.
 * Відповідає за створення, завантаження та управління вимірами.
 *
 * @author Богдан Кравчук
 */
public class DimensionSystem extends System {
    private EntityManager entityManager;
    private Map<String, Entity> dimensions;
    
    public DimensionSystem() {
        this.dimensions = new HashMap<>();
    }
    
    public DimensionSystem(EntityManager entityManager) {
        this();
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.hasComponent(DimensionComponent.class)) {
                DimensionComponent dimension = entity.getComponent(DimensionComponent.class);
                processDimensionUpdate(dimension);
            }
        }
    }
    
    /**
     * Обробляє оновлення виміру.
     */
    private void processDimensionUpdate(DimensionComponent dimension) {
    }
    
    /**
     * Створює новий вимір.
     *
     * @param dimensionType тип виміру
     * @param dimensionName ім'я виміру
     * @param seed сід світу
     * @param generatorType тип генератора
     * @return сутність виміру або null, якщо не вдалося створити
     */
    public Entity createDimension(DimensionType dimensionType, String dimensionName, 
                                 long seed, String generatorType) {
        if (entityManager == null) {
            return null;
        }
        
        if (dimensions.containsKey(dimensionName)) {
            return dimensions.get(dimensionName);
        }
        
        Entity dimensionEntity = entityManager.createEntity();
        boolean hasSkyLight = dimensionType == DimensionType.OVERWORLD;
        int minHeight = (dimensionType == DimensionType.NETHER) ? 0 : -64;
        int maxHeight = (dimensionType == DimensionType.NETHER) ? 128 : 320;
        
        DimensionComponent dimension = new DimensionComponent(
            dimensionType, dimensionName, seed, hasSkyLight, minHeight, maxHeight, generatorType);
        
        entityManager.addComponentToEntity(dimensionEntity, dimension);
        dimensions.put(dimensionName, dimensionEntity);
        
        return dimensionEntity;
    }
    
    /**
     * Отримує вимір за ім'ям.
     *
     * @param dimensionName ім'я виміру
     * @return сутність виміру або null, якщо не знайдено
     */
    public Entity getDimension(String dimensionName) {
        return dimensions.get(dimensionName);
    }
    
    /**
     * Отримує вимір за типом.
     *
     * @param dimensionType тип виміру
     * @return сутність виміру або null, якщо не знайдено
     */
    public Entity getDimension(DimensionType dimensionType) {
        for (Map.Entry<String, Entity> entry : dimensions.entrySet()) {
            Entity entity = entry.getValue();
            if (entity.hasComponent(DimensionComponent.class)) {
                DimensionComponent dimension = entity.getComponent(DimensionComponent.class);
                if (dimension.getDimensionType() == dimensionType) {
                    return entity;
                }
            }
        }
        return null;
    }
    
    /**
     * Перевіряє, чи існує вимір з заданим ім'ям.
     *
     * @param dimensionName ім'я виміру
     * @return true, якщо вимір існує
     */
    public boolean hasDimension(String dimensionName) {
        return dimensions.containsKey(dimensionName);
    }
    
    /**
     * Видаляє вимір.
     *
     * @param dimensionName ім'я виміру
     * @return true, якщо вимір видалено успішно
     */
    public boolean removeDimension(String dimensionName) {
        Entity entity = dimensions.remove(dimensionName);
        if (entity != null && entityManager != null) {
            entityManager.removeEntity(entity.getId());
            return true;
        }
        return false;
    }
    
    /**
     * Отримує всі виміри.
     *
     * @return мапа всіх вимірів
     */
    public Map<String, Entity> getAllDimensions() {
        return new HashMap<>(dimensions);
    }
    
    /**
     * Отримує кількість вимірів.
     *
     * @return кількість вимірів
     */
    public int getDimensionCount() {
        return dimensions.size();
    }
    
    /**
     * Перевіряє, чи позиція знаходиться в межах виміру.
     *
     * @param dimensionName ім'я виміру
     * @param y координата Y
     * @return true, якщо позиція в межах виміру
     */
    public boolean isPositionInBounds(String dimensionName, int y) {
        Entity dimensionEntity = getDimension(dimensionName);
        if (dimensionEntity == null || !dimensionEntity.hasComponent(DimensionComponent.class)) {
            return false;
        }
        
        DimensionComponent dimension = dimensionEntity.getComponent(DimensionComponent.class);
        return dimension.isWithinBounds(y);
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(DimensionComponent.class);
        return required;
    }
    
    /**
     * Встановлює менеджер сутностей.
     */
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}