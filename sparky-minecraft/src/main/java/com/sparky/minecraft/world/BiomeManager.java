package com.sparky.minecraft.world;

import com.sparky.minecraft.math.Vector3D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Андрій Будильников
 * 
 * Менеджер біомів для генерації світу
 */
public class BiomeManager {
    
    // Типи біомів
    public enum BiomeType {
        OCEAN,
        PLAINS,
        DESERT,
        MOUNTAINS,
        FOREST,
        TAIGA,
        SWAMP,
        RIVER,
        NETHER,
        END,
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
        MODIFIED_BADLANDS_PLATEAU
    }
    
    // Карта біомів
    private Map<BiomeType, Biome> biomes;
    private Random random;
    private long seed;
    
    public BiomeManager(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
        this.biomes = new HashMap<>();
        
        // Ініціалізуємо біоми
        initializeBiomes();
    }
    
    /**
     * Ініціалізує всі біоми з їх параметрами
     */
    private void initializeBiomes() {
        // Океан
        biomes.put(BiomeType.OCEAN, new Biome(BiomeType.OCEAN, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f));
        
        // Рівнини
        biomes.put(BiomeType.PLAINS, new Biome(BiomeType.PLAINS, 0.8f, 0.4f, 0.5f, 0.8f, 0.4f));
        
        // Пустеля
        biomes.put(BiomeType.DESERT, new Biome(BiomeType.DESERT, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
        
        // Гори
        biomes.put(BiomeType.MOUNTAINS, new Biome(BiomeType.MOUNTAINS, 0.2f, 0.3f, 0.2f, 0.3f, 0.3f));
        
        // Ліс
        biomes.put(BiomeType.FOREST, new Biome(BiomeType.FOREST, 0.7f, 0.8f, 0.7f, 0.8f, 0.7f));
        
        // Тайга
        biomes.put(BiomeType.TAIGA, new Biome(BiomeType.TAIGA, 0.25f, 0.8f, 0.25f, 0.8f, 0.7f));
        
        // Болото
        biomes.put(BiomeType.SWAMP, new Biome(BiomeType.SWAMP, 0.8f, 0.9f, 0.8f, 0.9f, 0.8f));
        
        // Річка
        biomes.put(BiomeType.RIVER, new Biome(BiomeType.RIVER, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f));
        
        // Незер
        biomes.put(BiomeType.NETHER, new Biome(BiomeType.NETHER, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
        
        // Енд
        biomes.put(BiomeType.END, new Biome(BiomeType.END, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
        
        // Замерзлий океан
        biomes.put(BiomeType.FROZEN_OCEAN, new Biome(BiomeType.FROZEN_OCEAN, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f));
        
        // Замерзла річка
        biomes.put(BiomeType.FROZEN_RIVER, new Biome(BiomeType.FROZEN_RIVER, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f));
        
        // Снігова тундра
        biomes.put(BiomeType.SNOWY_TUNDRA, new Biome(BiomeType.SNOWY_TUNDRA, 0.0f, 0.5f, 0.0f, 0.5f, 0.4f));
        
        // Снігові гори
        biomes.put(BiomeType.SNOWY_MOUNTAINS, new Biome(BiomeType.SNOWY_MOUNTAINS, 0.0f, 0.5f, 0.0f, 0.5f, 0.3f));
        
        // Грибні поля
        biomes.put(BiomeType.MUSHROOM_FIELDS, new Biome(BiomeType.MUSHROOM_FIELDS, 0.9f, 0.9f, 0.9f, 0.9f, 0.8f));
        
        // Пляж
        biomes.put(BiomeType.BEACH, new Biome(BiomeType.BEACH, 0.8f, 0.4f, 0.8f, 0.4f, 0.3f));
        
        // Джунглі
        biomes.put(BiomeType.JUNGLE, new Biome(BiomeType.JUNGLE, 0.95f, 0.9f, 0.95f, 0.9f, 0.8f));
        
        // Пагорби джунглів
        biomes.put(BiomeType.JUNGLE_HILLS, new Biome(BiomeType.JUNGLE_HILLS, 0.95f, 0.9f, 0.95f, 0.9f, 0.7f));
        
        // Край джунглів
        biomes.put(BiomeType.JUNGLE_EDGE, new Biome(BiomeType.JUNGLE_EDGE, 0.95f, 0.8f, 0.95f, 0.8f, 0.7f));
        
        // Глибокий океан
        biomes.put(BiomeType.DEEP_OCEAN, new Biome(BiomeType.DEEP_OCEAN, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f));
        
        // Кам'яний берег
        biomes.put(BiomeType.STONE_SHORE, new Biome(BiomeType.STONE_SHORE, 0.2f, 0.3f, 0.2f, 0.3f, 0.3f));
        
        // Сніговий пляж
        biomes.put(BiomeType.SNOWY_BEACH, new Biome(BiomeType.SNOWY_BEACH, 0.05f, 0.3f, 0.05f, 0.3f, 0.4f));
        
        // Березовий ліс
        biomes.put(BiomeType.BIRCH_FOREST, new Biome(BiomeType.BIRCH_FOREST, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f));
        
        // Пагорби березового лісу
        biomes.put(BiomeType.BIRCH_FOREST_HILLS, new Biome(BiomeType.BIRCH_FOREST_HILLS, 0.6f, 0.6f, 0.6f, 0.6f, 0.5f));
        
        // Темний ліс
        biomes.put(BiomeType.DARK_FOREST, new Biome(BiomeType.DARK_FOREST, 0.7f, 0.8f, 0.7f, 0.8f, 0.7f));
        
        // Снігова тайга
        biomes.put(BiomeType.SNOWY_TAIGA, new Biome(BiomeType.SNOWY_TAIGA, -0.5f, 0.4f, -0.5f, 0.4f, 0.7f));
        
        // Пагорби снігової тайги
        biomes.put(BiomeType.SNOWY_TAIGA_HILLS, new Biome(BiomeType.SNOWY_TAIGA_HILLS, -0.5f, 0.4f, -0.5f, 0.4f, 0.6f));
        
        // Тайга з гігантськими деревами
        biomes.put(BiomeType.GIANT_TREE_TAIGA, new Biome(BiomeType.GIANT_TREE_TAIGA, 0.3f, 0.8f, 0.3f, 0.8f, 0.7f));
        
        // Пагорби тайги з гігантськими деревами
        biomes.put(BiomeType.GIANT_TREE_TAIGA_HILLS, new Biome(BiomeType.GIANT_TREE_TAIGA_HILLS, 0.3f, 0.8f, 0.3f, 0.8f, 0.6f));
        
        // Лісисті гори
        biomes.put(BiomeType.WOODED_MOUNTAINS, new Biome(BiomeType.WOODED_MOUNTAINS, 0.2f, 0.3f, 0.2f, 0.3f, 0.3f));
        
        // Савана
        biomes.put(BiomeType.SAVANNA, new Biome(BiomeType.SAVANNA, 1.2f, 0.0f, 1.2f, 0.0f, 0.1f));
        
        // Саванне плато
        biomes.put(BiomeType.SAVANNA_PLATEAU, new Biome(BiomeType.SAVANNA_PLATEAU, 1.0f, 0.0f, 1.0f, 0.0f, 0.1f));
        
        // Бесплідні землі
        biomes.put(BiomeType.BADLANDS, new Biome(BiomeType.BADLANDS, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
        
        // Плато бесплідних земель
        biomes.put(BiomeType.BADLANDS_PLATEAU, new Biome(BiomeType.BADLANDS_PLATEAU, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
        
        // Малі острови Енду
        biomes.put(BiomeType.SMALL_END_ISLANDS, new Biome(BiomeType.SMALL_END_ISLANDS, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
        
        // Середні землі Енду
        biomes.put(BiomeType.END_MIDLANDS, new Biome(BiomeType.END_MIDLANDS, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
        
        // Височини Енду
        biomes.put(BiomeType.END_HIGHLANDS, new Biome(BiomeType.END_HIGHLANDS, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
        
        // Спустошені землі Енду
        biomes.put(BiomeType.END_BARRENS, new Biome(BiomeType.END_BARRENS, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
        
        // Теплий океан
        biomes.put(BiomeType.WARM_OCEAN, new Biome(BiomeType.WARM_OCEAN, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f));
        
        // Тепло-прохолодний океан
        biomes.put(BiomeType.LUKEWARM_OCEAN, new Biome(BiomeType.LUKEWARM_OCEAN, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f));
        
        // Холодний океан
        biomes.put(BiomeType.COLD_OCEAN, new Biome(BiomeType.COLD_OCEAN, 0.5f, 0.5f, 0.0f, 0.5f, 0.5f));
        
        // Глибокий теплий океан
        biomes.put(BiomeType.DEEP_WARM_OCEAN, new Biome(BiomeType.DEEP_WARM_OCEAN, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f));
        
        // Глибокий тепло-прохолодний океан
        biomes.put(BiomeType.DEEP_LUKEWARM_OCEAN, new Biome(BiomeType.DEEP_LUKEWARM_OCEAN, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f));
        
        // Глибокий холодний океан
        biomes.put(BiomeType.DEEP_COLD_OCEAN, new Biome(BiomeType.DEEP_COLD_OCEAN, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f));
        
        // Глибокий замерзлий океан
        biomes.put(BiomeType.DEEP_FROZEN_OCEAN, new Biome(BiomeType.DEEP_FROZEN_OCEAN, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f));
        
        // Порожнеча
        biomes.put(BiomeType.THE_VOID, new Biome(BiomeType.THE_VOID, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
        
        // Соняшникова рівнина
        biomes.put(BiomeType.SUNFLOWER_PLAINS, new Biome(BiomeType.SUNFLOWER_PLAINS, 0.8f, 0.4f, 0.5f, 0.8f, 0.4f));
        
        // Озера в пустелі
        biomes.put(BiomeType.DESERT_LAKES, new Biome(BiomeType.DESERT_LAKES, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
        
        // Щебенисті гори
        biomes.put(BiomeType.GRAVELLY_MOUNTAINS, new Biome(BiomeType.GRAVELLY_MOUNTAINS, 0.2f, 0.3f, 0.2f, 0.3f, 0.3f));
        
        // Квітковий ліс
        biomes.put(BiomeType.FLOWER_FOREST, new Biome(BiomeType.FLOWER_FOREST, 0.7f, 0.8f, 0.7f, 0.8f, 0.7f));
        
        // Гірська тайга
        biomes.put(BiomeType.TAIGA_MOUNTAINS, new Biome(BiomeType.TAIGA_MOUNTAINS, 0.25f, 0.8f, 0.25f, 0.8f, 0.7f));
        
        // Болотяні пагорби
        biomes.put(BiomeType.SWAMP_HILLS, new Biome(BiomeType.SWAMP_HILLS, 0.8f, 0.9f, 0.8f, 0.9f, 0.8f));
        
        // Крижані шипи
        biomes.put(BiomeType.ICE_SPIKES, new Biome(BiomeType.ICE_SPIKES, 0.0f, 0.5f, 0.0f, 0.5f, 0.4f));
        
        // Модифіковані джунглі
        biomes.put(BiomeType.MODIFIED_JUNGLE, new Biome(BiomeType.MODIFIED_JUNGLE, 0.95f, 0.9f, 0.95f, 0.9f, 0.8f));
        
        // Модифікований край джунглів
        biomes.put(BiomeType.MODIFIED_JUNGLE_EDGE, new Biome(BiomeType.MODIFIED_JUNGLE_EDGE, 0.95f, 0.8f, 0.95f, 0.8f, 0.7f));
        
        // Високий березовий ліс
        biomes.put(BiomeType.TALL_BIRCH_FOREST, new Biome(BiomeType.TALL_BIRCH_FOREST, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f));
        
        // Пагорби високого березового лісу
        biomes.put(BiomeType.TALL_BIRCH_HILLS, new Biome(BiomeType.TALL_BIRCH_HILLS, 0.6f, 0.6f, 0.6f, 0.6f, 0.5f));
        
        // Пагорби темного лісу
        biomes.put(BiomeType.DARK_FOREST_HILLS, new Biome(BiomeType.DARK_FOREST_HILLS, 0.7f, 0.8f, 0.7f, 0.8f, 0.7f));
        
        // Гірська снігова тайга
        biomes.put(BiomeType.SNOWY_TAIGA_MOUNTAINS, new Biome(BiomeType.SNOWY_TAIGA_MOUNTAINS, -0.5f, 0.4f, -0.5f, 0.4f, 0.7f));
        
        // Тайга з гігантських ялинок
        biomes.put(BiomeType.GIANT_SPRUCE_TAIGA, new Biome(BiomeType.GIANT_SPRUCE_TAIGA, 0.25f, 0.8f, 0.25f, 0.8f, 0.7f));
        
        // Пагорби тайги з гігантських ялинок
        biomes.put(BiomeType.GIANT_SPRUCE_TAIGA_HILLS, new Biome(BiomeType.GIANT_SPRUCE_TAIGA_HILLS, 0.25f, 0.8f, 0.25f, 0.8f, 0.6f));
        
        // Модифіковані щебенисті гори
        biomes.put(BiomeType.MODIFIED_GRAVELLY_MOUNTAINS, new Biome(BiomeType.MODIFIED_GRAVELLY_MOUNTAINS, 0.2f, 0.3f, 0.2f, 0.3f, 0.3f));
        
        // Розколота савана
        biomes.put(BiomeType.SHATTERED_SAVANNA, new Biome(BiomeType.SHATTERED_SAVANNA, 1.1f, 0.0f, 1.1f, 0.0f, 0.1f));
        
        // Плато розколотої савани
        biomes.put(BiomeType.SHATTERED_SAVANNA_PLATEAU, new Biome(BiomeType.SHATTERED_SAVANNA_PLATEAU, 1.0f, 0.0f, 1.0f, 0.0f, 0.1f));
        
        // Зруйновані бесплідні землі
        biomes.put(BiomeType.ERODED_BADLANDS, new Biome(BiomeType.ERODED_BADLANDS, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
        
        // Модифіковане лісисте плато бесплідних земель
        biomes.put(BiomeType.MODIFIED_WOODED_BADLANDS_PLATEAU, new Biome(BiomeType.MODIFIED_WOODED_BADLANDS_PLATEAU, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
        
        // Модифіковане плато бесплідних земель
        biomes.put(BiomeType.MODIFIED_BADLANDS_PLATEAU, new Biome(BiomeType.MODIFIED_BADLANDS_PLATEAU, 2.0f, 0.0f, 2.0f, 0.0f, 0.0f));
    }
    
    /**
     * Отримує біом за типом
     * 
     * @param type тип біому
     * @return біом
     */
    public Biome getBiome(BiomeType type) {
        return biomes.get(type);
    }
    
    /**
     * Визначає біом для заданих координат
     * 
     * @param x координата X
     * @param z координата Z
     * @return тип біому
     */
    public BiomeType getBiomeAt(int x, int z) {
        // Використовуємо шум для визначення біому
        // Це спрощена версія, в реальній реалізації буде більш складний алгоритм
        
        // Генеруємо значення шуму для температури та вологості
        float temperature = generateTemperatureNoise(x, z);
        float humidity = generateHumidityNoise(x, z);
        float continentalness = generateContinentalnessNoise(x, z);
        
        // Визначаємо біом на основі параметрів
        return determineBiome(temperature, humidity, continentalness);
    }
    
    /**
     * Генерує шум температури
     * 
     * @param x координата X
     * @param z координата Z
     * @return значення температури в діапазоні [-1, 1]
     */
    private float generateTemperatureNoise(int x, int z) {
        // Спрощена реалізація, в реальності буде використовуватись перліновий шум
        return (float) (Math.sin(x * 0.01) * Math.cos(z * 0.01));
    }
    
    /**
     * Генерує шум вологості
     * 
     * @param x координата X
     * @param z координата Z
     * @return значення вологості в діапазоні [0, 1]
     */
    private float generateHumidityNoise(int x, int z) {
        // Спрощена реалізація, в реальності буде використовуватись перліновий шум
        return (float) (Math.sin(x * 0.005) * Math.cos(z * 0.005) * 0.5 + 0.5);
    }
    
    /**
     * Генерує шум континентальності
     * 
     * @param x координата X
     * @param z координата Z
     * @return значення континентальності в діапазоні [-1, 1]
     */
    private float generateContinentalnessNoise(int x, int z) {
        // Спрощена реалізація, в реальності буде використовуватись перліновий шум
        return (float) (Math.sin(x * 0.002) * Math.cos(z * 0.002));
    }
    
    /**
     * Визначає біом на основі параметрів
     * 
     * @param temperature температура
     * @param humidity вологість
     * @param continentalness континентальність
     * @return тип біому
     */
    private BiomeType determineBiome(float temperature, float humidity, float continentalness) {
        // Якщо континентальність низька, це океан або річка
        if (continentalness < -0.2) {
            if (temperature < 0.2) {
                return BiomeType.FROZEN_OCEAN;
            } else {
                return BiomeType.OCEAN;
            }
        }
        
        // Якщо температура дуже висока, це пустеля
        if (temperature > 0.8) {
            return BiomeType.DESERT;
        }
        
        // Якщо температура дуже низька, це снігова тундра або снігові гори
        if (temperature < 0.2) {
            if (continentalness > 0.5) {
                return BiomeType.SNOWY_MOUNTAINS;
            } else {
                return BiomeType.SNOWY_TUNDRA;
            }
        }
        
        // Якщо вологість висока, це ліс або джунглі
        if (humidity > 0.7) {
            if (temperature > 0.6) {
                return BiomeType.JUNGLE;
            } else {
                return BiomeType.FOREST;
            }
        }
        
        // Якщо вологість низька, це рівнини або савана
        if (humidity < 0.3) {
            if (temperature > 0.5) {
                return BiomeType.SAVANNA;
            } else {
                return BiomeType.PLAINS;
            }
        }
        
        // За замовчуванням - рівнини
        return BiomeType.PLAINS;
    }
    
    /**
     * Представляє біом з його параметрами
     */
    public static class Biome {
        private BiomeType type;
        private float temperature;
        private float humidity;
        private float continentalness;
        private float erosion;
        private float depth;
        
        public Biome(BiomeType type, float temperature, float humidity, float continentalness, float erosion, float depth) {
            this.type = type;
            this.temperature = temperature;
            this.humidity = humidity;
            this.continentalness = continentalness;
            this.erosion = erosion;
            this.depth = depth;
        }
        
        // Геттери
        public BiomeType getType() {
            return type;
        }
        
        public float getTemperature() {
            return temperature;
        }
        
        public float getHumidity() {
            return humidity;
        }
        
        public float getContinentalness() {
            return continentalness;
        }
        
        public float getErosion() {
            return erosion;
        }
        
        public float getDepth() {
            return depth;
        }
        
        @Override
        public String toString() {
            return "Biome{" +
                    "type=" + type +
                    ", temperature=" + temperature +
                    ", humidity=" + humidity +
                    ", continentalness=" + continentalness +
                    ", erosion=" + erosion +
                    ", depth=" + depth +
                    '}';
        }
    }
}