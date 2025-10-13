package com.sparky.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Елемент панелі для групування інших UI елементів.
 *
 * @author Андрій Будильников
 */
public class Panel implements UIElement {
    private final String id;
    private boolean visible = true;
    private int x, y;
    private int width, height;
    private final List<UIElement> children = new ArrayList<>();
    private int backgroundColor = 0xFF000000; // Black background by default
    private boolean hasBorder = false;
    private int borderColor = 0xFFFFFFFF; // White border by default
    private int borderWidth = 1;
    
    public Panel(String id) {
        this.id = id;
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
     * Додає дочірній елемент до панелі.
     */
    public void addChild(UIElement child) {
        children.add(child);
    }
    
    /**
     * Видаляє дочірній елемент з панелі.
     */
    public void removeChild(UIElement child) {
        children.remove(child);
    }
    
    /**
     * Отримує всі дочірні елементи.
     */
    public List<UIElement> getChildren() {
        return new ArrayList<>(children);
    }
    
    /**
     * Очищує всі дочірні елементи.
     */
    public void clearChildren() {
        children.clear();
    }
    
    /**
     * Отримує колір фону.
     */
    public int getBackgroundColor() {
        return backgroundColor;
    }
    
    /**
     * Встановлює колір фону.
     */
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    /**
     * Перевіряє, чи панель має рамку.
     */
    public boolean hasBorder() {
        return hasBorder;
    }
    
    /**
     * Встановлює наявність рамки.
     */
    public void setHasBorder(boolean hasBorder) {
        this.hasBorder = hasBorder;
    }
    
    /**
     * Отримує колір рамки.
     */
    public int getBorderColor() {
        return borderColor;
    }
    
    /**
     * Встановлює колір рамки.
     */
    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }
    
    /**
     * Отримує ширину рамки.
     */
    public int getBorderWidth() {
        return borderWidth;
    }
    
    /**
     * Встановлює ширину рамки.
     */
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
}