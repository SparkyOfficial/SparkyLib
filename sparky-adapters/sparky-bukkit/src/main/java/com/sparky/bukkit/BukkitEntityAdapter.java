package com.sparky.bukkit;

import com.sparky.core.SparkyLogger;
import com.sparky.ecs.Entity;
import com.sparky.ecs.PositionComponent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

/**
 * Адаптер для інтеграції сутностей SparkyLib з сутностями Bukkit.
 *
 * @author Андрій Будильников
 */
public class BukkitEntityAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(BukkitEntityAdapter.class);
    
    /**
     * Створює сутність Bukkit з сутності SparkyLib.
     */
    public org.bukkit.entity.Entity spawnEntity(Entity sparkyEntity, World world, EntityType type) {
        // Get position from the entity if it has a PositionComponent
        double x = 0, y = 0, z = 0;
        
        if (sparkyEntity.hasComponent(PositionComponent.class)) {
            PositionComponent position = sparkyEntity.getComponent(PositionComponent.class);
            x = position.getX();
            y = position.getY();
            z = position.getZ();
        }
        
        Location location = new Location(world, x, y, z);
        org.bukkit.entity.Entity bukkitEntity = world.spawnEntity(location, type);
        
        logger.debug("Spawned Bukkit entity " + type + " for SparkyLib entity " + sparkyEntity.getId());
        
        return bukkitEntity;
    }
    
    /**
     * Оновлює позицію сутності Bukkit згідно з компонентом позиції SparkyLib.
     */
    public void updateEntityPosition(Entity sparkyEntity, org.bukkit.entity.Entity bukkitEntity) {
        if (sparkyEntity.hasComponent(PositionComponent.class)) {
            PositionComponent position = sparkyEntity.getComponent(PositionComponent.class);
            
            Location location = bukkitEntity.getLocation();
            location.setX(position.getX());
            location.setY(position.getY());
            location.setZ(position.getZ());
            
            bukkitEntity.teleport(location);
            
            logger.debug("Updated position for entity " + sparkyEntity.getId());
        }
    }
}