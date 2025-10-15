package com.sparky.minecraft.world;

import java.util.Random;

/**
 * Система процедурної генерації світу для Minecraft.
 *
 * @author Андрій Будильников
 */
public class ProceduralWorldGenerator {
    private long seed;
    private Random random;
    
    public ProceduralWorldGenerator(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }
    
    // генерує базовий терен за координатами чанку
    public ChunkData generateChunk(int chunkX, int chunkZ) {
        ChunkData chunk = new ChunkData(chunkX, chunkZ);
        
        // генеруємо висоти терену
        generateTerrainHeights(chunk);
        
        // додаємо біоми
        applyBiomes(chunk);
        
        // розміщуємо структури
        placeStructures(chunk);
        
        return chunk;
    }
    
    // генерує висоти терену з використанням шуму перліна
    private void generateTerrainHeights(ChunkData chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // комбінуємо кілька октав шуму для більш реалістичного терену
                double height = generateHeightWithNoise(
                    chunk.getChunkX() * 16 + x, 
                    chunk.getChunkZ() * 16 + z
                );
                
                // встановлюємо блоки відповідно до висоти
                for (int y = 0; y < Math.min(256, height + 64); y++) {
                    if (y == 0) {
                        chunk.setBlock(x, y, z, "bedrock");
                    } else if (y < height + 60) {
                        chunk.setBlock(x, y, z, "stone");
                    } else if (y < height + 63) {
                        chunk.setBlock(x, y, z, "dirt");
                    } else if (y < height + 64) {
                        chunk.setBlock(x, y, z, "grass");
                    }
                }
            }
        }
    }
    
    // генерація висоти з шумом перліна
    private double generateHeightWithNoise(int x, int z) {
        // базова висота
        double baseHeight = 32;
        
        // перша октава - великі форми рельєфу
        double octave1 = perlinNoise(x * 0.01, z * 0.01, 1) * 64;
        
        // друга октава - середні форми
        double octave2 = perlinNoise(x * 0.02, z * 0.02, 2) * 32;
        
        // третя октава - дрібні деталі
        double octave3 = perlinNoise(x * 0.04, z * 0.04, 4) * 16;
        
        // четверта октава - дуже дрібні деталі
        double octave4 = perlinNoise(x * 0.08, z * 0.08, 8) * 8;
        
        return baseHeight + octave1 + octave2 + octave3 + octave4;
    }
    
    // спрощена реалізація шуму перліна
    private double perlinNoise(double x, double z, int octaves) {
        // це спрощена версія, в реальному коді було б складніше
        double value = 0;
        double amplitude = 1.0;
        double frequency = 1.0;
        
        for (int i = 0; i < octaves; i++) {
            value += interpolatedNoise(x * frequency, z * frequency) * amplitude;
            amplitude *= 0.5;
            frequency *= 2;
        }
        
        return value;
    }
    
    // інтерполюємо шум між точками
    private double interpolatedNoise(double x, double z) {
        int intX = (int) Math.floor(x);
        int intZ = (int) Math.floor(z);
        double fracX = x - intX;
        double fracZ = z - intZ;
        
        // отримуємо значення шуму в кутах
        double v1 = noise(intX, intZ);
        double v2 = noise(intX + 1, intZ);
        double v3 = noise(intX, intZ + 1);
        double v4 = noise(intX + 1, intZ + 1);
        
        // інтерполюємо по x
        double i1 = interpolate(v1, v2, fracX);
        double i2 = interpolate(v3, v4, fracX);
        
        // інтерполюємо по z
        return interpolate(i1, i2, fracZ);
    }
    
    // базова функція шуму
    private double noise(int x, int z) {
        // використовуємо просту хеш-функцію для генерації псевдовипадкових значень
        int n = x + z * 57;
        n = (n << 13) ^ n;
        return (1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }
    
    // косинусна інтерполяція
    private double interpolate(double a, double b, double t) {
        double ft = t * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5;
        return a * (1 - f) + b * f;
    }
    
    // застосовує біоми до чанку
    private void applyBiomes(ChunkData chunk) {
        // визначаємо біом на основі координат
        String biome = determineBiome(chunk.getChunkX(), chunk.getChunkZ());
        chunk.setBiome(biome);
        
        // модифікуємо блоки відповідно до біому
        modifyBlocksForBiome(chunk, biome);
    }
    
    // визначає біом за координатами
    private String determineBiome(int chunkX, int chunkZ) {
        // використовуємо шум для визначення біомів
        double temperature = perlinNoise(chunkX * 0.005, chunkZ * 0.005, 2);
        double humidity = perlinNoise(chunkX * 0.005 + 1000, chunkZ * 0.005 + 1000, 2);
        
        if (temperature > 0.5) {
            if (humidity > 0.5) {
                return "jungle";
            } else {
                return "desert";
            }
        } else {
            if (humidity > 0.5) {
                return "forest";
            } else {
                return "plains";
            }
        }
    }
    
    // модифікує блоки відповідно до біому
    private void modifyBlocksForBiome(ChunkData chunk, String biome) {
        switch (biome) {
            case "desert":
                // замінюємо траву на пісок
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = 0; y < 256; y++) {
                            if ("grass".equals(chunk.getBlock(x, y, z))) {
                                chunk.setBlock(x, y, z, "sand");
                            }
                        }
                    }
                }
                break;
            case "jungle":
                // додаємо дерева
                if (random.nextDouble() < 0.3) {
                    int treeX = random.nextInt(16);
                    int treeZ = random.nextInt(16);
                    generateTree(chunk, treeX, treeZ, "jungle");
                }
                break;
            case "forest":
                // додаємо дерева
                if (random.nextDouble() < 0.2) {
                    int treeX = random.nextInt(16);
                    int treeZ = random.nextInt(16);
                    generateTree(chunk, treeX, treeZ, "oak");
                }
                break;
        }
    }
    
    // генерує дерево в чанку
    private void generateTree(ChunkData chunk, int x, int z, String type) {
        // знаходимо висоту поверхні
        int surfaceHeight = 0;
        for (int y = 255; y >= 0; y--) {
            if (!"air".equals(chunk.getBlock(x, y, z))) {
                surfaceHeight = y;
                break;
            }
        }
        
        // генеруємо стовбур
        int treeHeight = 5 + random.nextInt(3);
        for (int y = surfaceHeight + 1; y <= surfaceHeight + treeHeight; y++) {
            chunk.setBlock(x, y, z, type + "_log");
        }
        
        // генеруємо листя
        for (int lx = -2; lx <= 2; lx++) {
            for (int lz = -2; lz <= 2; lz++) {
                for (int ly = 0; ly <= 2; ly++) {
                    // пропускаємо кути для більш природньої форми
                    if ((Math.abs(lx) == 2 && Math.abs(lz) == 2) && ly < 2) {
                        continue;
                    }
                    
                    int blockX = x + lx;
                    int blockY = surfaceHeight + treeHeight + ly;
                    int blockZ = z + lz;
                    
                    // перевіряємо межі чанку
                    if (blockX >= 0 && blockX < 16 && blockZ >= 0 && blockZ < 16 && blockY < 256) {
                        chunk.setBlock(blockX, blockY, blockZ, type + "_leaves");
                    }
                }
            }
        }
    }
    
    // розміщує структури в чанку
    private void placeStructures(ChunkData chunk) {
        // випадково розміщуємо структури
        if (random.nextDouble() < 0.05) {
            int structureX = random.nextInt(16);
            int structureZ = random.nextInt(16);
            placeStructure(chunk, structureX, structureZ, "village");
        }
    }
    
    // розміщує конкретну структуру
    private void placeStructure(ChunkData chunk, int x, int z, String structureType) {
        switch (structureType) {
            case "village":
                generateVillage(chunk, x, z);
                break;
            // тут можна додати інші типи структур
        }
    }
    
    // генерує просту сільську громаду
    private void generateVillage(ChunkData chunk, int centerX, int centerZ) {
        // знаходимо висоту поверхні
        int surfaceHeight = 0;
        for (int y = 255; y >= 0; y--) {
            if (!"air".equals(chunk.getBlock(centerX, y, centerZ))) {
                surfaceHeight = y;
                break;
            }
        }
        
        // генеруємо центральну площу
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                int blockX = centerX + dx;
                int blockZ = centerZ + dz;
                
                if (blockX >= 0 && blockX < 16 && blockZ >= 0 && blockZ < 16) {
                    // очищуємо терен
                    for (int y = surfaceHeight + 1; y < surfaceHeight + 5; y++) {
                        chunk.setBlock(blockX, y, blockZ, "air");
                    }
                    
                    // ставимо блоки підлоги
                    chunk.setBlock(blockX, surfaceHeight, blockZ, "cobblestone");
                }
            }
        }
        
        // генеруємо кілька будинків
        generateHouse(chunk, centerX - 5, surfaceHeight, centerZ - 5, "north");
        generateHouse(chunk, centerX + 5, surfaceHeight, centerZ + 5, "south");
    }
    
    // генерує простий будинок
    private void generateHouse(ChunkData chunk, int x, int baseHeight, int z, String facing) {
        // стіни будинку
        for (int dx = 0; dx < 5; dx++) {
            for (int dz = 0; dz < 5; dz++) {
                // пропускаємо внутрішні блоки
                if (dx > 0 && dx < 4 && dz > 0 && dz < 4) {
                    continue;
                }
                
                int blockX = x + dx;
                int blockZ = z + dz;
                
                if (blockX >= 0 && blockX < 16 && blockZ >= 0 && blockZ < 16) {
                    // стіни
                    for (int y = 1; y <= 3; y++) {
                        chunk.setBlock(blockX, baseHeight + y, blockZ, "oak_planks");
                    }
                    
                    // дах
                    if (dx == 0 || dx == 4 || dz == 0 || dz == 4) {
                        chunk.setBlock(blockX, baseHeight + 4, blockZ, "oak_planks");
                    }
                }
            }
        }
        
        // двері
        int doorX = x + 2;
        int doorZ = z;
        if (doorX >= 0 && doorX < 16 && doorZ >= 0 && doorZ < 16) {
            chunk.setBlock(doorX, baseHeight + 1, doorZ, "oak_door");
        }
    }
    
    // отримує сід генератора
    public long getSeed() {
        return seed;
    }
}