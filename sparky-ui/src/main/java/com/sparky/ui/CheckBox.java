package com.sparky.ui;

import java.util.function.Consumer;

/**
 * Елемент прапорця для вибору так/ні.
 *
 * @author Андрій Будильников
 */
public class CheckBox implements UIElement {
    private final String id;
    private boolean visible = true;
    private int x, y;
    private int width, height;
    private boolean checked = false;
    private String text = "";
    private Consumer<Boolean> onCheckHandler;
    
    public CheckBox(String id) {
        this.id = id;
        this.width = 20;  // Default width for checkbox
        this.height = 20; // Default height for checkbox
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
     * Перевіряє, чи прапорець встановлено.
     */
    public boolean isChecked() {
        return checked;
    }
    
    /**
     * Встановлює стан прапорця.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
        if (onCheckHandler != null) {
            onCheckHandler.accept(checked);
        }
    }
    
    /**
     * Перемикає стан прапорця.
     */
    public void toggle() {
        setChecked(!checked);
    }
    
    /**
     * Отримує текст прапорця.
     */
    public String getText() {
        return text;
    }
    
    /**
     * Встановлює текст прапорця.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * Встановлює обробник зміни стану.
     */
    public void setOnCheck(Consumer<Boolean> handler) {
        this.onCheckHandler = handler;
    }
}