package com.sparky.ecs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Система руху.
 * <p>
 * Оновлює позиції сутностей на основі їх швидкості.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public class MovementSystem extends System {
    private float deltaTime = 1.0f / 60.0f; // Default to 60 FPS
    
    @Override
    public void update(List<Entity> entities) {
        // Process entities that have both Position and Velocity components
        for (Entity entity : entities) {
            if (entity.hasComponent(PositionComponent.class) && entity.hasComponent(VelocityComponent.class)) {
                PositionComponent position = entity.getComponent(PositionComponent.class);
                VelocityComponent velocity = entity.getComponent(VelocityComponent.class);
                
                // Update position based on velocity and delta time for frame-rate independent movement
                position.setX(position.getX() + velocity.getDx() * deltaTime);
                position.setY(position.getY() + velocity.getDy() * deltaTime);
                position.setZ(position.getZ() + velocity.getDz() * deltaTime);
            }
        }
    }
    
    /**
     * Встановлює delta time для розрахунків руху.
     *
     * @param deltaTime час між кадрами в секундах
     */
    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }
    
    /**
     * Отримує поточний delta time.
     *
     * @return delta time в секундах
     */
    public float getDeltaTime() {
        return deltaTime;
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(PositionComponent.class);
        required.add(VelocityComponent.class);
        return required;
    }
}