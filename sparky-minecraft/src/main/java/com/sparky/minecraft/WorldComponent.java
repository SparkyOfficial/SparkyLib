package com.sparky.minecraft;

import com.sparky.ecs.Component;
import com.sparky.minecraft.math.Vector3D;

/**
 * Компонент світу для Minecraft.
 *
 * @author Андрій Будильников
 */
public class WorldComponent extends Component {
    private String worldName;
    private Vector3D spawnPoint;
    private long time;
    private boolean isRaining;
    private boolean isThundering;
    private double rainStrength;
    private double thunderStrength;

    public WorldComponent() {
        this.worldName = "world";
        this.spawnPoint = new Vector3D(0, 64, 0);
        this.time = 0;
        this.isRaining = false;
        this.isThundering = false;
        this.rainStrength = 0.0;
        this.thunderStrength = 0.0;
    }

    public WorldComponent(String worldName, Vector3D spawnPoint) {
        this.worldName = worldName;
        this.spawnPoint = new Vector3D(spawnPoint);
        this.time = 0;
        this.isRaining = false;
        this.isThundering = false;
        this.rainStrength = 0.0;
        this.thunderStrength = 0.0;
    }

    
    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public Vector3D getSpawnPoint() {
        return new Vector3D(spawnPoint);
    }

    public void setSpawnPoint(Vector3D spawnPoint) {
        this.spawnPoint = new Vector3D(spawnPoint);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public boolean isThundering() {
        return isThundering;
    }

    public void setThundering(boolean thundering) {
        isThundering = thundering;
    }

    public double getRainStrength() {
        return rainStrength;
    }

    public void setRainStrength(double rainStrength) {
        this.rainStrength = Math.max(0.0, Math.min(1.0, rainStrength)); // Обмежуємо між 0 та 1
    }

    public double getThunderStrength() {
        return thunderStrength;
    }

    public void setThunderStrength(double thunderStrength) {
        this.thunderStrength = Math.max(0.0, Math.min(1.0, thunderStrength)); // Обмежуємо між 0 та 1
    }

    /**
     * Оновлює час світу.
     */
    public void updateTime(long deltaTime) {
        this.time += deltaTime;
    }

    /**
     * Оновлює погоду.
     */
    public void updateWeather(double deltaTime) {
        if (isRaining) {
            rainStrength = Math.min(1.0, rainStrength + deltaTime * 0.01);
        } else {
            rainStrength = Math.max(0.0, rainStrength - deltaTime * 0.01);
        }

        if (isThundering) {
            thunderStrength = Math.min(1.0, thunderStrength + deltaTime * 0.005);
        } else {
            thunderStrength = Math.max(0.0, thunderStrength - deltaTime * 0.005);
        }
    }

    @Override
    public String toString() {
        return "WorldComponent{" +
               "worldName='" + worldName + '\'' +
               ", spawnPoint=" + spawnPoint +
               ", time=" + time +
               ", isRaining=" + isRaining +
               ", isThundering=" + isThundering +
               ", rainStrength=" + rainStrength +
               ", thunderStrength=" + thunderStrength +
               '}';
    }
}