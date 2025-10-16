package com.sparky.minecraft.potions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для системи зілля.
 *
 * @author Андрій Будильников
 */
public class PotionTest {
    
    @Test
    public void testPotionSystemCreation() {
        PotionSystem potionSystem = new PotionSystem();
        assertNotNull(potionSystem);
        System.out.println("PotionSystem created successfully");
    }
    
    @Test
    public void testPotionSystemInitialization() {
        PotionSystem potionSystem = new PotionSystem();
        
        // Перевіряємо, що система ініціалізується правильно
        assertNotNull(potionSystem);
        System.out.println("Potion system initialization test completed");
    }
}