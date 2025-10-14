package com.sparky.minecraft;

import com.sparky.ecs.EntityManager;
import com.sparky.ecs.Entity;
import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.BiomeComponent.BiomeType;
import com.sparky.minecraft.BiomeComponent.ClimateType;
import com.sparky.minecraft.DimensionComponent.DimensionType;
import com.sparky.minecraft.PotionEffectComponent.PotionEffectType;
import com.sparky.minecraft.PotionEffectComponent.EffectParticles;

/**
 * Демонстрація розширених можливостей бібліотеки Sparky Minecraft.
 * Цей клас демонструє використання нових компонентів та систем.
 *
 * @author Богдан Кравчук
 */
public class MinecraftAdvancedDemo {
    private EntityManager entityManager;
    private DimensionSystem dimensionSystem;
    private BiomeSystem biomeSystem;
    private PotionEffectSystem potionEffectSystem;
    
    public MinecraftAdvancedDemo() {
        entityManager = new EntityManager();
        dimensionSystem = new DimensionSystem(entityManager);
        biomeSystem = new BiomeSystem(entityManager);
        potionEffectSystem = new PotionEffectSystem(entityManager);
        
        dimensionSystem.setEntityManager(entityManager);
        biomeSystem.setEntityManager(entityManager);
        potionEffectSystem.setEntityManager(entityManager);
    }
    
    public void runDemo() {
        System.out.println("=== Демонстрація розширених можливостей Sparky Minecraft ===\n");
        
        // Демонстрація роботи з вимірами
        demonstrateDimensions();
        
        // Демонстрація роботи з біомами
        demonstrateBiomes();
        
        // Демонстрація роботи з ефектами зілля
        demonstratePotionEffects();
        
        System.out.println("\n=== Демонстрація завершена ===");
    }
    
    private void demonstrateDimensions() {
        System.out.println("--- Робота з вимірами ---");
        
        // Створюємо різні виміри
        Entity overworld = dimensionSystem.createDimension(
            DimensionType.OVERWORLD, "overworld", 12345L, "default");
        Entity nether = dimensionSystem.createDimension(
            DimensionType.NETHER, "nether", 12345L, "default");
        Entity end = dimensionSystem.createDimension(
            DimensionType.END, "the_end", 12345L, "default");
        
        System.out.println("Створено виміри:");
        System.out.println("- Overworld: " + (overworld != null ? "Успішно" : "Помилка"));
        System.out.println("- Nether: " + (nether != null ? "Успішно" : "Помилка"));
        System.out.println("- The End: " + (end != null ? "Успішно" : "Помилка"));
        
        // Отримуємо вимір за ім'ям
        Entity foundDimension = dimensionSystem.getDimension("overworld");
        if (foundDimension != null && foundDimension.hasComponent(DimensionComponent.class)) {
            DimensionComponent dimension = foundDimension.getComponent(DimensionComponent.class);
            System.out.println("Знайдено вимір: " + dimension.getDimensionName() + 
                             " (Тип: " + dimension.getDimensionType() + ")");
        }
        
        // Перевіряємо межі виміру
        boolean inBounds = dimensionSystem.isPositionInBounds("overworld", 64);
        System.out.println("Позиція Y=64 в межах Overworld: " + inBounds);
        
        System.out.println("Загальна кількість вимірів: " + dimensionSystem.getDimensionCount());
        System.out.println();
    }
    
    private void demonstrateBiomes() {
        System.out.println("--- Робота з біомами ---");
        
        // Створюємо різні біоми
        Entity plains = biomeSystem.createBiome(
            "plains", BiomeType.PLAINS, ClimateType.TEMPERATE, 0.8f, 0.4f);
        Entity desert = biomeSystem.createBiome(
            "desert", BiomeType.DESERT, ClimateType.DESERT, 2.0f, 0.0f);
        Entity forest = biomeSystem.createBiome(
            "forest", BiomeType.FOREST, ClimateType.TEMPERATE, 0.7f, 0.8f);
        Entity snowyTundra = biomeSystem.createBiome(
            "snowy_tundra", BiomeType.SNOWY_TUNDRA, ClimateType.COLD, -0.5f, 0.5f);
        
        System.out.println("Створено біоми:");
        System.out.println("- Plains: " + (plains != null ? "Успішно" : "Помилка"));
        System.out.println("- Desert: " + (desert != null ? "Успішно" : "Помилка"));
        System.out.println("- Forest: " + (forest != null ? "Успішно" : "Помилка"));
        System.out.println("- Snowy Tundra: " + (snowyTundra != null ? "Успішно" : "Помилка"));
        
        // Додаємо особливості до біомів
        biomeSystem.addFeatureToBiome("plains", "villages");
        biomeSystem.addFeatureToBiome("plains", "pillager_outposts");
        biomeSystem.addFeatureToBiome("desert", "desert_pyramids");
        biomeSystem.addFeatureToBiome("desert", "villages");
        biomeSystem.addFeatureToBiome("forest", "woodland_mansions");
        
        // Додаємо спавни мобів
        biomeSystem.addMobSpawnToBiome("plains", "cow", 8);
        biomeSystem.addMobSpawnToBiome("plains", "pig", 10);
        biomeSystem.addMobSpawnToBiome("plains", "sheep", 12);
        biomeSystem.addMobSpawnToBiome("desert", "rabbit", 4);
        biomeSystem.addMobSpawnToBiome("desert", "husk", 8);
        biomeSystem.addMobSpawnToBiome("forest", "wolf", 5);
        
        // Отримуємо біом за ім'ям
        Entity foundBiome = biomeSystem.getBiome("forest");
        if (foundBiome != null && foundBiome.hasComponent(BiomeComponent.class)) {
            BiomeComponent biome = foundBiome.getComponent(BiomeComponent.class);
            System.out.println("Знайдено біом: " + biome.getBiomeName() + 
                             " (Тип: " + biome.getBiomeType() + 
                             ", Температура: " + biome.getTemperature() + 
                             ", Опади: " + biome.getRainfall() + ")");
            
            System.out.println("  Особливості: " + biome.getFeatures());
            System.out.println("  Спавни мобів: " + biome.getMobSpawns());
        }
        
        // Отримуємо біоми за кліматом
        java.util.List<Entity> temperateBiomes = biomeSystem.getBiomesByClimate(ClimateType.TEMPERATE);
        System.out.println("Біоми з помірним кліматом: " + temperateBiomes.size());
        
        System.out.println("Загальна кількість біомів: " + biomeSystem.getBiomeCount());
        System.out.println();
    }
    
