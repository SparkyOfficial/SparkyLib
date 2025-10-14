package com.sparky.minecraft;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sparky.ecs.Component;
import com.sparky.ecs.Entity;

/**
 * Система для управління блоками Minecraft.
 *
 * @author Андрій Будильников
 */
public class BlockSystem extends com.sparky.ecs.System {
    
    @Override
    public void update(List<Entity> entities) {
        
        for (Entity entity : entities) {
            if (entity.hasComponent(com.sparky.minecraft.BlockComponent.class)) {
                com.sparky.minecraft.BlockComponent block = entity.getComponent(com.sparky.minecraft.BlockComponent.class);
                
                
                processBlockChange(block);
            }
        }
    }
    
    /**
     * Обробляє зміну блоку.
     */
    private void processBlockChange(com.sparky.minecraft.BlockComponent block) {
        
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(com.sparky.minecraft.BlockComponent.class);
        return required;
    }
}