package com.sparky.minecraft;

import com.sparky.ecs.Component;

/**
 * Компонент блоку для представлення блоків Minecraft у ECS.
 *
 * @author Андрій Будильников
 */
public class BlockComponent extends Component {
    private String blockType;
    private int x, y, z;
    private String worldName;
    
    public BlockComponent() {
        this("", 0, 0, 0, "world");
    }
    
    public BlockComponent(String blockType, int x, int y, int z, String worldName) {
        this.blockType = blockType;
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
    }
    
    public String getBlockType() {
        return blockType;
    }
    
    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getZ() {
        return z;
    }
    
    public void setZ(int z) {
        this.z = z;
    }
    
    public String getWorldName() {
        return worldName;
    }
    
    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }
}