package com.sparky.commands;

import java.util.List;

/**
 * Інтерфейс для команди.
 *
 * @author Андрій Будильников
 */
public interface Command {
    /**
     * Отримує назву команди.
     */
    String getName();
    
    /**
     * Отримує опис команди.
     */
    String getDescription();
    
    /**
     * Отримує використання команди.
     */
    String getUsage();
    
    /**
     * Виконує команду.
     *
     * @param sender хто викликає команду
     * @param args аргументи команди
     */
    void execute(CommandSender sender, List<String> args);
}