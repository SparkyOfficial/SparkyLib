package com.sparky.ui;

import java.util.function.Consumer;

/**
 * Елемент текстового поля для введення тексту.
 *
 * @author Андрій Будильников
 */
public class TextField implements UIElement {
    private final String id;
    private boolean visible = true;
    private int x, y;
    private int width, height;
    private String text = "";
    private String placeholder = "";
    private boolean focused = false;
    private int maxLength = 100;
    private Consumer<String> onTextChangeHandler;
    private Runnable onFocusHandler;
    private Runnable onBlurHandler;
    
    public TextField(String id) {
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
     * Отримує текст поля.
     */
    public String getText() {
        return text;
    }
    
    /**
     * Встановлює текст поля.
     */
    public void setText(String text) {
        this.text = text;
        if (onTextChangeHandler != null) {
            onTextChangeHandler.accept(text);
        }
    }
    
    /**
     * Отримує текст заповнювача.
     */
    public String getPlaceholder() {
        return placeholder;
    }
    
    /**
     * Встановлює текст заповнювача.
     */
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
    
    /**
     * Перевіряє, чи поле має фокус.
     */
    public boolean isFocused() {
        return focused;
    }
    
    /**
     * Встановлює фокус на поле.
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
        if (focused && onFocusHandler != null) {
            onFocusHandler.run();
        } else if (!focused && onBlurHandler != null) {
            onBlurHandler.run();
        }
    }
    
    /**
     * Отримує максимальну довжину тексту.
     */
    public int getMaxLength() {
        return maxLength;
    }
    
    /**
     * Встановлює максимальну довжину тексту.
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    
    /**
     * Встановлює обробник зміни тексту.
     */
    public void setOnTextChange(Consumer<String> handler) {
        this.onTextChangeHandler = handler;
    }
    
    /**
     * Встановлює обробник отримання фокусу.
     */
    public void setOnFocus(Runnable handler) {
        this.onFocusHandler = handler;
    }
    
    /**
     * Встановлює обробник втрати фокусу.
     */
    public void setOnBlur(Runnable handler) {
        this.onBlurHandler = handler;
    }
    
    /**
     * Додає символ до тексту.
     */
    public void addCharacter(char c) {
        if (text.length() < maxLength) {
            text += c;
            if (onTextChangeHandler != null) {
                onTextChangeHandler.accept(text);
            }
        }
    }
    
    /**
     * Видаляє останній символ з тексту.
     */
    public void backspace() {
        if (!text.isEmpty()) {
            text = text.substring(0, text.length() - 1);
            if (onTextChangeHandler != null) {
                onTextChangeHandler.accept(text);
            }
        }
    }
}