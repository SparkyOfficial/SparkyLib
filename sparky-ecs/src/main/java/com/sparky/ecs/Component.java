package com.sparky.ecs;

/**
 * Базовий клас для компонентів ECS.
 *
 * @author Андрій Будильников
 */
public abstract class Component {
    private int entityId;
    
    public int getEntityId() {
        return entityId;
    }
    
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}