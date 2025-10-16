package com.sparky.minecraft.world;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.world.BiomeManager.Biome;
import com.sparky.minecraft.world.BiomeManager.BiomeType;
import java.util.Random;

/**
 * @author Андрій Будильников
 * 
 * Генератор терену з використанням біомів
 */
public class TerrainGenerator {
    
    private BiomeManager biomeManager;
    private Random random;
    private long seed;
    
    // Параметри генерації
    private float terrainScale;
    private int octaves;
    private float persistence;
    private float lacunarity;
    
    public TerrainGenerator(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
        this.biomeManager = new BiomeManager(seed);
        
        // Стандартні параметри генерації
        this.terrainScale = 0.005f;
        this.octaves = 6;
        this.persistence = 0.5f;
        this.lacunarity = 2.0f;
    }
    
    // Геттери та сеттери для параметрів генерації
    public float getTerrainScale() {
        return terrainScale;
    }
    
    public void setTerrainScale(float terrainScale) {
        this.terrainScale = terrainScale;
    }
    
    public int getOctaves() {
        return octaves;
    }
    
    public void setOctaves(int octaves) {
        this.octaves = octaves;
    }
    
    public float getPersistence() {
        return persistence;
    }
    
    public void setPersistence(float persistence) {
        this.persistence = persistence;
    }
    
    public float getLacunarity() {
        return lacunarity;
    }
    
    public void setLacunarity(float lacunarity) {
        this.lacunarity = lacunarity;
    }
    
    /**
     * Генерує висоту терену для заданих координат
     * 
     * @param x координата X
     * @param z координата Z
     * @return висота терену
     */
    public double generateHeight(int x, int z) {
        // Отримуємо біом для цих координат
        BiomeType biomeType = biomeManager.getBiomeAt(x, z);
        Biome biome = biomeManager.getBiome(biomeType);
        
        // Генеруємо базову висоту з використанням фрактального шуму
        double baseHeight = generateFractalNoise(x, z);
        
        // Модифікуємо висоту в залежності від біому
        double modifiedHeight = modifyHeightByBiome(baseHeight, biome);
        
        return modifiedHeight;
    }
    
    /**
     * Генерує фрактальний шум (FBM) для створення реалістичного терену
     * 
     * @param x координата X
     * @param z координата Z
     * @return значення шуму
     */
    private double generateFractalNoise(int x, int z) {
        double total = 0;
        double frequency = terrainScale;
        double amplitude = 1;
        double maxValue = 0; // Для нормалізації результату
        
        for (int i = 0; i < octaves; i++) {
            // Використовуємо перліновий шум (спрощена реалізація)
            double noiseValue = perlinNoise(x * frequency, z * frequency);
            total += noiseValue * amplitude;
            
            maxValue += amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;
        }
        
        return total / maxValue; // Нормалізована висота в діапазоні [-1, 1]
    }
    
    /**
     * Модифікує висоту в залежності від біому
     * 
     * @param baseHeight базова висота
     * @param biome біом
     * @return модифікована висота
     */
    private double modifyHeightByBiome(double baseHeight, Biome biome) {
        // Нормалізуємо базову висоту до діапазону [0, 1]
        double normalizedHeight = (baseHeight + 1) / 2;
        
        // Отримуємо параметри біому
        float depth = biome.getDepth();
        float scale = biome.getContinentalness(); // Використовуємо як масштаб
        
        // Модифікуємо висоту в залежності від біому
        double modifiedHeight = normalizedHeight * (1 + depth) + scale;
        
        // Перетворюємо назад до діапазону [-1, 1] для сумісності
        modifiedHeight = modifiedHeight * 2 - 1;
        
        return modifiedHeight;
    }
    
    /**
     * Реалізація перлінового шуму (спрощена версія)
     * 
     * @param x координата X
     * @param z координата Z
     * @return значення шуму в діапазоні [-1, 1]
     */
    private double perlinNoise(double x, double z) {
        // Це спрощена реалізація перлінового шуму
        // В реальній реалізації буде використовуватись повноцінний алгоритм перлінового шуму
        
        // Використовуємо комбінацію тригонометричних функцій для створення шуму
        double noise = Math.sin(x * 12.9898) * Math.cos(z * 78.233);
        noise = noise - Math.floor(noise); // Фракційна частина
        noise = noise * 2 - 1; // Перетворюємо до діапазону [-1, 1]
        
        return noise;
    }
    
