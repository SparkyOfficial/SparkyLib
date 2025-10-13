package com.sparky.events;

import java.lang.reflect.Method;

/**
 * Представляє обробник події.
 *
 * @author Андрій Будильников
 */
public record EventHandler(Object listener, Method method, int priority, boolean async) {
}