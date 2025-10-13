package com.sparky.ui;

import java.util.function.Consumer;

/**
 * Елемент повзунка для вибору значення в діапазоні.
 *
 * @author Андрій Будильников
 */
public class Slider implements UIElement {
    private final String id;
    private boolean visible = true;
    private int x, y;
    private int width, height;
    private float value = 0.0f;
    private float minValue = 0.0f;
    private float maxValue = 1.0f;
    private boolean vertical = false;
    private Consumer<Float> onValueChangedHandler;
    
    public Slider(String id) {
        this.id = id;
        this.width = 100;  // Default width for horizontal slider
        this.height = 20;  // Default height for horizontal slider
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    @Override
    public boolean isVisible() {
        return visible;
    }
    
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    /**
     * Отримує поточне значення повзунка.
     */
    public float getValue() {
        return value;
    }
    
    /**
     * Встановлює значення повзунка.
     */
    public void setValue(float value) {
        this.value = Math.max(minValue, Math.min(maxValue, value));
        if (onValueChangedHandler != null) {
            onValueChangedHandler.accept(this.value);
        }
    }
    
    /**
     * Отримує мінімальне значення.
     */
    public float getMinValue() {
        return minValue;
    }
    
    /**
     * Встановлює мінімальне значення.
     */
    public void setMinValue(float minValue) {
        this.minValue = minValue;
        // Adjust current value if needed
        if (this.value < minValue) {
            setValue(minValue);
        }
    }
    
    /**
     * Отримує максимальне значення.
     */
    public float getMaxValue() {
        return maxValue;
    }
    
    /**
     * Встановлює максимальне значення.
     */
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        // Adjust current value if needed
        if (this.value > maxValue) {
            setValue(maxValue);
        }
    }
    
    /**
     * Перевіряє, чи повзунок вертикальний.
     */
    public boolean isVertical() {
        return vertical;
    }
    
    /**
     * Встановлює орієнтацію повзунка.
     */
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
    
    /**
     * Встановлює обробник зміни значення.
     */
    public void setOnValueChanged(Consumer<Float> handler) {
        this.onValueChangedHandler = handler;
    }
    
    /**
     * Встановлює значення на основі позиції кліку.
     */
    public void setValueFromPosition(int clickX, int clickY) {
        if (vertical) {
            // For vertical slider, y position determines value
            float ratio = 1.0f - Math.max(0, Math.min(1, (float)(clickY - y) / height));
            setValue(minValue + ratio * (maxValue - minValue));
        } else {
            // For horizontal slider, x position determines value
            float ratio = Math.max(0, Math.min(1, (float)(clickX - x) / width));
            setValue(minValue + ratio * (maxValue - minValue));
        }
    }
}