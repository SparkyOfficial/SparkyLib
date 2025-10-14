package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Система для управління предметами Minecraft.
 *
 * @author Андрій Будильников
 */
public class ItemSystem extends com.sparky.ecs.System {
    
    @Override
    public void update(List<Entity> entities) {
        
        for (Entity entity : entities) {
            if (entity.hasComponent(com.sparky.minecraft.ItemComponent.class)) {
                com.sparky.minecraft.ItemComponent item = entity.getComponent(com.sparky.minecraft.ItemComponent.class);
                
                
                processItemUpdate(item);
            }
        }
    }
    
    /**
     * Обробляє оновлення предмету.
     */
    private void processItemUpdate(com.sparky.minecraft.ItemComponent item) {
        
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(com.sparky.minecraft.ItemComponent.class);
        return required;
    }
}