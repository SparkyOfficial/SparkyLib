package com.sparky.bukkit;

import com.sparky.core.SparkyLogger;
import com.sparky.scheduler.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * Адаптер для інтеграції планувальника SparkyLib з планувальником Bukkit.
 *
 * @author Андрій Будильников
 */
public class BukkitSchedulerAdapter implements Scheduler {
    private static final SparkyLogger logger = SparkyLogger.getLogger(BukkitSchedulerAdapter.class);
    
    private final JavaPlugin plugin;
    private final Map<Integer, BukkitTask> tasks = new HashMap<>();
    private int nextTaskId = 1;
    
    public BukkitSchedulerAdapter(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public int schedule(Runnable task, long delay) {
        int taskId = nextTaskId++;
        
        BukkitTask bukkitTask = plugin.getServer().getScheduler().runTaskLater(
            plugin, 
            task, 
            delay
        );
        
        tasks.put(taskId, bukkitTask);
        logger.debug("Scheduled task " + taskId + " with delay " + delay);
        
        return taskId;
    }
    
    @Override
    public int scheduleRepeating(Runnable task, long delay, long period) {
        int taskId = nextTaskId++;
        
        BukkitTask bukkitTask = plugin.getServer().getScheduler().runTaskTimer(
            plugin, 
            task, 
            delay, 
            period
        );
        
        tasks.put(taskId, bukkitTask);
        logger.debug("Scheduled repeating task " + taskId + " with delay " + delay + " and period " + period);
        
        return taskId;
    }
    
    @Override
    public void cancel(int taskId) {
        BukkitTask task = tasks.remove(taskId);
        if (task != null) {
            task.cancel();
            logger.debug("Cancelled task " + taskId);
        }
    }
    
    @Override
    public void tick() {
        // Bukkit scheduler handles ticking automatically
        // This method is here to satisfy the Scheduler interface
    }
    
    /**
     * Виконує задачу асинхронно.
     */
    public Future<Void> runAsync(Runnable task) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                task.run();
                future.complete(null);
            } catch (Exception e) {
                logger.error("Error in async task", e);
                future.completeExceptionally(e);
            }
        });
        
        return future;
    }
    
    /**
     * Виконує задачу у основному потоці.
     */
    public void runSync(Runnable task) {
        plugin.getServer().getScheduler().runTask(plugin, task);
    }
}