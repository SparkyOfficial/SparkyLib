package com.sparky.ecs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Базовий клас для систем ECS.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
public abstract class System {
    protected EntityManager entityManager;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Виконує логіку системи для заданого списку сутностей.
     */
    public abstract void update(List<Entity> entities);
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    public Set<Class<? extends Component>> getRequiredComponents() {
        return new HashSet<>();
    }
}