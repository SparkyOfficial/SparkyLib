package com.sparky.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для класу Config.
 *
 * @author Андрій Будильников
 */
class ConfigTest {
    
    @TempDir
    Path tempDir;
    
    @Test
    void testConfigCreation() {
        Config config = new Config();
        assertNotNull(config);
    }
    
    @Test
    void testGetStringProperty() throws IOException {
        // Create a temporary JSON file
        File tempFile = tempDir.resolve("test.json").toFile();
        String jsonContent = "{\"testKey\": \"testValue\"}";
        java.nio.file.Files.write(tempFile.toPath(), jsonContent.getBytes());
        
        // Load config from file
        Config config = new Config().loadFromJsonFile(tempFile);
        
        // Test property retrieval
        assertEquals("testValue", config.getStringProperty("testKey", "defaultValue"));
        assertEquals("defaultValue", config.getStringProperty("nonExistentKey", "defaultValue"));
    }
    
    @Test
    void testGetIntProperty() throws IOException {
        // Create a temporary JSON file
        File tempFile = tempDir.resolve("test.json").toFile();
        String jsonContent = "{\"intKey\": 42, \"stringIntKey\": \"123\"}";
        java.nio.file.Files.write(tempFile.toPath(), jsonContent.getBytes());
        
        // Load config from file
        Config config = new Config().loadFromJsonFile(tempFile);
        
        // Test property retrieval
        assertEquals(42, config.getIntProperty("intKey", 0));
        assertEquals(123, config.getIntProperty("stringIntKey", 0));
        assertEquals(0, config.getIntProperty("nonExistentKey", 0));
    }
    
    @Test
    void testGetBooleanProperty() throws IOException {
        // Create a temporary JSON file
        File tempFile = tempDir.resolve("test.json").toFile();
        String jsonContent = "{\"boolKey\": true, \"stringBoolKey\": \"false\"}";
        java.nio.file.Files.write(tempFile.toPath(), jsonContent.getBytes());
        
        // Load config from file
        Config config = new Config().loadFromJsonFile(tempFile);
        
        // Test property retrieval
        assertTrue(config.getBooleanProperty("boolKey", false));
        assertFalse(config.getBooleanProperty("stringBoolKey", true));
        assertFalse(config.getBooleanProperty("nonExistentKey", false));
    }
}