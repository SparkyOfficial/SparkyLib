package com.sparky.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Контейнер для UI елементів.
 *
 * @author Андрій Будильников
 */
public class Container implements UIElement {
    private final String id;
    private boolean visible = true;
    private int x, y;
    private int width, height;
    private final List<UIElement> children = new ArrayList<>();
    private LayoutManager layoutManager;
    
    public Container(String id) {
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
        // Re-layout when position changes
        doLayout();
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
        // Re-layout when size changes
        doLayout();
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
     * Додає дочірній елемент.
     */
    public void addChild(UIElement child) {
        children.add(child);
        // Re-layout when children change
        doLayout();
    }
    
    /**
     * Видаляє дочірній елемент.
     */
    public void removeChild(UIElement child) {
        children.remove(child);
        // Re-layout when children change
        doLayout();
    }
    
    /**
     * Отримує всі дочірні елементи.
     */
    public List<UIElement> getChildren() {
        return new ArrayList<>(children);
    }
    
    /**
     * Встановлює менеджер компонування.
     */
    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        // Re-layout with new layout manager
        doLayout();
    }
    
    /**
     * Отримує менеджер компонування.
     */
    public LayoutManager getLayoutManager() {
        return layoutManager;
    }
    
    /**
     * Виконує компонування дочірніх елементів.
     */
    public void doLayout() {
        if (layoutManager != null) {
            layoutManager.layout(this);
        }
    }
}