package com.sparky.minecraft;

import com.sparky.core.SparkyLogger;
import com.sparky.ecs.EntityManager;
import com.sparky.bukkit.BukkitAdapter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Основний адаптер для інтеграції SparkyLib з Minecraft.
 *
 * @author Андрій Будильников
 */
public class MinecraftAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(MinecraftAdapter.class);
    
    private final JavaPlugin plugin;
    private final BukkitAdapter bukkitAdapter;
    private final EntityManager entityManager;
    
    // Системи Minecraft
    private final com.sparky.minecraft.BlockSystem blockSystem;
    private final com.sparky.minecraft.PlayerSystem playerSystem;
    private final com.sparky.minecraft.ItemSystem itemSystem;
    
    public MinecraftAdapter(JavaPlugin plugin) {
        this.plugin = plugin;
        this.bukkitAdapter = new BukkitAdapter(plugin);
        this.entityManager = new EntityManager();
        
        // Ініціалізуємо системи
        this.blockSystem = new com.sparky.minecraft.BlockSystem();
        this.playerSystem = new com.sparky.minecraft.PlayerSystem();
        this.itemSystem = new com.sparky.minecraft.ItemSystem();
        
        // Налаштовуємо системи
        this.blockSystem.setEntityManager(entityManager);
        this.playerSystem.setEntityManager(entityManager);
        this.itemSystem.setEntityManager(entityManager);
        
        logger.info("MinecraftAdapter initialized for plugin: " + plugin.getName());
    }
    
    /**
     * Оновлює всі системи Minecraft.
     */
    public void update() {
        // Отримуємо сутності для кожної системи
        java.util.List<com.sparky.ecs.Entity> blockEntities = entityManager.getEntitiesForSystem(blockSystem);
        java.util.List<com.sparky.ecs.Entity> playerEntities = entityManager.getEntitiesForSystem(playerSystem);
        java.util.List<com.sparky.ecs.Entity> itemEntities = entityManager.getEntitiesForSystem(itemSystem);
        
        // Оновлюємо системи
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
     * Отримує менеджер сутностей.
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    /**
     * Отримує адаптер Bukkit.
     */
    public BukkitAdapter getBukkitAdapter() {
        return bukkitAdapter;
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