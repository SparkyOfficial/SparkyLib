package com.sparky.minecraft.world;

/**
 * Представляє дані чанку в світі Minecraft.
 *
 * @author Андрій Будильников
 */
public class ChunkData {
    private int chunkX;
    private int chunkZ;
    private String[][][] blocks;
    private String biome;
    
    public ChunkData(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.blocks = new String[16][256][16];
        this.biome = "plains";
        
        // ініціалізуємо всі блоки як повітря
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    blocks[x][y][z] = "air";
                }
            }
        }
    }
    
    // отримує блок за координатами
    public String getBlock(int x, int y, int z) {
        if (x < 0 || x >= 16 || y < 0 || y >= 256 || z < 0 || z >= 16) {
            return "air";
        }
        return blocks[x][y][z];
    }
    
    // встановлює блок за координатами
    public void setBlock(int x, int y, int z, String blockType) {
        if (x >= 0 && x < 16 && y >= 0 && y < 256 && z >= 0 && z < 16) {
            blocks[x][y][z] = blockType;
        }
    }
    
    // отримує координату x чанку
    public int getChunkX() {
        return chunkX;
    }
    
    // отримує координату z чанку
    public int getChunkZ() {
        return chunkZ;
    }
    
    // отримує біом чанку
    public String getBiome() {
        return biome;
    }
    
    // встановлює біом чанку
    public void setBiome(String biome) {
        this.biome = biome;
    }
}