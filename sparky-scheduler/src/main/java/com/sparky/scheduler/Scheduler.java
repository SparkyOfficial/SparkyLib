package com.sparky.scheduler;

/**
 * Інтерфейс для планувальника задач.
 *
 * @author Андрій Будильников
 */
public interface Scheduler {
    /**
     * Планує виконання задачі з вказаною затримкою.
     *
     * @param task задача для виконання
     * @param delay затримка у тіках
     * @return ідентифікатор задачі
     */
    int schedule(Runnable task, long delay);
    
    /**
     * Планує повторювану задачу.
     *
     * @param task задача для виконання
     * @param delay початкова затримка у тіках
     * @param period період повторення у тіках
     * @return ідентифікатор задачі
     */
    int scheduleRepeating(Runnable task, long delay, long period);
    
    /**
     * Скасовує задачу за її ідентифікатором.
     *
     * @param taskId ідентифікатор задачі
     */
    void cancel(int taskId);
    
    /**
     * Виконує один крок планувальника.
     */
    void tick();
}