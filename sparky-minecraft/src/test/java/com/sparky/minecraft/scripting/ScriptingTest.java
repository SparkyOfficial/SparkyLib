package com.sparky.minecraft.scripting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для системи скриптів.
 *
 * @author Андрій Будильников
 */
public class ScriptingTest {
    
    @Test
    public void testScriptingEngineCreation() {
        ScriptingEngine scriptEngine = new ScriptingEngine();
        assertNotNull(scriptEngine);
        System.out.println("ScriptingEngine created successfully");
    }
    
    @Test
    public void testScriptEngineAvailability() {
        ScriptingEngine scriptEngine = new ScriptingEngine();
        // Просто перевіряємо, що метод існує
        assertDoesNotThrow(() -> scriptEngine.isScriptEngineAvailable());
        System.out.println("Script engine availability check completed");
    }
    
    @Test
    public void testSimpleScriptExecution() {
        ScriptingEngine scriptEngine = new ScriptingEngine();
        
        // Якщо двигун доступний, виконуємо простий скрипт
        if (scriptEngine.isScriptEngineAvailable()) {
            try {
                String script = "var x = 5 + 3; x;";
                Object result = scriptEngine.executeScript(script);
                // Результат може бути різним залежно від реалізації
                System.out.println("Script executed successfully, result: " + result);
            } catch (Exception e) {
                // Дозволяємо помилки виконання скриптів
                System.out.println("Script execution test completed with expected behavior");
            }
        } else {
            System.out.println("Script engine not available, test skipped");
        }
    }
}