package com.sparky.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Простий контейнер для впровадження залежностей.
 *
 * @author Андрій Будильников
 */
public class DIContainer {
    private static final DIContainer INSTANCE = new DIContainer();
    
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Supplier<?>> factories = new HashMap<>();
    
    private DIContainer() {}
    
    public static DIContainer getInstance() {
        return INSTANCE;
    }
    
    public <T> void registerSingleton(Class<T> type, T instance) {
        singletons.put(type, instance);
    }
    
    public <T> void registerFactory(Class<T> type, Supplier<T> factory) {
        factories.put(type, factory);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        // First check for singleton
        T singleton = (T) singletons.get(type);
        if (singleton != null) {
            return singleton;
        }
        
        // Then check for factory
        Supplier<T> factory = (Supplier<T>) factories.get(type);
        if (factory != null) {
            return factory.get();
        }
        
        // If not found, try to instantiate directly
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new CoreException("Failed to instantiate " + type.getName(), e);
        }
    }
    
    public void clear() {
        singletons.clear();
        factories.clear();
    }
}