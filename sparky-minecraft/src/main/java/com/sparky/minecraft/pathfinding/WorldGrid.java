package com.sparky.minecraft.pathfinding;

import java.util.HashMap;
import java.util.Map;

import com.sparky.minecraft.math.Vector3D;

/**
 * Сітка світу для пошуку шляхів.
 *
 * @author Андрій Будильников
 */
public class WorldGrid {
    private Map<String, Boolean> walkableBlocks;
    
    public WorldGrid() {
        this.walkableBlocks = new HashMap<>();
        initializeDefaultWalkableBlocks();
    }
    
    // ініціалізує стандартні прохідні блоки
    private void initializeDefaultWalkableBlocks() {
        // повітря завжди прохідне
        walkableBlocks.put("air", true);
        
        // рідина не прохідна
        walkableBlocks.put("water", false);
        walkableBlocks.put("lava", false);
        
        // тверді блоки не прохідні
        walkableBlocks.put("stone", false);
        walkableBlocks.put("dirt", false);
        walkableBlocks.put("grass", false);
        walkableBlocks.put("sand", false);
        walkableBlocks.put("gravel", false);
        
        // деякі блоки прохідні
        walkableBlocks.put("torch", true);
        walkableBlocks.put("flower", true);
        walkableBlocks.put("grass_tall", true);
    }
    
    // перевіряє, чи позиція прохідна
    public boolean isWalkable(Vector3D position) {
        // в межах світу?
        if (position.getY() < 0 || position.getY() >= 256) {
            return false;
        }
        
        // тут була б перевірка реальних блоків у світі
        // для прикладу просто повертаємо true
        return true;
    }
    
    // перевіряє, чи блок прохідний
    public boolean isBlockWalkable(String blockType) {
        return walkableBlocks.getOrDefault(blockType, false);
    }
    
    // встановлює прохідність блоку
    public void setBlockWalkable(String blockType, boolean walkable) {
        walkableBlocks.put(blockType, walkable);
    }
}