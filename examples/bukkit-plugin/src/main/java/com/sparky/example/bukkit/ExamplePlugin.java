package com.sparky.example.bukkit;

import com.sparky.events.EventBus;
import com.sparky.events.Subscribe;
import com.sparky.particles.ParticleEffect;
import com.sparky.particles.ParticleSpec;
import com.sparky.scheduler.SimpleScheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Приклад плагіна Bukkit, що використовує SparkyLib.
 *
 * @author Андрій Будильников
 */
public class ExamplePlugin extends JavaPlugin {
    private SimpleScheduler scheduler;
    private EventBus eventBus;
    
    @Override
    public void onEnable() {
        // Ініціалізація компонентів SparkyLib
        scheduler = new SimpleScheduler();
        eventBus = EventBus.getInstance();
        
        // Реєстрація слухачів подій
        eventBus.registerListener(this);
        
        // Створення прикладного ефекту частинок
        createExampleParticleEffect();
        
        getLogger().info("SparkyLib Example Plugin enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("SparkyLib Example Plugin disabled!");
    }
    
    /**
     * Створює прикладний ефект частинок.
     */
    private void createExampleParticleEffect() {
        ParticleSpec spec = new ParticleSpec(
            "flame",
            0.5f,
            10,
            new HashMap<>()
        );
        
        ParticleEffect effect = new ParticleEffect(
            "example_effect",
            Arrays.asList(spec)
        );
        
        // Тут можна зберегти ефект або використовувати його
        getLogger().info("Created particle effect: " + effect.getName());
    }
    
    /**
     * Приклад обробника подій.
     */
    @Subscribe(priority = 1, async = false)
    public void onExampleEvent(ExampleEvent event) {
        getLogger().info("Received example event: " + event.getMessage());
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