package com.sparky.minecraft;

import com.sparky.ecs.Component;
import com.sparky.minecraft.math.Vector3D;

/**
 * Компонент NPC для представлення неігрових персонажів в ECS.
 *
 * @author Андрій Будильников
 */
public class NPCComponent extends Component {
    private String npcId;
    private String npcName;
    private String entityType;
    private Vector3D position;
    private Vector3D rotation;
    private String aiType;
    private String dialogueTreeId;
    private boolean isHostile;
    private int health;
    private int maxHealth;
    private int level;
    private String faction;
    
    public NPCComponent() {
        this("", "", "villager", new Vector3D(0, 0, 0), new Vector3D(0, 0, 0), 
             "passive", "", false, 20, 20, 1, "neutral");
    }
    
    public NPCComponent(String npcId, String npcName, String entityType, 
                       Vector3D position, Vector3D rotation, String aiType, 
                       String dialogueTreeId, boolean isHostile, int health, 
                       int maxHealth, int level, String faction) {
        this.npcId = npcId;
        this.npcName = npcName;
        this.entityType = entityType;
        this.position = position;
        this.rotation = rotation;
        this.aiType = aiType;
        this.dialogueTreeId = dialogueTreeId;
        this.isHostile = isHostile;
        this.health = health;
        this.maxHealth = maxHealth;
        this.level = level;
        this.faction = faction;
    }
    
    /**
     * Перевіряє, чи NPC живий.
     *
     * @return true, якщо NPC живий
     */
    public boolean isAlive() {
        return health > 0;
    }
    
    /**
     * Завдає шкоди NPC.
     *
     * @param damage кількість шкоди
     */
    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }
    
    /**
     * Лікує NPC.
     *
     * @param heal кількість здоров'я для відновлення
     */
    public void heal(int heal) {
        this.health = Math.min(maxHealth, this.health + heal);
    }
    
    /**
     * Переміщує NPC до нової позиції.
     *
     * @param newPosition нова позиція
     */
    public void moveTo(Vector3D newPosition) {
        this.position = new Vector3D(newPosition);
    }
    
    /**
     * Повертає NPC до нової орієнтації.
     *
     * @param newRotation нова орієнтація
     */
    public void rotateTo(Vector3D newRotation) {
        this.rotation = new Vector3D(newRotation);
    }
    
    
    public String getNpcId() {
        return npcId;
    }
    
    public void setNpcId(String npcId) {
        this.npcId = npcId;
    }
    
    public String getNpcName() {
        return npcName;
    }
    
    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }
    
    public String getEntityType() {
        return entityType;
    }
    
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    
    public Vector3D getPosition() {
        return position;
    }
    
    public void setPosition(Vector3D position) {
        this.position = position;
    }
    
    public Vector3D getRotation() {
        return rotation;
    }
    
    public void setRotation(Vector3D rotation) {
        this.rotation = rotation;
    }
    
    public String getAiType() {
        return aiType;
    }
    
    public void setAiType(String aiType) {
        this.aiType = aiType;
    }
    
    public String getDialogueTreeId() {
        return dialogueTreeId;
    }
    
    public void setDialogueTreeId(String dialogueTreeId) {
        this.dialogueTreeId = dialogueTreeId;
    }
    
    public boolean isHostile() {
        return isHostile;
    }
    
    public void setHostile(boolean hostile) {
        isHostile = hostile;
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
        this.health = Math.min(this.health, maxHealth);
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public String getFaction() {
        return faction;
    }
    
    public void setFaction(String faction) {
        this.faction = faction;
    }
    
    @Override
    public String toString() {
        return "NPCComponent{" +
                "npcId='" + npcId + '\'' +
                ", npcName='" + npcName + '\'' +
                ", entityType='" + entityType + '\'' +
                ", position=" + position +
                ", health=" + health +
                ", level=" + level +
                ", faction='" + faction + '\'' +
                '}';
    }
}