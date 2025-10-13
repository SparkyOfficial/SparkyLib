package com.sparky.ecs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Система виявлення колізій.
 * <p>
 * Перевіряє перетини між сутностями з компонентами колізій.
 *
 * @author Андрій Будильников
 */
public class CollisionSystem extends System {
    
    @Override
    public void update(List<Entity> entities) {
        // Перевіряємо колізії між усіма парами сутностей
        for (int i = 0; i < entities.size(); i++) {
            Entity entityA = entities.get(i);
            
            // Перевіряємо, чи має сутність необхідні компоненти
            if (!entityA.hasComponent(CollisionComponent.class) || !entityA.hasComponent(PositionComponent.class)) {
                continue;
            }
            
            CollisionComponent collisionA = entityA.getComponent(CollisionComponent.class);
            PositionComponent positionA = entityA.getComponent(PositionComponent.class);
            
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entityB = entities.get(j);
                
                // Перевіряємо, чи має сутність необхідні компоненти
                if (!entityB.hasComponent(CollisionComponent.class) || !entityB.hasComponent(PositionComponent.class)) {
                    continue;
                }
                
                CollisionComponent collisionB = entityB.getComponent(CollisionComponent.class);
                PositionComponent positionB = entityB.getComponent(PositionComponent.class);
                
                // Перевіряємо перетин
                if (collisionA.intersects(collisionB, positionA, positionB)) {
                    // Обробляємо колізію
                    handleCollision(entityA, entityB, collisionA, collisionB, positionA, positionB);
                }
            }
        }
    }
    
    /**
     * Обробляє колізію між двома сутностями.
     *
     * @param entityA перша сутність
     * @param entityB друга сутність
     * @param collisionA компонент колізії першої сутності
     * @param collisionB компонент колізії другої сутності
     * @param positionA позиція першої сутності
     * @param positionB позиція другої сутності
     */
    private void handleCollision(Entity entityA, Entity entityB, CollisionComponent collisionA, CollisionComponent collisionB, 
                                PositionComponent positionA, PositionComponent positionB) {
        // Отримуємо компоненти швидкості, якщо вони є
        VelocityComponent velocityA = entityA.getComponent(VelocityComponent.class);
        VelocityComponent velocityB = entityB.getComponent(VelocityComponent.class);
        
        PhysicsComponent physicsA = entityA.getComponent(PhysicsComponent.class);
        PhysicsComponent physicsB = entityB.getComponent(PhysicsComponent.class);
        
        // Проста обробка відскоку для сутностей з компонентами швидкості
        if (velocityA != null && velocityB != null) {
            // Розраховуємо коефіцієнти відскоку
            float bounceA = (physicsA != null) ? physicsA.getBounciness() : 0.3f;
            float bounceB = (physicsB != null) ? physicsB.getBounciness() : 0.3f;
            
            // Простий відскок: міняємо знак швидкості з урахуванням пружності
            velocityA.setDx(-velocityA.getDx() * bounceA);
            velocityA.setDy(-velocityA.getDy() * bounceA);
            velocityA.setDz(-velocityA.getDz() * bounceA);
            
            velocityB.setDx(-velocityB.getDx() * bounceB);
            velocityB.setDy(-velocityB.getDy() * bounceB);
            velocityB.setDz(-velocityB.getDz() * bounceB);
        }
        // Якщо тільки одна сутність має швидкість, відбиваємо її
        else if (velocityA != null) {
            float bounceA = (physicsA != null) ? physicsA.getBounciness() : 0.3f;
            velocityA.setDx(-velocityA.getDx() * bounceA);
            velocityA.setDy(-velocityA.getDy() * bounceA);
            velocityA.setDz(-velocityA.getDz() * bounceA);
        }
        else if (velocityB != null) {
            float bounceB = (physicsB != null) ? physicsB.getBounciness() : 0.3f;
            velocityB.setDx(-velocityB.getDx() * bounceB);
            velocityB.setDy(-velocityB.getDy() * bounceB);
            velocityB.setDz(-velocityB.getDz() * bounceB);
        }
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(CollisionComponent.class);
        required.add(PositionComponent.class);
        return required;
    }
}