package com.sparky.bukkit;

import com.sparky.core.SparkyLogger;
import com.sparky.events.EventBus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Обробник подій Bukkit, який інтегрується зі SparkyLib EventBus.
 *
 * @author Андрій Будильников
 */
public class BukkitEventHandler implements Listener {
    private static final SparkyLogger logger = SparkyLogger.getLogger(BukkitEventHandler.class);
    
    private final EventBus eventBus;
    
    public BukkitEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Convert Bukkit event to SparkyLib event and post it
        SparkyPlayerJoinEvent sparkyEvent = new SparkyPlayerJoinEvent(
            event.getPlayer().getUniqueId().toString(),
            event.getPlayer().getName()
        );
        eventBus.post(sparkyEvent);
        
        logger.debug("Posted SparkyPlayerJoinEvent for player: " + event.getPlayer().getName());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Convert Bukkit event to SparkyLib event and post it
        SparkyPlayerQuitEvent sparkyEvent = new SparkyPlayerQuitEvent(
            event.getPlayer().getUniqueId().toString(),
            event.getPlayer().getName()
        );
        eventBus.post(sparkyEvent);
        
        logger.debug("Posted SparkyPlayerQuitEvent for player: " + event.getPlayer().getName());
    }
}