    /**
     * Генерує тип блоку для заданих координат
     * 
     * @param x координата X
     * @param y координата Y
     * @param z координата Z
     * @return тип блоку
     */
    public String generateBlockType(int x, int y, int z) {
        // Отримуємо біом для цих координат
        BiomeType biomeType = biomeManager.getBiomeAt(x, z);
        Biome biome = biomeManager.getBiome(biomeType);
        
        // Генеруємо висоту терену
        double height = generateHeight(x, z);
        
        // Перетворюємо висоту в ціле число
        int heightInt = (int) (height * 64 + 64); // Від 0 до 128
        
        // Визначаємо тип блоку на основі висоти та біому
        return determineBlockType(x, y, z, heightInt, biomeType);
    }
    
    /**
     * Визначає тип блоку на основі висоти, біому та координат
     * 
     * @param x координата X
     * @param y координата Y
     * @param z координата Z
     * @param height висота терену
     * @param biomeType тип біому
     * @return тип блоку
     */
    private String determineBlockType(int x, int y, int z, int height, BiomeType biomeType) {
        // Якщо ми нижче рівня моря (64)
        if (y < 64) {
            // Якщо ми нижче висоти терену, це камінь
            if (y < height - 5) {
                return "STONE";
            }
            // Якщо ми близько до висоти терену, це руда
            else if (y < height) {
                // Визначаємо тип руди на основі глибини
                if (y < 10) {
                    return random.nextDouble() < 0.01 ? "DIAMOND_ORE" : "STONE";
                } else if (y < 20) {
                    return random.nextDouble() < 0.02 ? "GOLD_ORE" : "STONE";
                } else if (y < 30) {
                    return random.nextDouble() < 0.03 ? "IRON_ORE" : "STONE";
                } else if (y < 40) {
                    return random.nextDouble() < 0.04 ? "COAL_ORE" : "STONE";
                } else {
                    return "STONE";
                }
            }
            // Якщо ми вище висоти терену, але нижче рівня моря, це вода
            else {
                return "WATER";
            }
        }
        // Якщо ми на рівні або вище рівня моря
        else {
            // Якщо ми нижче висоти терену, це камінь
            if (y < height - 5) {
                return "STONE";
            }
            // Якщо ми близько до висоти терену, це поверхневий блок
            else if (y < height) {
                return getSurfaceBlock(biomeType);
            }
            // Якщо ми вище висоти терену, це повітря
            else {
                return "AIR";
            }
        }
    }
    
    /**
     * Отримує тип поверхневого блоку для заданого біому
     * 
     * @param biomeType тип біому
     * @return тип поверхневого блоку
     */
    private String getSurfaceBlock(BiomeType biomeType) {
        switch (biomeType) {
            case DESERT:
            case DESERT_LAKES:
                return "SAND";
            case OCEAN:
            case DEEP_OCEAN:
            case WARM_OCEAN:
            case LUKEWARM_OCEAN:
            case COLD_OCEAN:
            case DEEP_WARM_OCEAN:
            case DEEP_LUKEWARM_OCEAN:
            case DEEP_COLD_OCEAN:
            case FROZEN_OCEAN:
            case DEEP_FROZEN_OCEAN:
                return "SAND";
            case SNOWY_TUNDRA:
            case SNOWY_MOUNTAINS:
            case SNOWY_BEACH:
            case SNOWY_TAIGA:
            case SNOWY_TAIGA_HILLS:
            case SNOWY_TAIGA_MOUNTAINS:
            case ICE_SPIKES:
                return "SNOW_BLOCK";
            case MUSHROOM_FIELDS:
                return "MYCELIUM";
            case NETHER:
                return "NETHERRACK";
            case END:
            case SMALL_END_ISLANDS:
            case END_MIDLANDS:
            case END_HIGHLANDS:
            case END_BARRENS:
                return "END_STONE";
            default:
                return "GRASS_BLOCK";
        }
    }
    
