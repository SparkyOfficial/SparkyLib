package com.sparky.minecraft;

import com.sparky.ecs.Component;

/**
 * Компонент гравця для представлення гравців Minecraft у ECS.
 *
 * @author Андрій Будильников
 */
public class PlayerComponent extends Component {
    private String playerName;
    private String uuid;
    private int health;
    private int maxHealth;
    private int foodLevel;
    private int experienceLevel;
    private float experienceProgress;
    private boolean isOnline;
    
    public PlayerComponent() {
        this("", "", 20, 20, 20, 0, 0.0f, false);
    }
    
    public PlayerComponent(String playerName, String uuid, int health, int maxHealth, 
                          int foodLevel, int experienceLevel, float experienceProgress, boolean isOnline) {
        this.playerName = playerName;
        this.uuid = uuid;
        this.health = health;
        this.maxHealth = maxHealth;
        this.foodLevel = foodLevel;
        this.experienceLevel = experienceLevel;
        this.experienceProgress = experienceProgress;
        this.isOnline = isOnline;
    }
    
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(maxHealth, health));
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    
    public int getFoodLevel() {
        return foodLevel;
    }
    
    public void setFoodLevel(int foodLevel) {
        this.foodLevel = Math.max(0, Math.min(20, foodLevel));
    }
    
    public int getExperienceLevel() {
        return experienceLevel;
    }
    
    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    
    public float getExperienceProgress() {
        return experienceProgress;
    }
    
    public void setExperienceProgress(float experienceProgress) {
        this.experienceProgress = Math.max(0.0f, Math.min(1.0f, experienceProgress));
    }
    
    public boolean isOnline() {
        return isOnline;
    }
    
    public void setOnline(boolean online) {
        isOnline = online;
    }
}