package com.sparky.ui;

import java.util.HashMap;
import java.util.Map;

/**
 * Реалізація менеджера компонування з граничним розташуванням.
 *
 * @author Андрій Будильников
 */
public class BorderLayout implements LayoutManager {
    public static final String NORTH = "NORTH";
    public static final String SOUTH = "SOUTH";
    public static final String EAST = "EAST";
    public static final String WEST = "WEST";
    public static final String CENTER = "CENTER";
    
    private int hgap = 5;  // Horizontal gap between components
    private int vgap = 5;  // Vertical gap between components
    private Map<String, UIElement> constraints = new HashMap<>();
    
    public BorderLayout() {}
    
    public BorderLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }
    
    @Override
    public void layout(Container container) {
        int containerX = container.getX();
        int containerY = container.getY();
        int containerWidth = container.getWidth();
        int containerHeight = container.getHeight();
        
        UIElement north = constraints.get(NORTH);
        UIElement south = constraints.get(SOUTH);
        UIElement east = constraints.get(EAST);
        UIElement west = constraints.get(WEST);
        UIElement center = constraints.get(CENTER);
        
        int northHeight = 0;
        int southHeight = 0;
        int eastWidth = 0;
        int westWidth = 0;
        
        // Layout NORTH component
        if (north != null && north.isVisible()) {
            northHeight = north.getHeight();
            north.setPosition(containerX + hgap, containerY + vgap);
            north.setSize(containerWidth - 2 * hgap, northHeight);
        }
        
        // Layout SOUTH component
        if (south != null && south.isVisible()) {
            southHeight = south.getHeight();
            south.setPosition(containerX + hgap, containerY + containerHeight - southHeight - vgap);
            south.setSize(containerWidth - 2 * hgap, southHeight);
        }
        
        // Layout WEST component
        if (west != null && west.isVisible()) {
            westWidth = west.getWidth();
            west.setPosition(containerX + hgap, containerY + northHeight + vgap);
            west.setSize(westWidth, containerHeight - northHeight - southHeight - 2 * vgap);
        }
        
        // Layout EAST component
        if (east != null && east.isVisible()) {
            eastWidth = east.getWidth();
            east.setPosition(containerX + containerWidth - eastWidth - hgap, containerY + northHeight + vgap);
            east.setSize(eastWidth, containerHeight - northHeight - southHeight - 2 * vgap);
        }
        
        // Layout CENTER component
        if (center != null && center.isVisible()) {
            int centerX = containerX + westWidth + hgap;
            int centerY = containerY + northHeight + vgap;
            int centerWidth = containerWidth - westWidth - eastWidth - 2 * hgap;
            int centerHeight = containerHeight - northHeight - southHeight - 2 * vgap;
            
            center.setPosition(centerX, centerY);
            center.setSize(centerWidth, centerHeight);
        }
    }
    
    /**
     * Додає компонент з відповідним обмеженням.
     */
    public void addComponent(UIElement component, String constraint) {
        constraints.put(constraint, component);
    }
    
    /**
     * Видаляє компонент.
     */
    public void removeComponent(UIElement component) {
        constraints.values().removeIf(element -> element == component);
    }
    
    /**
     * Очищує всі компоненти.
     */
    public void clearComponents() {
        constraints.clear();
    }
    
    /**
     * Отримує горизонтальний інтервал.
     */
    public int getHgap() {
        return hgap;
    }
    
    /**
     * Встановлює горизонтальний інтервал.
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }
    
    /**
     * Отримує вертикальний інтервал.
     */
    public int getVgap() {
        return vgap;
    }
    
    /**
     * Встановлює вертикальний інтервал.
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }
}