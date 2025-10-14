package com.sparky.minecraft;

import com.sparky.ecs.Component;
import com.sparky.minecraft.math.Vector3D;

/**
 * Компонент сутності Minecraft.
 *
 * @author Андрій Будильников
 */
public class EntityComponent extends Component {
    private String minecraftEntityId;
    private String entityType;
    private Vector3D position;
    private Vector3D rotation;
    private boolean isAlive;
    private double health;
    private double maxHealth;
    private String customName;
    private boolean customNameVisible;

    public EntityComponent() {
        this.minecraftEntityId = "entity_" + System.currentTimeMillis();
        this.entityType = "generic";
        this.position = new Vector3D(0, 0, 0);
        this.rotation = new Vector3D(0, 0, 0);
        this.isAlive = true;
        this.health = 20.0;
        this.maxHealth = 20.0;
        this.customName = "";
        this.customNameVisible = false;
    }

    public EntityComponent(String entityId, String entityType, Vector3D position) {
        this.minecraftEntityId = entityId;
        this.entityType = entityType;
        this.position = new Vector3D(position);
        this.rotation = new Vector3D(0, 0, 0);
        this.isAlive = true;
        this.health = 20.0;
        this.maxHealth = 20.0;
        this.customName = "";
        this.customNameVisible = false;
    }

    
    public String getMinecraftEntityId() {
        return minecraftEntityId;
    }

    public void setMinecraftEntityId(String entityId) {
        this.minecraftEntityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Vector3D getPosition() {
        return new Vector3D(position);
    }

    public void setPosition(Vector3D position) {
        this.position = new Vector3D(position);
    }

    public Vector3D getRotation() {
        return new Vector3D(rotation);
    }

    public void setRotation(Vector3D rotation) {
        this.rotation = new Vector3D(rotation);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.max(0, Math.min(maxHealth, health));
        if (this.health <= 0) {
            isAlive = false;
        }
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
        
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public boolean isCustomNameVisible() {
        return customNameVisible;
    }

    public void setCustomNameVisible(boolean customNameVisible) {
        this.customNameVisible = customNameVisible;
    }

    /**
     * Наносить шкоду сутності.
     */
    public void damage(double amount) {
        if (!isAlive) return;
        
        health -= amount;
        if (health <= 0) {
            health = 0;
            isAlive = false;
        }
    }

    /**
     * Лікує сутність.
     */
    public void heal(double amount) {
        if (!isAlive) return;
        
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Переміщує сутність на вказаний вектор.
     */
    public void move(Vector3D offset) {
        position = position.add(offset);
    }

    /**
     * Обертає сутність.
     */
    public void rotate(Vector3D rotationOffset) {
        rotation = rotation.add(rotationOffset);
    }

    /**
     * Встановлює сутність у певну позицію.
     */
    public void teleport(Vector3D newPosition) {
        position = new Vector3D(newPosition);
    }

    @Override
    public String toString() {
        return "EntityComponent{" +
               "minecraftEntityId='" + minecraftEntityId + '\'' +
               ", entityType='" + entityType + '\'' +
               ", position=" + position +
               ", rotation=" + rotation +
               ", isAlive=" + isAlive +
               ", health=" + health +
               ", maxHealth=" + maxHealth +
               ", customName='" + customName + '\'' +
               ", customNameVisible=" + customNameVisible +
               '}';
    }
}