package com.sparky.scheduler;

/**
 * Представляє заплановану задачу.
 *
 * @author Андрій Будильников
 */
public class ScheduledTask implements Comparable<ScheduledTask> {
    private final int id;
    private final Runnable task;
    private final long executionTick;
    private final long period;
    private boolean cancelled = false;
    
    public ScheduledTask(int id, Runnable task, long executionTick, long period) {
        this.id = id;
        this.task = task;
        this.executionTick = executionTick;
        this.period = period;
    }
    
    @Override
    public int compareTo(ScheduledTask other) {
        return Long.compare(this.executionTick, other.executionTick);
    }
    
    // Getters
    public int id() {
        return id;
    }
    
    public Runnable task() {
        return task;
    }
    
    public long executionTick() {
        return executionTick;
    }
    
    public long period() {
        return period;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void cancel() {
        this.cancelled = true;
    }
}