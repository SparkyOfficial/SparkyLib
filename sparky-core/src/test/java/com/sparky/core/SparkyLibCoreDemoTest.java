package com.sparky.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Демонстраційний тест для SparkyLib core module.
 *
 * @author Андрій Будильников
 */
class SparkyLibCoreDemoTest {
    
    @Test
    void testBasicFunctionality() {
        // Test DIContainer
        DIContainer diContainer = DIContainer.getInstance();
        diContainer.registerSingleton(String.class, "testString");
        String retrieved = diContainer.get(String.class);
        assertEquals("testString", retrieved);
        
        // Test CollectionUtils
        assertTrue(CollectionUtils.isEmpty(null));
        assertFalse(CollectionUtils.isNotEmpty(null));
        
        // Test SerializationUtils
        HashMap<String, Object> testData = new HashMap<>();
        testData.put("key1", "value1");
        testData.put("key2", 42);
        
        try {
            String json = SerializationUtils.toJson(testData);
            assertNotNull(json);
            
            @SuppressWarnings("unchecked")
            HashMap<String, Object> deserialized = SerializationUtils.fromJson(json, HashMap.class);
            assertNotNull(deserialized);
            assertEquals("value1", deserialized.get("key1"));
        } catch (Exception e) {
            fail("Serialization failed: " + e.getMessage());
        }
        
        System.out.println("SparkyLib core functionality test passed!");
    }
}