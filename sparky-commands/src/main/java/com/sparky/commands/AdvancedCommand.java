package com.sparky.commands;

import java.util.List;
import java.util.Set;

/**
 * Розширений інтерфейс для команди з підтримкою дозволів.
 *
 * @author Богдан Кравчук
 */
public interface AdvancedCommand extends Command {
    /**
     * Отримує набір дозволів, необхідних для виконання команди.
     */
    Set<Permission> getPermissions();
    
    /**
     * Отримує список псевдонімів команди.
     */
    List<String> getAliases();
    
    /**
     * Перевіряє, чи команда доступна лише для гравців.
     */
    boolean isPlayerOnly();
    
    /**
     * Отримує мінімальну кількість аргументів.
     */
    int getMinArgs();
    
    /**
     * Отримує максимальну кількість аргументів.
     */
    int getMaxArgs();
}