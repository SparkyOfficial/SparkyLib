package com.sparky.minecraft.scripting;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.Map;
import java.util.HashMap;

/**
 * Двигун скриптів для розширюваності Minecraft.
 *
 * @author Андрій Будильников
 */
public class ScriptingEngine {
    private ScriptEngine scriptEngine;
    private Map<String, Object> globalVariables;
    
    public ScriptingEngine() {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            this.scriptEngine = manager.getEngineByName("javascript");
            if (this.scriptEngine == null) {
                System.out.println("JavaScript engine not found, trying Nashorn...");
                this.scriptEngine = manager.getEngineByName("nashorn");
            }
            if (this.scriptEngine == null) {
                System.out.println("No JavaScript engine available");
            }
        } catch (Exception e) {
            System.out.println("Error initializing script engine: " + e.getMessage());
            this.scriptEngine = null;
        }
        this.globalVariables = new HashMap<>();
        
        // ініціалізуємо глобальні змінні
        initializeGlobalVariables();
    }
    
    // ініціалізує глобальні змінні
    private void initializeGlobalVariables() {
        // тут можна додати глобальні об'єкти для доступу до API
        globalVariables.put("console", new ScriptConsole());
        globalVariables.put("math", new ScriptMath());
    }
    
    // виконує скрипт з рядка
    public Object executeScript(String script) throws ScriptException {
        // перевіряємо, чи доступний двигун скриптів
        if (scriptEngine == null) {
            System.err.println("JavaScript engine not available");
            return null;
        }
        
        // встановлюємо глобальні змінні
        for (Map.Entry<String, Object> entry : globalVariables.entrySet()) {
            if (scriptEngine != null) {
                scriptEngine.put(entry.getKey(), entry.getValue());
            }
        }
        
        // виконуємо скрипт
        return scriptEngine.eval(script);
    }
    
    // виконує скрипт з файлу
    public Object executeScriptFile(String filePath) throws ScriptException {
        // перевіряємо, чи доступний двигун скриптів
        if (scriptEngine == null) {
            System.err.println("JavaScript engine not available");
            return null;
        }
        
        // тут була б логіка завантаження файлу
        // для прикладу просто повертаємо null
        return null;
    }
    
    // додає глобальну змінну
    public void setGlobalVariable(String name, Object value) {
        globalVariables.put(name, value);
        if (scriptEngine != null) {
            scriptEngine.put(name, value);
        }
    }
    
    // отримує глобальну змінну
    public Object getGlobalVariable(String name) {
        return globalVariables.get(name);
    }
    
    // перевіряє, чи доступний двигун скриптів
    public boolean isScriptEngineAvailable() {
        return scriptEngine != null;
    }
    
    // внутрішній клас для консолі в скриптах
    public static class ScriptConsole {
        public void log(String message) {
            System.out.println("[Script] " + message);
        }
        
        public void warn(String message) {
            System.out.println("[Script Warning] " + message);
        }
        
        public void error(String message) {
            System.err.println("[Script Error] " + message);
        }
    }
    
    // внутрішній клас для математичних функцій в скриптах
    public static class ScriptMath {
        public double random() {
            return Math.random();
        }
        
        public double sin(double value) {
            return Math.sin(value);
        }
        
        public double cos(double value) {
            return Math.cos(value);
        }
        
        public double sqrt(double value) {
            return Math.sqrt(value);
        }
    }
}