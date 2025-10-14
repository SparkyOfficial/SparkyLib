package com.sparky.minecraft;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sparky.ecs.Component;
import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.ecs.System;
import com.sparky.minecraft.math.Vector3D;

/**
 * Система для управління світом Minecraft.
 *
 * @author Андрій Будильников
 */
public class WorldSystem extends System {
    private long lastUpdateTime;

    public WorldSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.lastUpdateTime = java.lang.System.currentTimeMillis();
    }

    @Override
    public void update(List<Entity> entities) {
        long currentTime = java.lang.System.currentTimeMillis();
        long timeDelta = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;
        
        for (Entity entity : entities) {
            WorldComponent world = entity.getComponent(WorldComponent.class);
            if (world != null) {
                // Оновлюємо час світу
                world.updateTime(timeDelta);
                
                // Оновлюємо погоду
                world.updateWeather(0.016); // Припускаємо 60 FPS
            }
        }
    }

    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> requiredComponents = new HashSet<>();
        requiredComponents.add(WorldComponent.class);
        return requiredComponents;
    }

    /**
     * Створює новий світ.
     */
    public Entity createWorld(String worldName, Vector3D spawnPoint) {
        Entity worldEntity = entityManager.createEntity();
        WorldComponent worldComponent = new WorldComponent(worldName, spawnPoint);
        worldEntity.addComponent(worldComponent);
        return worldEntity;
    }

    /**
     * Отримує компонент світу за ім'ям.
     */
    public WorldComponent getWorldByName(String worldName) {
        List<Entity> entities = entityManager.getAllEntities();
        for (Entity entity : entities) {
            if (entity.hasComponent(WorldComponent.class)) {
                WorldComponent world = entity.getComponent(WorldComponent.class);
                if (world != null && world.getWorldName().equals(worldName)) {
                    return world;
                }
            }
        }
        return null;
    }

    /**
     * Встановлює погоду у світі.
     */
    public void setWeather(String worldName, boolean raining, boolean thundering) {
        WorldComponent world = getWorldByName(worldName);
        if (world != null) {
            world.setRaining(raining);
            world.setThundering(thundering);
        }
    }

    /**
     * Встановлює силу дощу.
     */
    public void setRainStrength(String worldName, double strength) {
        WorldComponent world = getWorldByName(worldName);
        if (world != null) {
            world.setRainStrength(strength);
        }
    }

    /**
     * Встановлює силу грому.
     */
    public void setThunderStrength(String worldName, double strength) {
        WorldComponent world = getWorldByName(worldName);
        if (world != null) {
            world.setThunderStrength(strength);
        }
    }
}