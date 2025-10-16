package com.sparky.minecraft;

import com.sparky.minecraft.enchantments.EnchantmentSystem.Enchantment;
import java.util.ArrayList;
import java.util.List;

/**
 * Представляє стак предметів в інвентарі Minecraft.
 *
 * @author Андрій Будильников
 */
public class ItemStack {
    private String itemType;
    private int amount;
    private short durability;
    private int maxStackSize;
    private List<Enchantment> enchantments;
    
    public ItemStack() {
        this("air", 1, (short) 0, 64);
    }
    
    public ItemStack(String itemType, int amount) {
        this(itemType, amount, (short) 0, 64);
    }
    
    public ItemStack(String itemType, int amount, short durability) {
        this(itemType, amount, durability, 64);
    }
    
    public ItemStack(String itemType, int amount, short durability, int maxStackSize) {
        this.itemType = itemType;
        this.amount = Math.max(1, amount);
        this.durability = durability;
        this.maxStackSize = Math.max(1, maxStackSize);
        this.enchantments = new ArrayList<>();
    }
    
    /**
     * Додає зачарування до предмета.
     *
     * @param enchantment зачарування для додавання
     */
    public void addEnchantment(Enchantment enchantment) {
        if (enchantment != null) {
            enchantments.add(enchantment);
        }
    }
    
    /**
     * Отримує список зачарувань предмета.
     *
     * @return список зачарувань
     */
    public List<Enchantment> getEnchantments() {
        return new ArrayList<>(enchantments);
    }
    
    /**
     * Перевіряє, чи можна об'єднати два стаки.
     *
     * @param other інший стак для перевірки
     * @return true, якщо стаки можна об'єднати
     */
    public boolean canMergeWith(ItemStack other) {
        if (other == null) {
            return false;
        }
        
        return this.itemType.equals(other.itemType) && 
               this.durability == other.durability;
    }
    
    /**
     * Об'єднує два стаки.
     *
     * @param other інший стак для об'єднання
     */
    public void mergeWith(ItemStack other) {
        if (!canMergeWith(other)) {
            return;
        }
        
        int totalAmount = this.amount + other.amount;
        int newAmount = Math.min(totalAmount, maxStackSize);
        
        this.amount = newAmount;
        other.amount = totalAmount - newAmount;
    }
    
    /**
     * Перевіряє, чи два предмети мають однаковий тип.
     *
     * @param other інший предмет для перевірки
     * @return true, якщо предмети мають однаковий тип
     */
    public boolean isSameType(ItemStack other) {
        if (other == null) {
            return false;
        }
        
        return this.itemType.equals(other.itemType);
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
        this.amount = Math.max(1, Math.min(maxStackSize, amount));
    }
    
    public short getDurability() {
        return durability;
    }
    
    public void setDurability(short durability) {
        this.durability = durability;
    }
    
    public int getMaxStackSize() {
        return maxStackSize;
    }
    
    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = Math.max(1, maxStackSize);
    }
    
    /**
     * Створює копію цього стаку.
     *
     * @return копія стаку
     */
    public ItemStack copy() {
        ItemStack copy = new ItemStack(itemType, amount, durability, maxStackSize);
        // Копіюємо зачарування
        for (Enchantment enchantment : enchantments) {
            copy.addEnchantment(enchantment);
        }
        return copy;
    }
    
    @Override
    public String toString() {
        return "ItemStack{" +
                "itemType='" + itemType + '\'' +
                ", amount=" + amount +
                ", durability=" + durability +
                ", maxStackSize=" + maxStackSize +
                ", enchantments=" + enchantments +
                '}';
    }
}