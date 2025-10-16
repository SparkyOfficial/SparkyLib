package com.sparky.minecraft.crafting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для системи крафту.
 *
 * @author Андрій Будильников
 */
public class CraftingTest {
    
    @Test
    public void testAdvancedCraftingSystemCreation() {
        AdvancedCraftingSystem craftingSystem = new AdvancedCraftingSystem();
        assertNotNull(craftingSystem);
        System.out.println("AdvancedCraftingSystem created successfully");
    }
    
    @Test
    public void testCraftingSystemInitialization() {
        AdvancedCraftingSystem craftingSystem = new AdvancedCraftingSystem();
        
        // Перевіряємо, що система ініціалізується правильно
        assertNotNull(craftingSystem);
        System.out.println("Crafting system initialization test completed");
    }
}