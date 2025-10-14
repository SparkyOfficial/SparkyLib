package com.sparky.minecraft;

import com.sparky.ecs.Component;
import com.sparky.minecraft.math.Vector3D;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * Компонент для представлення біому в Minecraft.
 * Використовується для управління властивостями біомів, такими як клімат, рослинність, тварини тощо.
 *
 * @author Богдан Кравчук
 */
public class BiomeComponent extends Component {
    public enum BiomeType {
        OCEAN,
        PLAINS,
        DESERT,
        MOUNTAINS,
        FOREST,
        TAIGA,
        SWAMP,
        RIVER,
        NETHER_WASTES,
        THE_END,
        FROZEN_OCEAN,
        FROZEN_RIVER,
        SNOWY_TUNDRA,
        SNOWY_MOUNTAINS,
        MUSHROOM_FIELDS,
        BEACH,
        JUNGLE,
        JUNGLE_HILLS,
        JUNGLE_EDGE,
        DEEP_OCEAN,
        STONE_SHORE,
        SNOWY_BEACH,
        BIRCH_FOREST,
        BIRCH_FOREST_HILLS,
        DARK_FOREST,
        SNOWY_TAIGA,
        SNOWY_TAIGA_HILLS,
        GIANT_TREE_TAIGA,
        GIANT_TREE_TAIGA_HILLS,
        WOODED_MOUNTAINS,
        SAVANNA,
        SAVANNA_PLATEAU,
        BADLANDS,
        WOODED_BADLANDS_PLATEAU,
        BADLANDS_PLATEAU,
        SMALL_END_ISLANDS,
        END_MIDLANDS,
        END_HIGHLANDS,
        END_BARRENS,
        WARM_OCEAN,
        LUKEWARM_OCEAN,
        COLD_OCEAN,
        DEEP_WARM_OCEAN,
        DEEP_LUKEWARM_OCEAN,
        DEEP_COLD_OCEAN,
        DEEP_FROZEN_OCEAN,
        THE_VOID,
        SUNFLOWER_PLAINS,
        DESERT_LAKES,
        GRAVELLY_MOUNTAINS,
        FLOWER_FOREST,
        TAIGA_MOUNTAINS,
        SWAMP_HILLS,
        ICE_SPIKES,
        MODIFIED_JUNGLE,
        MODIFIED_JUNGLE_EDGE,
        TALL_BIRCH_FOREST,
        TALL_BIRCH_HILLS,
        DARK_FOREST_HILLS,
        SNOWY_TAIGA_MOUNTAINS,
        GIANT_SPRUCE_TAIGA,
        GIANT_SPRUCE_TAIGA_HILLS,
        MODIFIED_GRAVELLY_MOUNTAINS,
        SHATTERED_SAVANNA,
        SHATTERED_SAVANNA_PLATEAU,
        ERODED_BADLANDS,
        MODIFIED_WOODED_BADLANDS_PLATEAU,
        MODIFIED_BADLANDS_PLATEAU,
        BAMBOO_JUNGLE,
        BAMBOO_JUNGLE_HILLS,
        SOUL_SAND_VALLEY,
        CRIMSON_FOREST,
        WARPED_FOREST,
        BASALT_DELTAS
    }
    
    public enum ClimateType {
        OCEANIC,
        COLD,
        TEMPERATE,
        WARM,
        DESERT,
        NETHER,
        END
    }
    
    private String biomeName;
    private BiomeType biomeType;
    private ClimateType climateType;
    private float temperature;
    private float rainfall;
    private int waterColor;
    private int foliageColor;
    private int grassColor;
    private boolean hasRain;
    private boolean hasSnow;
    private Set<String> features;
    private Map<String, Integer> mobSpawns;
    
    public BiomeComponent() {
        this("plains", BiomeType.PLAINS, ClimateType.TEMPERATE, 0.8f, 0.4f, 
             0x3F76E4, 0x71A047, 0x71A047, true, false);
    }
    
