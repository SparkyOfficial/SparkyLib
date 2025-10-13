package com.sparky.core;

/**
 * Базовий клас винятків для SparkyLib.
 *
 * @author Андрій Будильников
 */
public class CoreException extends RuntimeException {
    
    public CoreException(String message) {
        super(message);
    }
    
    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public CoreException(Throwable cause) {
        super(cause);
    }
}