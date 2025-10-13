package com.sparky.bukkit;

import com.sparky.core.SparkyLogger;
import com.sparky.events.EventBus;
import com.sparky.scheduler.SimpleScheduler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Основний адаптер для інтеграції SparkyLib з Bukkit/Spigot.
 *
 * @author Андрій Будильников
 */
public class BukkitAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(BukkitAdapter.class);
    
    private final JavaPlugin plugin;
    private final SimpleScheduler scheduler;
    private final EventBus eventBus;
    
    public BukkitAdapter(JavaPlugin plugin) {
        this.plugin = plugin;
        this.scheduler = new SimpleScheduler();
        this.eventBus = EventBus.getInstance();
        
        // Register the scheduler task
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::onTick, 1L, 1L);
        
        logger.info("BukkitAdapter initialized for plugin: " + plugin.getName());
    }
    
    /**
     * Метод, який викликається кожен тік сервера.
     */
    private void onTick() {
        // Update the scheduler
        scheduler.tick();
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
}