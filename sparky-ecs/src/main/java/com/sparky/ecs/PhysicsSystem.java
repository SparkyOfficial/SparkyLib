package com.sparky.ecs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Система фізики.
 * <p>
 * Обробляє фізичні взаємодії сутностей, включаючи гравітацію, тертя та прискорення.
 *
 * @author Андрій Будильников
 */
public class PhysicsSystem extends System {
    private float gravity = 9.81f; // Гравітація (м/с²)
    private float deltaTime = 1.0f / 60.0f; // Delta time для розрахунків
    
    @Override
    public void update(List<Entity> entities) {
        // Оновлюємо фізику для всіх сутностей з необхідними компонентами
        for (Entity entity : entities) {
            if (entity.hasComponent(PhysicsComponent.class) && entity.hasComponent(VelocityComponent.class)) {
                PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);
                VelocityComponent velocity = entity.getComponent(VelocityComponent.class);
                
                // Застосовуємо гравітацію, якщо об'єкт на неї реагує
                if (physics.isAffectedByGravity()) {
                    velocity.setDy(velocity.getDy() - gravity * physics.getGravityScale() * deltaTime);
                }
                
                // Застосовуємо тертя
                velocity.setDx(velocity.getDx() * (1.0f - physics.getFriction() * deltaTime));
                velocity.setDy(velocity.getDy() * (1.0f - physics.getFriction() * deltaTime));
                velocity.setDz(velocity.getDz() * (1.0f - physics.getFriction() * deltaTime));
                
                // Застосовуємо прискорення до швидкості
                velocity.setDx(velocity.getDx() + physics.getAccelerationX() * deltaTime);
                velocity.setDy(velocity.getDy() + physics.getAccelerationY() * deltaTime);
                velocity.setDz(velocity.getDz() + physics.getAccelerationZ() * deltaTime);
                
                // Скидаємо прискорення після застосування
                physics.setAccelerationX(0);
                physics.setAccelerationY(0);
                physics.setAccelerationZ(0);
            }
        }
    }
    
    /**
     * Встановлює силу гравітації.
     *
     * @param gravity сила гравітації
     */
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    
    /**
     * Отримує поточну силу гравітації.
     *
     * @return сила гравітації
     */
    public float getGravity() {
        return gravity;
    }
    
    /**
     * Встановлює delta time для розрахунків.
     *
     * @param deltaTime час між кадрами
     */
    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }
    
    /**
     * Отримує поточний delta time.
     *
     * @return delta time
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
        required.add(PhysicsComponent.class);
        required.add(VelocityComponent.class);
        return required;
    }
}