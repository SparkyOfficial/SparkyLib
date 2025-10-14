package com.sparky.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import com.sparky.core.SparkyLogger;
import com.sparky.events.EventBus;
import com.sparky.minecraft.MinecraftAdapter;
import com.sparky.scheduler.SimpleScheduler;

/**
 * Основний адаптер для інтеграції SparkyLib з Bukkit/Spigot.
 *
 * @author Андрій Будильников
 */
public class BukkitAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(BukkitAdapter.class);
    
    private final MinecraftAdapter minecraftAdapter;
    
    public BukkitAdapter(JavaPlugin plugin) {
        this.minecraftAdapter = new MinecraftAdapter(plugin);
        logger.info("BukkitAdapter initialized for plugin: " + plugin.getName());
    }
    
    /**
     * Отримує адаптер Minecraft.
     */
    public MinecraftAdapter getMinecraftAdapter() {
        return minecraftAdapter;
    }
    
    /**
     * Отримує планувальник.
     */
    public SimpleScheduler getScheduler() {
        return minecraftAdapter.getScheduler();
    }
    
    /**
     * Отримує шину подій.
     */
    public EventBus getEventBus() {
        return minecraftAdapter.getEventBus();
    }
}