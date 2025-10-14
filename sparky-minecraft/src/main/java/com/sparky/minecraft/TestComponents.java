package com.sparky.minecraft;

import com.sparky.ecs.EntityManager;
import com.sparky.ecs.Entity;
import com.sparky.minecraft.DimensionComponent.DimensionType;
import com.sparky.minecraft.BiomeComponent.BiomeType;
import com.sparky.minecraft.BiomeComponent.ClimateType;

/**
 * Тест для перевірки роботи компонентів Minecraft.
 */
public class TestComponents {
    public static void main(String[] args) {
        System.out.println("=== Тест компонентів Minecraft ===\n");
        
        // Створюємо менеджер сутностей
        EntityManager entityManager = new EntityManager();
        
        // Тестуємо виміри
        testDimensions(entityManager);
        
        // Тестуємо біоми
        testBiomes(entityManager);
        
        System.out.println("=== Тест завершено ===");
    }
    
    private static void testDimensions(EntityManager entityManager) {
        System.out.println("--- Тест вимірів ---");
        
        // Створюємо сутність виміру
        Entity dimensionEntity = entityManager.createEntity();
        
        // Створюємо компонент виміру
        DimensionComponent dimension = new DimensionComponent(
            DimensionType.OVERWORLD, 
            "overworld", 
            12345L, 
            true, 
            -64, 
            320, 
            "default"
        );
        
        // Додаємо компонент до сутності
        entityManager.addComponentToEntity(dimensionEntity, dimension);
        
        // Перевіряємо, що компонент додано
        if (dimensionEntity.hasComponent(DimensionComponent.class)) {
            DimensionComponent retrievedDimension = dimensionEntity.getComponent(DimensionComponent.class);
            System.out.println("Вимір створено успішно:");
            System.out.println("  Назва: " + retrievedDimension.getDimensionName());
            System.out.println("  Тип: " + retrievedDimension.getDimensionType());
            System.out.println("  Сід: " + retrievedDimension.getSeed());
            System.out.println("  Висота: " + retrievedDimension.getHeight());
            System.out.println("  В межах (64): " + retrievedDimension.isWithinBounds(64));
            System.out.println("  В межах (-100): " + retrievedDimension.isWithinBounds(-100));
        } else {
            System.out.println("Помилка: не вдалося додати компонент виміру");
        }
        
        System.out.println();
    }
    
    private static void testBiomes(EntityManager entityManager) {
        System.out.println("--- Тест біомів ---");
        
        // Створюємо сутність біому
        Entity biomeEntity = entityManager.createEntity();
        
        // Створюємо компонент біому
        BiomeComponent biome = new BiomeComponent(
            "forest", 
            BiomeType.FOREST, 
            ClimateType.TEMPERATE, 
            0.7f, 
            0.8f, 
            0x3F76E4, 
            0x71A047, 
            0x71A047, 
            true, 
            false
        );
        
        // Додаємо особливості та спавни
        biome.addFeature("villages");
        biome.addFeature("woodland_mansions");
        biome.addMobSpawn("wolf", 5);
        biome.addMobSpawn("cow", 8);
        
        // Додаємо компонент до сутності
        entityManager.addComponentToEntity(biomeEntity, biome);
        
        // Перевіряємо, що компонент додано
        if (biomeEntity.hasComponent(BiomeComponent.class)) {
            BiomeComponent retrievedBiome = biomeEntity.getComponent(BiomeComponent.class);
            System.out.println("Біом створено успішно:");
            System.out.println("  Назва: " + retrievedBiome.getBiomeName());
            System.out.println("  Тип: " + retrievedBiome.getBiomeType());
            System.out.println("  Температура: " + retrievedBiome.getTemperature());
            System.out.println("  Опади: " + retrievedBiome.getRainfall());
            System.out.println("  Рівень рослинності: " + retrievedBiome.getVegetationLevel());
            System.out.println("  Особливості: " + retrievedBiome.getFeatures());
            System.out.println("  Спавни мобів: " + retrievedBiome.getMobSpawns());
        } else {
            System.out.println("Помилка: не вдалося додати компонент біому");
        }
        
        System.out.println();
    }
}