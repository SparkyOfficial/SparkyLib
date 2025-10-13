package com.sparky.libgdx;

import com.sparky.core.SparkyLogger;
import com.sparky.events.EventBus;
import com.sparky.scheduler.SimpleScheduler;

/**
 * Основний адаптер для інтеграції SparkyLib з LibGDX.
 *
 * @author Андрій Будильников
 */
public class LibGDXAdapter {
    private static final SparkyLogger logger = SparkyLogger.getLogger(LibGDXAdapter.class);
    
    private final SimpleScheduler scheduler;
    private final EventBus eventBus;
    
    public LibGDXAdapter() {
        this.scheduler = new SimpleScheduler();
        this.eventBus = EventBus.getInstance();
        
        logger.info("LibGDXAdapter initialized");
    }
    
    /**
     * Метод, який викликається кожен кадр LibGDX.
     */
    public void render() {
        // Update the scheduler
        scheduler.tick();
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