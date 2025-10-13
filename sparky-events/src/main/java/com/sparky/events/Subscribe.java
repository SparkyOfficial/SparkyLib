package com.sparky.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Анотація для позначення методів-обробників подій.
 *
 * @author Андрій Будильников
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {
    /**
     * Пріоритет обробника подій (менше значення = вищий пріоритет).
     */
    int priority() default 0;
    
    /**
     * Чи обробляти подію асинхронно.
     */
    boolean async() default false;
}