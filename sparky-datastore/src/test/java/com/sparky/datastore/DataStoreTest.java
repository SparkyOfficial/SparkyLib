package com.sparky.datastore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Демонстраційний тест для модуля зберігання даних.
 *
 * @author Богдан Кравчук
 */
class DataStoreTest {
    
    @Test
    void testDataStoreFunctionality() throws IOException {
        // Test JsonDataStore
        File jsonFile = Files.createTempFile("test", ".json").toFile();
        DataStore jsonStore = new JsonDataStore(jsonFile);
        
        jsonStore.put("testKey", "testValue");
        assertTrue(jsonStore.contains("testKey"));
        assertEquals("testValue", jsonStore.get("testKey", String.class).orElse(null));
        
        jsonStore.remove("testKey");
        assertFalse(jsonStore.contains("testKey"));
        
        jsonStore.close();
        jsonFile.delete();
        
        // Test YamlDataStore
        File yamlFile = Files.createTempFile("test", ".yaml").toFile();
        DataStore yamlStore = new YamlDataStore(yamlFile);
        
        yamlStore.put("testKey", "testValue");
        assertTrue(yamlStore.contains("testKey"));
        assertEquals("testValue", yamlStore.get("testKey", String.class).orElse(null));
        
        yamlStore.remove("testKey");
        assertFalse(yamlStore.contains("testKey"));
        
        yamlStore.close();
        yamlFile.delete();
        
        // Test SQLiteDataStore
        File sqliteFile = Files.createTempFile("test", ".db").toFile();
        DataStore sqliteStore = new SQLiteDataStore(sqliteFile);
        
        sqliteStore.put("testKey", "testValue");
        assertTrue(sqliteStore.contains("testKey"));
        assertEquals("testValue", sqliteStore.get("testKey", String.class).orElse(null));
        
        sqliteStore.remove("testKey");
        assertFalse(sqliteStore.contains("testKey"));
        
        sqliteStore.close();
        sqliteFile.delete();
        
        System.out.println("SparkyLib DataStore functionality test passed!");
    }
}