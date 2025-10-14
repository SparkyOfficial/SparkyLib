package com.sparky.minecraft;

import com.sparky.ecs.Component;

/**
 * Компонент предмету для представлення предметів Minecraft у ECS.
 *
 * @author Андрій Будильников
 */
public class ItemComponent extends Component {
    private String itemType;
    private int amount;
    private short durability;
    private String displayName;
    private String[] lore;
    
    public ItemComponent() {
        this("air", 1, (short) 0, "", new String[0]);
    }
    
    public ItemComponent(String itemType, int amount, short durability, String displayName, String[] lore) {
        this.itemType = itemType;
        this.amount = amount;
        this.durability = durability;
        this.displayName = displayName;
        this.lore = lore != null ? lore : new String[0];
    }
    
    
    public String getItemType() {
        return itemType;
    }
    
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = Math.max(1, amount);
    }
    
    public short getDurability() {
        return durability;
    }
    
    public void setDurability(short durability) {
        this.durability = durability;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String[] getLore() {
        return lore;
    }
    
    public void setLore(String[] lore) {
        this.lore = lore != null ? lore : new String[0];
    }
    
    /**
     * Додає лінію до опису предмету.
     */
    public void addLoreLine(String line) {
        String[] newLore = new String[lore.length + 1];
        System.arraycopy(lore, 0, newLore, 0, lore.length);
        newLore[lore.length] = line;
        this.lore = newLore;
    }
}