package com.sparky.minecraft;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Представляє рецепт крафтингу в Minecraft.
 *
 * @author Андрій Будильников
 */
public class Recipe {
    private String recipeId;
    private ItemStack result;
    private Map<String, Integer> ingredients;
    private int experienceReward;
    private int craftingTime;
    
    public Recipe(String recipeId, ItemStack result) {
        this.recipeId = recipeId;
        this.result = result;
        this.ingredients = new HashMap<>();
        this.experienceReward = 0;
        this.craftingTime = 1000; // 1 секунда за замовчуванням
    }
    
    /**
     * Додає інгредієнт до рецепта.
     *
     * @param itemType тип предмета
     * @param amount кількість
     */
    public void addIngredient(String itemType, int amount) {
        ingredients.put(itemType, ingredients.getOrDefault(itemType, 0) + amount);
    }
    
    /**
     * Перевіряє, чи інвентар містить необхідні інгредієнти.
     *
     * @param inventory інвентар для перевірки
     * @return true, якщо інгредієнти є
     */
    public boolean hasIngredients(InventoryComponent inventory) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            String itemType = entry.getKey();
            int requiredAmount = entry.getValue();
            
            // Підраховуємо кількість цього предмета в інвентарі
            int availableAmount = 0;
            for (ItemStack item : inventory.getItems().values()) {
                if (item != null && item.getItemType().equals(itemType)) {
                    availableAmount += item.getAmount();
                }
            }
            
            if (availableAmount < requiredAmount) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Споживає інгредієнти з інвентарю.
     *
     * @param inventory інвентар для споживання
     */
    public void consumeIngredients(InventoryComponent inventory) {
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            String itemType = entry.getKey();
            int requiredAmount = entry.getValue();
            
            // Споживаємо необхідну кількість предметів
            int remainingAmount = requiredAmount;
            Map<Integer, ItemStack> items = inventory.getItems();
            
            for (Map.Entry<Integer, ItemStack> itemEntry : items.entrySet()) {
                int slot = itemEntry.getKey();
                ItemStack item = itemEntry.getValue();
                
                if (item != null && item.getItemType().equals(itemType)) {
                    if (item.getAmount() <= remainingAmount) {
                        // Видаляємо весь стак
                        remainingAmount -= item.getAmount();
                        inventory.removeItem(slot);
                    } else {
                        // Зменшуємо кількість в стаку
                        item.setAmount(item.getAmount() - remainingAmount);
                        remainingAmount = 0;
                    }
                    
                    if (remainingAmount <= 0) {
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Отримує список інгредієнтів.
     *
     * @return копія мапи інгредієнтів
     */
    public Map<String, Integer> getIngredients() {
        return new HashMap<>(ingredients);
    }
    
    
    public String getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
    
    public ItemStack getResult() {
        return result;
    }
    
    public void setResult(ItemStack result) {
        this.result = result;
    }
    
    public int getExperienceReward() {
        return experienceReward;
    }
    
    public void setExperienceReward(int experienceReward) {
        this.experienceReward = experienceReward;
    }
    
    public int getCraftingTime() {
        return craftingTime;
    }
    
    public void setCraftingTime(int craftingTime) {
        this.craftingTime = craftingTime;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Recipe recipe = (Recipe) obj;
        return recipeId.equals(recipe.recipeId);
    }
    
    @Override
    public int hashCode() {
        return recipeId.hashCode();
    }
    
    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId='" + recipeId + '\'' +
                ", result=" + result +
                ", ingredients=" + ingredients +
                '}';
    }
}