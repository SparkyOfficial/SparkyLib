package com.sparky.minecraft;

import org.bukkit.plugin.java.JavaPlugin;

import com.sparky.core.SparkyLogger;
import com.sparky.ecs.EntityManager;
import com.sparky.events.EventBus;
import com.sparky.scheduler.SimpleScheduler;

/**
 * Основний адаптер для інтеграції SparkyLib з Minecraft.
 *
 * @author Андрій Будильников
 */
public class MinecraftAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(MinecraftAdapter.class);
    
    private final JavaPlugin plugin;
    private final SimpleScheduler scheduler;
    private final EventBus eventBus;
    private final EntityManager entityManager;
    
    
    private final com.sparky.minecraft.BlockSystem blockSystem;
    private final com.sparky.minecraft.PlayerSystem playerSystem;
    private final com.sparky.minecraft.ItemSystem itemSystem;
    
    public MinecraftAdapter(JavaPlugin plugin) {
        this.plugin = plugin;
        this.scheduler = new SimpleScheduler();
        this.eventBus = EventBus.getInstance();
        this.entityManager = new EntityManager();
        
        this.blockSystem = new com.sparky.minecraft.BlockSystem();
        this.playerSystem = new com.sparky.minecraft.PlayerSystem();
        this.itemSystem = new com.sparky.minecraft.ItemSystem();
        
        this.blockSystem.setEntityManager(entityManager);
        this.playerSystem.setEntityManager(entityManager);
        this.itemSystem.setEntityManager(entityManager);
        
        // Register the scheduler task
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::onTick, 1L, 1L);
        
        logger.info("MinecraftAdapter initialized for plugin: " + plugin.getName());
    }
    
    /**
     * Метод, який викликається кожен тік сервера.
     */
    private void onTick() {
        
        scheduler.tick();
        
        
        update();
    }
    
    /**
     * Оновлює всі системи Minecraft.
     */
    public void update() {
        
        java.util.List<com.sparky.ecs.Entity> blockEntities = entityManager.getEntitiesForSystem(blockSystem);
        java.util.List<com.sparky.ecs.Entity> playerEntities = entityManager.getEntitiesForSystem(playerSystem);
        java.util.List<com.sparky.ecs.Entity> itemEntities = entityManager.getEntitiesForSystem(itemSystem);
        
        
        blockSystem.update(blockEntities);
        playerSystem.update(playerEntities);
        itemSystem.update(itemEntities);
    }
    
    /**
     * Отримує плагін Bukkit.
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }
    
    /**
     * Отримує планувальник.
     */
    public SimpleScheduler getScheduler() {
        return scheduler;
    }
    
    /**
     * Отримує шину подій.
     */
    public EventBus getEventBus() {
        return eventBus;
    }
    
    /**
     * Отримує менеджер сутностей.
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    /**
     * Отримує систему блоків.
     */
    public com.sparky.minecraft.BlockSystem getBlockSystem() {
        return blockSystem;
    }
    
    /**
     * Отримує систему гравців.
     */
    public com.sparky.minecraft.PlayerSystem getPlayerSystem() {
        return playerSystem;
    }
    
    /**
     * Отримує систему предметів.
     */
    public com.sparky.minecraft.ItemSystem getItemSystem() {
        return itemSystem;
    }
}