package com.sparky.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.sparky.ecs.Component;

/**
 * Компонент крафтингу для представлення можливостей крафтингу сутності в ECS.
 *
 * @author Андрій Будильников
 */
public class CraftingComponent extends Component {
    private List<Recipe> knownRecipes;
    private int craftingLevel;
    private int craftingExperience;
    private boolean canCraft;
    
    public CraftingComponent() {
        this.knownRecipes = new ArrayList<>();
        this.craftingLevel = 1;
        this.craftingExperience = 0;
        this.canCraft = true;
    }
    
    /**
     * Додає рецепт до відомих рецептів.
     *
     * @param recipe рецепт для додавання
     */
    public void addRecipe(Recipe recipe) {
        if (!knownRecipes.contains(recipe)) {
            knownRecipes.add(recipe);
        }
    }
    
    /**
     * Видаляє рецепт з відомих рецептів.
     *
     * @param recipe рецепт для видалення
     */
    public void removeRecipe(Recipe recipe) {
        knownRecipes.remove(recipe);
    }
    
    /**
     * Перевіряє, чи сутність знає певний рецепт.
     *
     * @param recipe рецепт для перевірки
     * @return true, якщо рецепт відомий
     */
    public boolean knowsRecipe(Recipe recipe) {
        return knownRecipes.contains(recipe);
    }
    
    /**
     * Отримує список відомих рецептів.
     *
     * @return копія списку відомих рецептів
     */
    public List<Recipe> getKnownRecipes() {
        return new ArrayList<>(knownRecipes);
    }
    
    /**
     * Додає досвід крафтингу.
     *
     * @param experience кількість досвіду для додавання
     */
    public void addCraftingExperience(int experience) {
        this.craftingExperience += experience;
        
        updateLevel();
    }
    
    /**
     * Оновлює рівень крафтингу на основі досвіду.
     */
    private void updateLevel() {
        
        int newLevel = craftingExperience / 100 + 1;
        if (newLevel > craftingLevel) {
            craftingLevel = newLevel;
        }
    }
    
    /**
     * Спроба крафтингу предмета.
     *
     * @param recipe рецепт для крафтингу
     * @param inventory інвентар для використання
     * @return результат крафтингу
     */
    public CraftingResult craft(Recipe recipe, InventoryComponent inventory) {
        if (!canCraft || !knownRecipes.contains(recipe)) {
            return new CraftingResult(false, null, "Крафтинг недоступний або рецепт невідомий");
        }
        
        
        if (!recipe.hasIngredients(inventory)) {
            return new CraftingResult(false, null, "Недостатньо інгредієнтів");
        }
        
        
        recipe.consumeIngredients(inventory);
        
        
        ItemStack result = recipe.getResult().copy();
        
        
        addCraftingExperience(recipe.getExperienceReward());
        
        return new CraftingResult(true, result, "Успішний крафтинг");
    }
    
    
    public int getCraftingLevel() {
        return craftingLevel;
    }
    
    public void setCraftingLevel(int craftingLevel) {
        this.craftingLevel = craftingLevel;
    }
    
    public int getCraftingExperience() {
        return craftingExperience;
    }
    
    public void setCraftingExperience(int craftingExperience) {
        this.craftingExperience = craftingExperience;
        updateLevel();
    }
    
    public boolean isCanCraft() {
        return canCraft;
    }
    
    public void setCanCraft(boolean canCraft) {
        this.canCraft = canCraft;
    }
}