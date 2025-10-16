package com.sparky.minecraft.world;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для системи генерації світу.
 *
 * @author Андрій Будильников
 */
public class WorldGenerationTest {
    
    @Test
    public void testProceduralWorldGeneratorCreation() {
        ProceduralWorldGenerator generator = new ProceduralWorldGenerator(12345);
        assertNotNull(generator);
        System.out.println("ProceduralWorldGenerator created with seed: 12345");
    }
    
    @Test
    public void testChunkDataCreation() {
        ProceduralWorldGenerator generator = new ProceduralWorldGenerator(12345);
        ChunkData chunk = generator.generateChunk(0, 0);
        
        assertNotNull(chunk);
        assertEquals(0, chunk.getChunkX());
        assertEquals(0, chunk.getChunkZ());
        assertNotNull(chunk.getBiome());
        System.out.println("ChunkData generated at (0, 0) with biome: " + chunk.getBiome());
    }
    
    @Test
    public void testMultipleChunkGeneration() {
        ProceduralWorldGenerator generator = new ProceduralWorldGenerator(12345);
        
        // Генеруємо кілька чанків
        ChunkData chunk1 = generator.generateChunk(0, 0);
        ChunkData chunk2 = generator.generateChunk(1, 0);
        ChunkData chunk3 = generator.generateChunk(0, 1);
        
        assertNotNull(chunk1);
        assertNotNull(chunk2);
        assertNotNull(chunk3);
        
        // Перевіряємо, що координати правильні
        assertEquals(0, chunk1.getChunkX());
        assertEquals(0, chunk1.getChunkZ());
        
        assertEquals(1, chunk2.getChunkX());
        assertEquals(0, chunk2.getChunkZ());
        
        assertEquals(0, chunk3.getChunkX());
        assertEquals(1, chunk3.getChunkZ());
        
        System.out.println("Multiple chunks generated successfully");
    }
    
    @Test
    public void testTerrainGenerator() {
        TerrainGenerator terrainGenerator = new TerrainGenerator(12345);
        assertNotNull(terrainGenerator);
        System.out.println("TerrainGenerator created with seed: 12345");
    }
    
    @Test
    public void testBiomeManager() {
        BiomeManager biomeManager = new BiomeManager(12345);
        assertNotNull(biomeManager);
        System.out.println("BiomeManager created with seed: 12345");
    }
}