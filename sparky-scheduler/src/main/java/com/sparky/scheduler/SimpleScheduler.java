package com.sparky.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.sparky.core.SparkyLogger;

/**
 * Проста реалізація планувальника задач.
 *
 * @author Андрій Будильников
 */
public class SimpleScheduler implements Scheduler {
    private final SparkyLogger logger = SparkyLogger.getLogger(SimpleScheduler.class);
    
    private final PriorityQueue<ScheduledTask> taskQueue = new PriorityQueue<>();
    private final Map<Integer, ScheduledTask> taskMap = new HashMap<>();
    private int nextTaskId = 1;
    private long currentTick = 0;
    
    @Override
    public int schedule(Runnable task, long delay) {
        int taskId = nextTaskId++;
        ScheduledTask scheduledTask = new ScheduledTask(taskId, task, currentTick + delay, -1);
        taskQueue.add(scheduledTask);
        taskMap.put(taskId, scheduledTask);
        return taskId;
    }
    
    @Override
    public int scheduleRepeating(Runnable task, long delay, long period) {
        int taskId = nextTaskId++;
        ScheduledTask scheduledTask = new ScheduledTask(taskId, task, currentTick + delay, period);
        taskQueue.add(scheduledTask);
        taskMap.put(taskId, scheduledTask);
        return taskId;
    }
    
    @Override
    public void cancel(int taskId) {
        ScheduledTask task = taskMap.remove(taskId);
        if (task != null) {
            task.cancel();
        }
    }
    
    @Override
    public void tick() {
        currentTick++;
        
        while (!taskQueue.isEmpty() && taskQueue.peek().executionTick() <= currentTick) {
            ScheduledTask task = taskQueue.poll();
            
            // Skip cancelled tasks
            if (task.isCancelled()) {
                taskMap.remove(task.id());
                continue;
            }
            
            try {
                task.task().run();
            } catch (Exception e) {
                logger.error("Error executing scheduled task", e);
            }
            
            // Reschedule repeating tasks
            if (task.period() > 0) {
                ScheduledTask rescheduledTask = new ScheduledTask(
                    task.id(), 
                    task.task(), 
                    currentTick + task.period(), 
                    task.period()
                );
                taskQueue.add(rescheduledTask);
                taskMap.put(task.id(), rescheduledTask);
            } else {
                taskMap.remove(task.id());
            }
        }
    }
    
    /**
     * Отримує поточний тік.
     */
    public long getCurrentTick() {
        return currentTick;
    }
}