package com.sparky.commands;

/**
 * Інтерфейс для того, хто викликає команду.
 *
 * @author Андрій Будильников
 */
public interface CommandSender {
    /**
     * Надсилає повідомлення відправнику.
     */
    void sendMessage(String message);
    
    /**
     * Отримує назву відправника.
     */
    String getName();
    
    /**
     * Перевіряє, чи має відправник певний дозвіл.
     */
    boolean hasPermission(String permission);
}