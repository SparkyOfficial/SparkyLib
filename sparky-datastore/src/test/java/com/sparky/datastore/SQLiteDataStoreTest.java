package com.sparky.datastore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тест для SQLiteDataStore.
 *
 * @author Богдан Кравчук
 */
class SQLiteDataStoreTest {
    private SQLiteDataStore dataStore;
    private File tempDbFile;
    
    @BeforeEach
    void setUp() throws IOException {
        tempDbFile = Files.createTempFile("test", ".db").toFile();
        dataStore = new SQLiteDataStore(tempDbFile);
    }
    
    @AfterEach
    void tearDown() {
        if (dataStore != null) {
            dataStore.close();
        }
        if (tempDbFile != null && tempDbFile.exists()) {
            tempDbFile.delete();
        }
    }
    
    @Test
    void testPutAndGet() {
        // Test string value
        dataStore.put("testKey", "testValue");
        Optional<String> result = dataStore.get("testKey", String.class);
        assertTrue(result.isPresent());
        assertEquals("testValue", result.get());
        
        // Test integer value
        dataStore.put("intKey", 42);
        Optional<Integer> intResult = dataStore.get("intKey", Integer.class);
        assertTrue(intResult.isPresent());
        assertEquals(42, intResult.get());
        
        // Test boolean value
        dataStore.put("boolKey", true);
        Optional<Boolean> boolResult = dataStore.get("boolKey", Boolean.class);
        assertTrue(boolResult.isPresent());
        assertTrue(boolResult.get());
    }
    
    @Test
    void testRemove() {
        dataStore.put("keyToRemove", "value");
        assertTrue(dataStore.contains("keyToRemove"));
        
        dataStore.remove("keyToRemove");
        assertFalse(dataStore.contains("keyToRemove"));
        
        Optional<String> result = dataStore.get("keyToRemove", String.class);
        assertFalse(result.isPresent());
    }
    
    @Test
    void testContains() {
        assertFalse(dataStore.contains("nonExistentKey"));
        
        dataStore.put("existentKey", "value");
        assertTrue(dataStore.contains("existentKey"));
    }
    
    @Test
    void testUpdateValue() {
        dataStore.put("key", "initialValue");
        Optional<String> result = dataStore.get("key", String.class);
        assertTrue(result.isPresent());
        assertEquals("initialValue", result.get());
        
        dataStore.put("key", "updatedValue");
        result = dataStore.get("key", String.class);
        assertTrue(result.isPresent());
        assertEquals("updatedValue", result.get());
    }
}