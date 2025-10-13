package com.sparky.example.bukkit;

/**
 * Приклад події для демонстрації системи подій.
 *
 * @author Андрій Будильников
 */
public class ExampleEvent {
    private final String message;
    
    public ExampleEvent(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}