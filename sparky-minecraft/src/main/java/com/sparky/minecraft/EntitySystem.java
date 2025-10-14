package com.sparky.minecraft;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.ecs.System;
import com.sparky.minecraft.math.Vector3D;

/**
 * Система для управління сутностями Minecraft.
 *
 * @author Андрій Будильников
 */
public class EntitySystem extends System {
    
    public EntitySystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(List<Entity> entities) {
        // Оновлюємо всі сутності
        for (Entity entity : entities) {
            EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
            if (entityComponent != null) {
                // Тут можна додати логіку оновлення сутностей
                // Наприклад, регенерацію здоров'я, обробку ефектів тощо
            }
        }
    }

    @Override
    public Set<Class<? extends com.sparky.ecs.Component>> getRequiredComponents() {
        Set<Class<? extends com.sparky.ecs.Component>> requiredComponents = new HashSet<>();
        requiredComponents.add(EntityComponent.class);
        return requiredComponents;
    }

    /**
     * Створює нову сутність.
     */
    public Entity createEntity(String entityId, String entityType, Vector3D position) {
        Entity entity = entityManager.createEntity();
        EntityComponent entityComponent = new EntityComponent(entityId, entityType, position);
        entity.addComponent(entityComponent);
        return entity;
    }

    /**
     * Отримує сутність за ідентифікатором Minecraft.
     */
    public Entity getEntityById(String minecraftEntityId) {
        List<Entity> entities = entityManager.getAllEntities();
        for (Entity entity : entities) {
            if (entity.hasComponent(EntityComponent.class)) {
                EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
                if (entityComponent != null && 
                    entityComponent.getMinecraftEntityId().equals(minecraftEntityId)) {
                    return entity;
                }
            }
        }
        return null;
    }

    /**
     * Переміщує сутність.
     */
    public void moveEntity(String minecraftEntityId, Vector3D offset) {
        Entity entity = getEntityById(minecraftEntityId);
        if (entity != null) {
            EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
            if (entityComponent != null) {
                entityComponent.move(offset);
            }
        }
    }

    /**
     * Телепортує сутність.
     */
    public void teleportEntity(String minecraftEntityId, Vector3D newPosition) {
        Entity entity = getEntityById(minecraftEntityId);
        if (entity != null) {
            EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
            if (entityComponent != null) {
                entityComponent.teleport(newPosition);
            }
        }
    }

    /**
     * Наносить шкоду сутності.
     */
    public void damageEntity(String minecraftEntityId, double damage) {
        Entity entity = getEntityById(minecraftEntityId);
        if (entity != null) {
            EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
            if (entityComponent != null) {
                entityComponent.damage(damage);
            }
        }
    }

    /**
     * Лікує сутність.
     */
    public void healEntity(String minecraftEntityId, double health) {
        Entity entity = getEntityById(minecraftEntityId);
        if (entity != null) {
            EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
            if (entityComponent != null) {
                entityComponent.heal(health);
            }
        }
    }

    /**
     * Встановлює ім'я сутності.
     */
    public void setEntityName(String minecraftEntityId, String name, boolean visible) {
        Entity entity = getEntityById(minecraftEntityId);
        if (entity != null) {
            EntityComponent entityComponent = entity.getComponent(EntityComponent.class);
            if (entityComponent != null) {
                entityComponent.setCustomName(name);
                entityComponent.setCustomNameVisible(visible);
            }
        }
    }
}