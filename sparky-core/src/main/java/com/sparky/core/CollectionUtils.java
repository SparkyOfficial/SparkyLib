package com.sparky.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Утилітарний клас для роботи з колекціями.
 *
 * @author Андрій Будильников
 */
public class CollectionUtils {
    
    /**
     * Повертає порожній список, якщо вхідна колекція є null.
     */
    public static <T> List<T> emptyIfNull(Collection<T> collection) {
        return collection == null ? Collections.emptyList() : collection.stream().collect(Collectors.toList());
    }
    
    /**
     * Перевіряє, чи є колекція null або порожньою.
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    /**
     * Перевіряє, чи колекція не є null і не порожньою.
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}