package com.sparky.core;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Адаптер логування для SparkyLib.
 *
 * @author Андрій Будильников
 */
public class SparkyLogger {
    private final Logger logger;
    
    private SparkyLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }
    
    public static SparkyLogger getLogger(Class<?> clazz) {
        return new SparkyLogger(clazz);
    }
    
    public void info(String message) {
        logger.info(message);
    }
    
    public void warn(String message) {
        logger.warn(message);
    }
    
    public void error(String message) {
        logger.error(message);
    }
    
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    public void debug(String message) {
        logger.debug(message);
    }
}