    public BiomeComponent(String biomeName, BiomeType biomeType, ClimateType climateType,
                         float temperature, float rainfall, int waterColor, 
                         int foliageColor, int grassColor, boolean hasRain, boolean hasSnow) {
        this.biomeName = biomeName;
        this.biomeType = biomeType;
        this.climateType = climateType;
        this.temperature = temperature;
        this.rainfall = rainfall;
        this.waterColor = waterColor;
        this.foliageColor = foliageColor;
        this.grassColor = grassColor;
        this.hasRain = hasRain;
        this.hasSnow = hasSnow;
        this.features = new HashSet<>();
        this.mobSpawns = new HashMap<>();
    }
    
    public String getBiomeName() {
        return biomeName;
    }
    
    public void setBiomeName(String biomeName) {
        this.biomeName = biomeName;
    }
    
    public BiomeType getBiomeType() {
        return biomeType;
    }
    
    public void setBiomeType(BiomeType biomeType) {
        this.biomeType = biomeType;
    }
    
    public ClimateType getClimateType() {
        return climateType;
    }
    
    public void setClimateType(ClimateType climateType) {
        this.climateType = climateType;
    }
    
    public float getTemperature() {
        return temperature;
    }
    
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    
    public float getRainfall() {
        return rainfall;
    }
    
    public void setRainfall(float rainfall) {
        this.rainfall = rainfall;
    }
    
    public int getWaterColor() {
        return waterColor;
    }
    
    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }
    
    public int getFoliageColor() {
        return foliageColor;
    }
    
    public void setFoliageColor(int foliageColor) {
        this.foliageColor = foliageColor;
    }
    
    public int getGrassColor() {
        return grassColor;
    }
    
    public void setGrassColor(int grassColor) {
        this.grassColor = grassColor;
    }
    
    public boolean hasRain() {
        return hasRain;
    }
    
    public void setHasRain(boolean hasRain) {
        this.hasRain = hasRain;
    }
    
    public boolean hasSnow() {
        return hasSnow;
    }
    
    public void setHasSnow(boolean hasSnow) {
        this.hasSnow = hasSnow;
    }
    
    public Set<String> getFeatures() {
        return new HashSet<>(features);
    }
    
    public void addFeature(String feature) {
        features.add(feature);
    }
    
    public void removeFeature(String feature) {
        features.remove(feature);
    }
    
    public boolean hasFeature(String feature) {
        return features.contains(feature);
    }
    
    public Map<String, Integer> getMobSpawns() {
        return new HashMap<>(mobSpawns);
    }
    
    public void addMobSpawn(String mobType, int weight) {
        mobSpawns.put(mobType, weight);
    }
    
    public void removeMobSpawn(String mobType) {
        mobSpawns.remove(mobType);
    }
    
    public int getMobSpawnWeight(String mobType) {
        return mobSpawns.getOrDefault(mobType, 0);
    }
    
    /**
     * Перевіряє, чи біом підходить для розміщення води.
     *
     * @return true, якщо біом підходить для води
     */
    public boolean isWaterFriendly() {
        return climateType != ClimateType.DESERT && climateType != ClimateType.NETHER && climateType != ClimateType.END;
    }
    
    /**
     * Перевіряє, чи біом підходить для снігу.
     *
     * @return true, якщо біом підходить для снігу
     */
    public boolean isSnowy() {
        return temperature < 0.3f || hasSnow;
    }
    
    /**
     * Отримує рівень рослинності біому.
     *
     * @return рівень рослинності від 0.0 до 1.0
     */
    public float getVegetationLevel() {
        if (climateType == ClimateType.DESERT || climateType == ClimateType.NETHER || climateType == ClimateType.END) {
            return 0.0f;
        }
        return Math.min(1.0f, rainfall * (1.5f - Math.abs(temperature - 0.5f)));
    }
}