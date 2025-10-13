package com.sparky.ui;

/**
 * Базовий інтерфейс для UI елемента.
 *
 * @author Андрій Будильников
 */
public interface UIElement {
    /**
     * Отримує ідентифікатор елемента.
     */
    String getId();
    
    /**
     * Встановлює видимість елемента.
     */
    void setVisible(boolean visible);
    
    /**
     * Перевіряє, чи елемент видимий.
     */
    boolean isVisible();
    
    /**
     * Встановлює позицію елемента.
     */
    void setPosition(int x, int y);
    
    /**
     * Отримує позицію X елемента.
     */
    int getX();
    
    /**
     * Отримує позицію Y елемента.
     */
    int getY();
    
    /**
     * Встановлює розмір елемента.
     */
    void setSize(int width, int height);
    
    /**
     * Отримує ширину елемента.
     */
    int getWidth();
    
    /**
     * Отримує висоту елемента.
     */
    int getHeight();
}