    private void demonstratePotionEffects() {
        System.out.println("--- Робота з ефектами зілля ---");
        
        // Створюємо сутність для тестування ефектів
        Entity testEntity = entityManager.createEntity();
        
        // Створюємо різні ефекти зілля
        PotionEffectComponent speedEffect = potionEffectSystem.createPotionEffect(
            PotionEffectType.SPEED, 1, 600); // Ефект швидкості II на 30 секунд
        PotionEffectComponent strengthEffect = potionEffectSystem.createPotionEffect(
            PotionEffectType.STRENGTH, 0, 1200); // Ефект сили I на 60 секунд
        PotionEffectComponent regenerationEffect = potionEffectSystem.createPotionEffect(
            PotionEffectType.REGENERATION, 1, 300); // Ефект регенерації II на 15 секунд
        
        System.out.println("Створено ефекти зілля:");
        System.out.println("- Speed II: " + (speedEffect != null ? "Успішно" : "Помилка"));
        System.out.println("- Strength I: " + (strengthEffect != null ? "Успішно" : "Помилка"));
        System.out.println("- Regeneration II: " + (regenerationEffect != null ? "Успішно" : "Помилка"));
        
        // Додаємо ефекти до сутності
        boolean speedAdded = potionEffectSystem.addEffectToEntity(testEntity.getId(), speedEffect);
        boolean strengthAdded = potionEffectSystem.addEffectToEntity(testEntity.getId(), strengthEffect);
        boolean regenerationAdded = potionEffectSystem.addEffectToEntity(testEntity.getId(), regenerationEffect);
        
        System.out.println("Ефекти додано до сутності:");
        System.out.println("- Speed: " + speedAdded);
        System.out.println("- Strength: " + strengthAdded);
        System.out.println("- Regeneration: " + regenerationAdded);
        
        // Перевіряємо наявність ефектів
        boolean hasSpeed = potionEffectSystem.entityHasEffect(testEntity.getId(), PotionEffectType.SPEED);
        boolean hasStrength = potionEffectSystem.entityHasEffect(testEntity.getId(), PotionEffectType.STRENGTH);
        boolean hasRegeneration = potionEffectSystem.entityHasEffect(testEntity.getId(), PotionEffectType.REGENERATION);
        
        System.out.println("Сутність має ефекти:");
        System.out.println("- Speed: " + hasSpeed);
        System.out.println("- Strength: " + hasStrength);
        System.out.println("- Regeneration: " + hasRegeneration);
        
        // Отримуємо всі ефекти сутності
        java.util.List<PotionEffectComponent> entityEffects = potionEffectSystem.getEntityEffects(testEntity.getId());
        System.out.println("Загальна кількість ефектів у сутності: " + entityEffects.size());
        
        // Отримуємо конкретний ефект
        PotionEffectComponent effect = potionEffectSystem.getEntityEffect(testEntity.getId(), PotionEffectType.SPEED);
        if (effect != null) {
            System.out.println("Ефект швидкості:");
            System.out.println("  Посилення: " + effect.getAmplifier() + " (" + effect.getAmplifierRoman() + ")");
            System.out.println("  Тривалість: " + effect.getDuration() + " тіків");
            System.out.println("  Колір: #" + Integer.toHexString(effect.getColor()));
            System.out.println("  Позитивний: " + effect.isBeneficial());
        }
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        MinecraftAdvancedDemo demo = new MinecraftAdvancedDemo();
        demo.runDemo();
    }
}