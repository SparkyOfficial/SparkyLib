package com.sparky.ui;

/**
 * Елемент мітки для відображення тексту.
 *
 * @author Андрій Будильников
 */
public class Label implements UIElement {
    private final String id;
    private boolean visible = true;
    private int x, y;
    private int width, height;
    private String text = "";
    private String font = "default";
    private int fontSize = 12;
    private int color = 0xFFFFFFFF; // White color by default
    
    public Label(String id) {
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
     * Отримує текст мітки.
     */
    public String getText() {
        return text;
    }
    
    /**
     * Встановлює текст мітки.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * Отримує шрифт мітки.
     */
    public String getFont() {
        return font;
    }
    
    /**
     * Встановлює шрифт мітки.
     */
    public void setFont(String font) {
        this.font = font;
    }
    
    /**
     * Отримує розмір шрифту.
     */
    public int getFontSize() {
        return fontSize;
    }
    
    /**
     * Встановлює розмір шрифту.
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    /**
     * Отримує колір тексту.
     */
    public int getColor() {
        return color;
    }
    
    /**
     * Встановлює колір тексту.
     */
    public void setColor(int color) {
        this.color = color;
    }
}