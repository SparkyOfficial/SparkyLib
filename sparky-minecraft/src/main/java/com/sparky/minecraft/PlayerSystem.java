package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Система для управління гравцями Minecraft.
 *
 * @author Андрій Будильников
 */
public class PlayerSystem extends com.sparky.ecs.System {
    
    @Override
    public void update(List<Entity> entities) {
        
        for (Entity entity : entities) {
            if (entity.hasComponent(com.sparky.minecraft.PlayerComponent.class)) {
                com.sparky.minecraft.PlayerComponent player = entity.getComponent(com.sparky.minecraft.PlayerComponent.class);
                
                
                processPlayerUpdate(player);
            }
        }
    }
    
    /**
     * Обробляє оновлення стану гравця.
     */
    private void processPlayerUpdate(com.sparky.minecraft.PlayerComponent player) {
        
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(com.sparky.minecraft.PlayerComponent.class);
        return required;
    }
}