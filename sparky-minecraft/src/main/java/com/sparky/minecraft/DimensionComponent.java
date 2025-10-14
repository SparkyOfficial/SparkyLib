package com.sparky.minecraft;

import com.sparky.ecs.Component;

/**
 * Компонент для представлення виміру (світу) в Minecraft.
 * Використовується для управління різними вимірами, такими як Overworld, Nether, End.
 *
 * @author Богдан Кравчук
 */
public class DimensionComponent extends Component {
    public enum DimensionType {
        OVERWORLD,
        NETHER,
        END
    }
    
    private DimensionType dimensionType;
    private String dimensionName;
    private long seed;
    private boolean hasSkyLight;
    private int minHeight;
    private int maxHeight;
    private String generatorType;
    
    public DimensionComponent() {
        this(DimensionType.OVERWORLD, "overworld", 0L, true, -64, 320, "default");
    }
    
    public DimensionComponent(DimensionType dimensionType, String dimensionName, long seed, 
                             boolean hasSkyLight, int minHeight, int maxHeight, String generatorType) {
        this.dimensionType = dimensionType;
        this.dimensionName = dimensionName;
        this.seed = seed;
        this.hasSkyLight = hasSkyLight;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.generatorType = generatorType;
    }
    
    public DimensionType getDimensionType() {
        return dimensionType;
    }
    
    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }
    
    public String getDimensionName() {
        return dimensionName;
    }
    
    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }
    
    public long getSeed() {
        return seed;
    }
    
    public void setSeed(long seed) {
        this.seed = seed;
    }
    
    public boolean hasSkyLight() {
        return hasSkyLight;
    }
    
    public void setHasSkyLight(boolean hasSkyLight) {
        this.hasSkyLight = hasSkyLight;
    }
    
    public int getMinHeight() {
        return minHeight;
    }
    
    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }
    
    public int getMaxHeight() {
        return maxHeight;
    }
    
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
    
    public String getGeneratorType() {
        return generatorType;
    }
    
    public void setGeneratorType(String generatorType) {
        this.generatorType = generatorType;
    }
    
    /**
     * Перевіряє, чи позиція знаходиться в межах виміру.
     *
     * @param y координата Y
     * @return true, якщо позиція в межах виміру
     */
    public boolean isWithinBounds(int y) {
        return y >= minHeight && y <= maxHeight;
    }
    
    /**
     * Отримує висоту виміру.
     *
     * @return висота виміру
     */
    public int getHeight() {
        return maxHeight - minHeight;
    }
}