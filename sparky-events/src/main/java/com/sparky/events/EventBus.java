package com.sparky.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sparky.core.SparkyLogger;

/**
 * Високопродуктивна система подій з підтримкою синхронних та асинхронних слухачів.
 *
 * @author Андрій Будильников
 */
public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    
    private final SparkyLogger logger = SparkyLogger.getLogger(EventBus.class);
    private final Map<Class<?>, List<EventHandler>> handlers = new ConcurrentHashMap<>();
    private final ExecutorService asyncExecutor = Executors.newCachedThreadPool();
    
    private EventBus() {}
    
    public static EventBus getInstance() {
        return INSTANCE;
    }
    
    /**
     * Реєструє слухач подій.
     */
    public void registerListener(Object listener) {
        Class<?> listenerClass = listener.getClass();
        
        for (Method method : listenerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length != 1) {
                    logger.warn("Event handler method " + method.getName() + " must have exactly one parameter");
                    continue;
                }
                
                Class<?> eventType = paramTypes[0];
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                EventHandler handler = new EventHandler(listener, method, subscribe.priority(), subscribe.async());
                
                handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
                
                // Sort handlers by priority
                handlers.get(eventType).sort(Comparator.comparingInt(EventHandler::priority));
            }
        }
    }
    
    /**
     * Відправляє подію всім зареєстрованим слухачам.
     */
    public void post(Object event) {
        List<EventHandler> eventHandlers = handlers.get(event.getClass());
        if (eventHandlers == null || eventHandlers.isEmpty()) {
            return;
        }
        
        for (EventHandler handler : eventHandlers) {
            try {
                if (handler.async()) {
                    CompletableFuture.runAsync(() -> {
                        try {
                            handler.method().setAccessible(true);
                            handler.method().invoke(handler.listener(), event);
                        } catch (Exception e) {
                            logger.error("Error handling event asynchronously", e);
                        }
                    }, asyncExecutor);
                } else {
                    handler.method().setAccessible(true);
                    handler.method().invoke(handler.listener(), event);
                }
            } catch (Exception e) {
                logger.error("Error handling event", e);
            }
        }
    }
    
    /**
     * Видаляє всі слухачі для заданого об'єкта.
     */
    public void unregisterListener(Object listener) {
        handlers.values().forEach(list -> list.removeIf(handler -> handler.listener().equals(listener)));
    }
    
    /**
     * Зупиняє асинхронний виконавець.
     */
    public void shutdown() {
        asyncExecutor.shutdown();
    }
}