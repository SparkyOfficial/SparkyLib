package com.sparky.ui;

import java.util.function.Consumer;

/**
 * Елемент кнопки.
 *
 * @author Андрій Будильников
 */
public class Button implements UIElement {
    private final String id;
    private boolean visible = true;
    private int x, y;
    private int width, height;
    private String text = "";
    private Consumer<Button> onClickHandler;
    
    public Button(String id) {
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
     * Отримує текст кнопки.
     */
    public String getText() {
        return text;
    }
    
    /**
     * Встановлює текст кнопки.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * Встановлює обробник натискання.
     */
    public void setOnClick(Consumer<Button> handler) {
        this.onClickHandler = handler;
    }
    
    /**
     * Викликає обробник натискання.
     */
    public void click() {
        if (onClickHandler != null) {
            onClickHandler.accept(this);
        }
    }
}