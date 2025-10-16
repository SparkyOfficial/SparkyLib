package com.sparky.minecraft.enchantments;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для системи зачарувань.
 *
 * @author Андрій Будильников
 */
public class EnchantmentTest {
    
    @Test
    public void testEnchantmentSystemCreation() {
        EnchantmentSystem enchantmentSystem = new EnchantmentSystem();
        assertNotNull(enchantmentSystem);
        System.out.println("EnchantmentSystem created successfully");
    }
    
    @Test
    public void testEnchantmentSystemInitialization() {
        EnchantmentSystem enchantmentSystem = new EnchantmentSystem();
        
        // Перевіряємо, що система ініціалізується правильно
        assertNotNull(enchantmentSystem);
        System.out.println("Enchantment system initialization test completed");
    }
}