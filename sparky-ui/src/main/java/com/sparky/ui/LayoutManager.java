package com.sparky.ui;

/**
 * Інтерфейс для менеджера компонування UI елементів.
 *
 * @author Андрій Будильников
 */
public interface LayoutManager {
    /**
     * Компонує дочірні елементи контейнера.
     *
     * @param container контейнер для компонування
     */
    void layout(Container container);
}