    /**
     * Генерує рослинність для заданих координат
     * 
     * @param x координата X
     * @param y координата Y
     * @param z координата Z
     * @return тип рослинності або null, якщо рослинність відсутня
     */
    public String generateVegetation(int x, int y, int z) {
        // Отримуємо біом для цих координат
        BiomeType biomeType = biomeManager.getBiomeAt(x, z);
        
        // Генеруємо висоту терену
        double height = generateHeight(x, z);
        int heightInt = (int) (height * 64 + 64);
        
        // Рослинність може з'явитись лише на поверхні
        if (y != heightInt) {
            return null;
        }
        
        // Визначаємо тип рослинності на основі біому
        return determineVegetationType(biomeType);
    }
    
    /**
     * Визначає тип рослинності для заданого біому
     * 
     * @param biomeType тип біому
     * @return тип рослинності або null
     */
    private String determineVegetationType(BiomeType biomeType) {
        // Визначаємо ймовірність появи рослинності
        double vegetationChance = 0.0;
        
        switch (biomeType) {
            case FOREST:
            case BIRCH_FOREST:
            case BIRCH_FOREST_HILLS:
            case DARK_FOREST:
            case DARK_FOREST_HILLS:
            case TALL_BIRCH_FOREST:
            case TALL_BIRCH_HILLS:
                vegetationChance = 0.3;
                break;
            case TAIGA:
            case TAIGA_MOUNTAINS:
            case SNOWY_TAIGA:
            case SNOWY_TAIGA_HILLS:
            case SNOWY_TAIGA_MOUNTAINS:
            case GIANT_TREE_TAIGA:
            case GIANT_TREE_TAIGA_HILLS:
            case GIANT_SPRUCE_TAIGA:
            case GIANT_SPRUCE_TAIGA_HILLS:
                vegetationChance = 0.2;
                break;
            case PLAINS:
            case SUNFLOWER_PLAINS:
                vegetationChance = 0.1;
                break;
            case JUNGLE:
            case JUNGLE_HILLS:
            case JUNGLE_EDGE:
            case MODIFIED_JUNGLE:
            case MODIFIED_JUNGLE_EDGE:
                vegetationChance = 0.5;
                break;
            case SWAMP:
            case SWAMP_HILLS:
                vegetationChance = 0.4;
                break;
            case SAVANNA:
            case SAVANNA_PLATEAU:
            case SHATTERED_SAVANNA:
            case SHATTERED_SAVANNA_PLATEAU:
                vegetationChance = 0.15;
                break;
            case MUSHROOM_FIELDS:
                vegetationChance = 0.6;
                break;
            default:
                vegetationChance = 0.05;
                break;
        }
        
        // Генеруємо випадкове число
        double randomValue = random.nextDouble();
        
        // Якщо випадкове число менше за ймовірність, створюємо рослинність
        if (randomValue < vegetationChance) {
            return getRandomVegetation(biomeType);
        }
        
        return null;
    }
    
