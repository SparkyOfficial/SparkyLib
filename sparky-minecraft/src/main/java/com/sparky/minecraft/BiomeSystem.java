package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;
import com.sparky.ecs.EntityManager;
import com.sparky.ecs.System;
import com.sparky.minecraft.BiomeComponent.BiomeType;
import com.sparky.minecraft.BiomeComponent.ClimateType;
import com.sparky.minecraft.math.Vector3D;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * Система для управління біомами в Minecraft.
 * Відповідає за створення, завантаження та управління біомами.
 *
 * @author Богдан Кравчук
 */
public class BiomeSystem extends System {
    private EntityManager entityManager;
    private Map<String, Entity> biomes;
    private Random random;
    
    public BiomeSystem() {
        this.biomes = new HashMap<>();
        this.random = new Random();
    }
    
    public BiomeSystem(EntityManager entityManager) {
        this();
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.hasComponent(BiomeComponent.class)) {
                BiomeComponent biome = entity.getComponent(BiomeComponent.class);
                processBiomeUpdate(biome);
            }
        }
    }
    
    /**
     * Обробляє оновлення біому.
     */
    private void processBiomeUpdate(BiomeComponent biome) {
    }
    
    /**
     * Створює новий біом.
     *
     * @param biomeName ім'я біому
     * @param biomeType тип біому
     * @param climateType тип клімату
     * @param temperature температура
     * @param rainfall кількість опадів
     * @return сутність біому або null, якщо не вдалося створити
     */
    public Entity createBiome(String biomeName, BiomeType biomeType, ClimateType climateType,
                             float temperature, float rainfall) {
        if (entityManager == null) {
            return null;
        }
        
        if (biomes.containsKey(biomeName)) {
            return biomes.get(biomeName);
        }
        
        Entity biomeEntity = entityManager.createEntity();
        
        int waterColor = 0x3F76E4;
        int foliageColor = 0x71A047;
        int grassColor = 0x71A047;
        boolean hasRain = climateType != ClimateType.DESERT && climateType != ClimateType.NETHER && climateType != ClimateType.END;
        boolean hasSnow = temperature < 0.3f;
        
        switch (biomeType) {
            case DESERT:
            case DESERT_LAKES:
                waterColor = 0x32A598;
                foliageColor = 0xBBAF5C;
                grassColor = 0xBBAF5C;
                break;
            case SWAMP:
            case SWAMP_HILLS:
                waterColor = 0x4C6559;
                foliageColor = 0x6A7039;
                grassColor = 0x6A7039;
                break;
            case JUNGLE:
            case JUNGLE_HILLS:
            case JUNGLE_EDGE:
            case BAMBOO_JUNGLE:
            case BAMBOO_JUNGLE_HILLS:
                waterColor = 0x008E87;
                foliageColor = 0x59C93C;
                grassColor = 0x59C93C;
                break;
            case OCEAN:
            case DEEP_OCEAN:
            case WARM_OCEAN:
            case LUKEWARM_OCEAN:
            case COLD_OCEAN:
            case DEEP_WARM_OCEAN:
            case DEEP_LUKEWARM_OCEAN:
            case DEEP_COLD_OCEAN:
            case DEEP_FROZEN_OCEAN:
                waterColor = 0x1D6B99;
                break;
            case SNOWY_TUNDRA:
            case SNOWY_MOUNTAINS:
            case SNOWY_BEACH:
            case SNOWY_TAIGA:
            case SNOWY_TAIGA_HILLS:
            case SNOWY_TAIGA_MOUNTAINS:
            case ICE_SPIKES:
                waterColor = 0x14559B;
                foliageColor = 0x80B497;
                grassColor = 0x80B497;
                break;
        }
        
        BiomeComponent biome = new BiomeComponent(
            biomeName, biomeType, climateType, temperature, rainfall,
            waterColor, foliageColor, grassColor, hasRain, hasSnow);
        
        entityManager.addComponentToEntity(biomeEntity, biome);
        biomes.put(biomeName, biomeEntity);
        
        return biomeEntity;
    }
    
    /**
     * Отримує біом за ім'ям.
     *
     * @param biomeName ім'я біому
     * @return сутність біому або null, якщо не знайдено
     */
    public Entity getBiome(String biomeName) {
        return biomes.get(biomeName);
    }
    
    /**
     * Отримує біом за типом.
     *
     * @param biomeType тип біому
     * @return сутність біому або null, якщо не знайдено
     */
    public Entity getBiome(BiomeType biomeType) {
        for (Map.Entry<String, Entity> entry : biomes.entrySet()) {
            Entity entity = entry.getValue();
            if (entity.hasComponent(BiomeComponent.class)) {
                BiomeComponent biome = entity.getComponent(BiomeComponent.class);
                if (biome.getBiomeType() == biomeType) {
                    return entity;
                }
            }
        }
        return null;
    }
    
    /**
     * Перевіряє, чи існує біом з заданим ім'ям.
     *
     * @param biomeName ім'я біому
     * @return true, якщо біом існує
     */
    public boolean hasBiome(String biomeName) {
        return biomes.containsKey(biomeName);
    }
    
    /**
     * Видаляє біом.
     *
     * @param biomeName ім'я біому
     * @return true, якщо біом видалено успішно
     */
    public boolean removeBiome(String biomeName) {
        Entity entity = biomes.remove(biomeName);
        if (entity != null && entityManager != null) {
            entityManager.removeEntity(entity.getId());
            return true;
        }
        return false;
    }
    
    /**
     * Отримує всі біоми.
     *
     * @return мапа всіх біомів
     */
    public Map<String, Entity> getAllBiomes() {
        return new HashMap<>(biomes);
    }
    
    /**
     * Отримує кількість біомів.
     *
     * @return кількість біомів
     */
    public int getBiomeCount() {
        return biomes.size();
    }
    
    /**
     * Додає особливість до біому.
     *
     * @param biomeName ім'я біому
     * @param feature особливість
     * @return true, якщо особливість додано успішно
     */
    public boolean addFeatureToBiome(String biomeName, String feature) {
        Entity biomeEntity = getBiome(biomeName);
        if (biomeEntity == null || !biomeEntity.hasComponent(BiomeComponent.class)) {
            return false;
        }
        
        BiomeComponent biome = biomeEntity.getComponent(BiomeComponent.class);
        biome.addFeature(feature);
        return true;
    }
    
    /**
     * Додає спавн моба до біому.
     *
     * @param biomeName ім'я біому
     * @param mobType тип моба
     * @param weight вага спавну
     * @return true, якщо спавн додано успішно
     */
    public boolean addMobSpawnToBiome(String biomeName, String mobType, int weight) {
        Entity biomeEntity = getBiome(biomeName);
        if (biomeEntity == null || !biomeEntity.hasComponent(BiomeComponent.class)) {
            return false;
        }
        
        BiomeComponent biome = biomeEntity.getComponent(BiomeComponent.class);
        biome.addMobSpawn(mobType, weight);
        return true;
    }
    
    /**
     * Отримує випадковий біом.
     *
     * @return сутність випадкового біому
     */
    public Entity getRandomBiome() {
        if (biomes.isEmpty()) {
            return null;
        }
        
        int index = random.nextInt(biomes.size());
        return (Entity) biomes.values().toArray()[index];
    }
    
    /**
     * Отримує біоми з певним кліматом.
     *
     * @param climateType тип клімату
     * @return список біомів з цим кліматом
     */
    public List<Entity> getBiomesByClimate(ClimateType climateType) {
        List<Entity> result = new java.util.ArrayList<>();
        for (Entity entity : biomes.values()) {
            if (entity.hasComponent(BiomeComponent.class)) {
                BiomeComponent biome = entity.getComponent(BiomeComponent.class);
                if (biome.getClimateType() == climateType) {
                    result.add(entity);
                }
            }
        }
        return result;
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(BiomeComponent.class);
        return required;
    }
    
    /**
     * Встановлює менеджер сутностей.
     */
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}