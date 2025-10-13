package com.sparky.datastore;

import java.util.Optional;

/**
 * Інтерфейс для зберігання даних з різними бекендами.
 *
 * @author Андрій Будильников
 */
public interface DataStore {
    /**
     * Зберігає значення за ключем.
     *
     * @param key ключ
     * @param value значення
     */
    void put(String key, Object value);
    
    /**
     * Отримує значення за ключем.
     *
     * @param key ключ
     * @param type тип значення
     * @return значення або порожній Optional, якщо ключ не знайдено
     */
    <T> Optional<T> get(String key, Class<T> type);
    
    /**
     * Видаляє значення за ключем.
     *
     * @param key ключ
     */
    void remove(String key);
    
    /**
     * Перевіряє наявність ключа.
     *
     * @param key ключ
     * @return true, якщо ключ існує
     */
    boolean contains(String key);
    
    /**
     * Закриває з'єднання зі сховищем даних.
     */
    void close();
}