    /**
     * Отримує випадковий тип рослинності для заданого біому
     * 
     * @param biomeType тип біому
     * @return тип рослинності
     */
    private String getRandomVegetation(BiomeType biomeType) {
        switch (biomeType) {
            case FOREST:
            case BIRCH_FOREST:
            case BIRCH_FOREST_HILLS:
            case TALL_BIRCH_FOREST:
            case TALL_BIRCH_HILLS:
                return random.nextBoolean() ? "OAK_TREE" : "BIRCH_TREE";
            case DARK_FOREST:
            case DARK_FOREST_HILLS:
                return random.nextBoolean() ? "DARK_OAK_TREE" : "OAK_TREE";
            case TAIGA:
            case TAIGA_MOUNTAINS:
            case SNOWY_TAIGA:
            case SNOWY_TAIGA_HILLS:
            case SNOWY_TAIGA_MOUNTAINS:
                return "SPRUCE_TREE";
            case GIANT_TREE_TAIGA:
            case GIANT_TREE_TAIGA_HILLS:
            case GIANT_SPRUCE_TAIGA:
            case GIANT_SPRUCE_TAIGA_HILLS:
                return random.nextBoolean() ? "GIANT_SPRUCE_TREE" : "GIANT_PINE_TREE";
            case PLAINS:
            case SUNFLOWER_PLAINS:
                return random.nextBoolean() ? "GRASS" : "FLOWER";
            case JUNGLE:
            case JUNGLE_HILLS:
            case JUNGLE_EDGE:
            case MODIFIED_JUNGLE:
            case MODIFIED_JUNGLE_EDGE:
                return random.nextInt(3) == 0 ? "JUNGLE_TREE" : (random.nextBoolean() ? "MELON" : "COCOA_BEAN");
            case SWAMP:
            case SWAMP_HILLS:
                return random.nextBoolean() ? "SWAMP_TREE" : "DEAD_BUSH";
            case SAVANNA:
            case SAVANNA_PLATEAU:
            case SHATTERED_SAVANNA:
            case SHATTERED_SAVANNA_PLATEAU:
                return random.nextBoolean() ? "ACACIA_TREE" : "GRASS";
            case MUSHROOM_FIELDS:
                return random.nextBoolean() ? "RED_MUSHROOM" : "BROWN_MUSHROOM";
            case DESERT:
            case DESERT_LAKES:
                return "CACTUS";
            default:
                return "GRASS";
        }
    }
    
    /**
     * Генерує структури (будівлі, печери, тощо) для заданої області
     * 
     * @param chunkX координата чанку X
     * @param chunkZ координата чанку Z
     * @return список структур у чанку
     */
    public void generateStructures(int chunkX, int chunkZ) {
        // Отримуємо біом для центру чанку
        BiomeType biomeType = biomeManager.getBiomeAt(chunkX * 16 + 8, chunkZ * 16 + 8);
        
        // Визначаємо, які структури можуть з'явитись у цьому біомі
        generateBiomeStructures(chunkX, chunkZ, biomeType);
    }
    
    /**
     * Генерує структури специфічні для біому
     * 
     * @param chunkX координата чанку X
     * @param chunkZ координата чанку Z
     * @param biomeType тип біому
     */
    private void generateBiomeStructures(int chunkX, int chunkZ, BiomeType biomeType) {
        // Визначаємо ймовірність появи структур
        double structureChance = 0.0;
        
        // Визначаємо ймовірність появи структур на основі типу біому
        switch (biomeType) {
            case DESERT:
                structureChance = 0.05; // Піраміда в пустелі
                break;
            case JUNGLE:
            case JUNGLE_HILLS:
                structureChance = 0.05; // Храм джунглів
                break;
            case SNOWY_TUNDRA:
            case SNOWY_MOUNTAINS:
                structureChance = 0.05; // Іглу в снігових біомах
                break;
            case PLAINS:
            case SUNFLOWER_PLAINS:
                structureChance = 0.03; // Аутпост піліжерів
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
                structureChance = 0.03; // Затонулі кораблі та океанські монументи
                break;
            case FOREST:
            case BIRCH_FOREST:
            case DARK_FOREST:
                structureChance = 0.01; // Лісова оселя
                break;
            case TAIGA:
            case SNOWY_TAIGA:
            case GIANT_TREE_TAIGA:
                structureChance = 0.02; // Міцність
                break;
            case NETHER:
                structureChance = 0.1; // Форт Незеру
                break;
            case END:
                structureChance = 0.05; // Місто Енду
                break;
            default:
                structureChance = 0.01; // Стандартна ймовірність
                break;
        }
        
        // Генеруємо випадкове число
        double randomValue = random.nextDouble();
        
        // Якщо випадкове число менше за ймовірність, створюємо структуру
        if (randomValue < structureChance) {
            generateRandomStructure(chunkX, chunkZ, biomeType);
        }
    }
    
    /**
     * Генерує випадкову структуру
     * 
     * @param chunkX координата чанку X
     * @param chunkZ координата чанку Z
     * @param biomeType тип біому
     */
    private void generateRandomStructure(int chunkX, int chunkZ, BiomeType biomeType) {
        // В реальній реалізації тут була б логіка генерації конкретних структур
        System.out.println("Generating structure in chunk (" + chunkX + ", " + chunkZ + ") with biome " + biomeType);
    }
}