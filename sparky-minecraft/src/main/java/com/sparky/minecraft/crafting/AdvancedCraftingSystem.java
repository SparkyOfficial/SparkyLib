package com.sparky.minecraft.crafting;

import com.sparky.minecraft.ItemStack;
import com.sparky.minecraft.Recipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Андрій Будильников
 * 
 * Просунута система крафтингу з підтримкою складних рецептів та механік
 */
public class AdvancedCraftingSystem {
    
    // Типи крафтингу
    public enum CraftingType {
        BASIC,        // Базовий крафтинг (2x2 або 3x3 сітка)
        SMELTING,     // Плавлення
        BREWING,      // Варіння зілля
        SMITHING,     // Кузнечне справа
        STONECUTTING, // Різьблення по каменю
        CARTOGRAPHY,  // Картографія
        FLETCHING,    // Виготовлення стріл
        LOOM,         // Ткацький верстат
        GRINDSTONE,   // Точильний камінь
        SMOKING,      // Коптіння
        BLASTING,     // Плавильна піч
        CAMPFIRE,     // Кострище
        STONECUTTER,  // Каменеріз
        SMITHING_TABLE // Стол коваля
    }
    
    // Список всіх рецептів
    private Map<CraftingType, List<Recipe>> recipes;
    private Random random;
    
    // Просунуті механіки крафтингу
    private boolean enableCriticalCrafting;
    private boolean enableCraftingFailures;
    private boolean enableExperienceGain;
    private boolean enableSpecialRecipes;
    
    public AdvancedCraftingSystem() {
        this.recipes = new HashMap<>();
        this.random = new Random();
        
        // Стандартні налаштування механік
        this.enableCriticalCrafting = true;
        this.enableCraftingFailures = false;
        this.enableExperienceGain = true;
        this.enableSpecialRecipes = true;
        
        // Ініціалізуємо рецепти для всіх типів крафтингу
        initializeRecipes();
    }
    
    // Геттери та сеттери для механік крафтингу
    public boolean isEnableCriticalCrafting() {
        return enableCriticalCrafting;
    }
    
    public void setEnableCriticalCrafting(boolean enableCriticalCrafting) {
        this.enableCriticalCrafting = enableCriticalCrafting;
    }
    
    public boolean isEnableCraftingFailures() {
        return enableCraftingFailures;
    }
    
    public void setEnableCraftingFailures(boolean enableCraftingFailures) {
        this.enableCraftingFailures = enableCraftingFailures;
    }
    
    public boolean isEnableExperienceGain() {
        return enableExperienceGain;
    }
    
    public void setEnableExperienceGain(boolean enableExperienceGain) {
        this.enableExperienceGain = enableExperienceGain;
    }
    
    public boolean isEnableSpecialRecipes() {
        return enableSpecialRecipes;
    }
    
    public void setEnableSpecialRecipes(boolean enableSpecialRecipes) {
        this.enableSpecialRecipes = enableSpecialRecipes;
    }
    
    /**
     * Ініціалізує всі рецепти для різних типів крафтингу
     */
    private void initializeRecipes() {
        // Ініціалізуємо списки рецептів для кожного типу
        for (CraftingType type : CraftingType.values()) {
            recipes.put(type, new ArrayList<>());
        }
        
        // Додаємо базові рецепти
        addBasicRecipes();
        addSmeltingRecipes();
        addBrewingRecipes();
        addSmithingRecipes();
        addSpecialRecipes();
    }
    
    /**
     * Додає базові рецепти крафтингу
     */
    private void addBasicRecipes() {
        List<Recipe> basicRecipes = recipes.get(CraftingType.BASIC);
        
        // Додаємо рецепти для інструментів
        basicRecipes.add(new Recipe("WOODEN_PICKAXE", 
            new String[]{"WOOD_PLANKS", "WOOD_PLANKS", "WOOD_PLANKS", 
                         "STICK", "STICK", null, 
                         null, null, null}, 
            new ItemStack("WOODEN_PICKAXE", 1)));
        
        basicRecipes.add(new Recipe("STONE_SWORD", 
            new String[]{"COBBLESTONE", "COBBLESTONE", null, 
                         "STICK", null, null, 
                         "STICK", null, null}, 
            new ItemStack("STONE_SWORD", 1)));
        
        basicRecipes.add(new Recipe("IRON_CHESTPLATE", 
            new String[]{"IRON_INGOT", null, "IRON_INGOT", 
                         "IRON_INGOT", "IRON_INGOT", "IRON_INGOT", 
                         "IRON_INGOT", "IRON_INGOT", "IRON_INGOT"}, 
            new ItemStack("IRON_CHESTPLATE", 1)));
        
        basicRecipes.add(new Recipe("DIAMOND_BLOCK", 
            new String[]{"DIAMOND", "DIAMOND", "DIAMOND", 
                         "DIAMOND", "DIAMOND", "DIAMOND", 
                         "DIAMOND", "DIAMOND", "DIAMOND"}, 
            new ItemStack("DIAMOND_BLOCK", 1)));
        
        basicRecipes.add(new Recipe("STICK", 
            new String[]{"WOOD_PLANKS", null, null, 
                         "WOOD_PLANKS", null, null, 
                         null, null, null}, 
            new ItemStack("STICK", 4)));
        
        // Додаємо рецепти для блоків
        basicRecipes.add(new Recipe("CRAFTING_TABLE", 
            new String[]{"WOOD_PLANKS", "WOOD_PLANKS", null, 
                         "WOOD_PLANKS", "WOOD_PLANKS", null, 
                         null, null, null}, 
            new ItemStack("CRAFTING_TABLE", 1)));
        
        basicRecipes.add(new Recipe("FURNACE", 
            new String[]{"COBBLESTONE", "COBBLESTONE", "COBBLESTONE", 
                         "COBBLESTONE", null, "COBBLESTONE", 
                         "COBBLESTONE", "COBBLESTONE", "COBBLESTONE"}, 
            new ItemStack("FURNACE", 1)));
        
        basicRecipes.add(new Recipe("CHEST", 
            new String[]{"WOOD_PLANKS", "WOOD_PLANKS", "WOOD_PLANKS", 
                         "WOOD_PLANKS", null, "WOOD_PLANKS", 
                         "WOOD_PLANKS", "WOOD_PLANKS", "WOOD_PLANKS"}, 
            new ItemStack("CHEST", 1)));
    }
    
    /**
     * Додає рецепти плавлення
     */
    private void addSmeltingRecipes() {
        List<Recipe> smeltingRecipes = recipes.get(CraftingType.SMELTING);
        
        smeltingRecipes.add(new Recipe("IRON_INGOT", 
            new String[]{"IRON_ORE"}, 
            new ItemStack("IRON_INGOT", 1)));
        
        smeltingRecipes.add(new Recipe("GOLD_INGOT", 
            new String[]{"GOLD_ORE"}, 
            new ItemStack("GOLD_INGOT", 1)));
        
        smeltingRecipes.add(new Recipe("GLASS", 
            new String[]{"SAND"}, 
            new ItemStack("GLASS", 1)));
        
        smeltingRecipes.add(new Recipe("COOKED_BEEF", 
            new String[]{"RAW_BEEF"}, 
            new ItemStack("COOKED_BEEF", 1)));
        
        smeltingRecipes.add(new Recipe("BRICK", 
            new String[]{"CLAY_BALL"}, 
            new ItemStack("BRICK", 1)));
    }
    
    /**
     * Додає рецепти варіння зілля
     */
    private void addBrewingRecipes() {
        List<Recipe> brewingRecipes = recipes.get(CraftingType.BREWING);
        
        brewingRecipes.add(new Recipe("POTION_OF_HEALING", 
            new String[]{"AWKWARD_POTION", "GLISTERING_MELON"}, 
            new ItemStack("POTION_OF_HEALING", 1)));
        
        brewingRecipes.add(new Recipe("POTION_OF_STRENGTH", 
            new String[]{"AWKWARD_POTION", "BLAZE_POWDER"}, 
            new ItemStack("POTION_OF_STRENGTH", 1)));
        
        brewingRecipes.add(new Recipe("POTION_OF_SWIFTNESS", 
            new String[]{"AWKWARD_POTION", "SUGAR"}, 
            new ItemStack("POTION_OF_SWIFTNESS", 1)));
        
        brewingRecipes.add(new Recipe("POTION_OF_SLOWNESS", 
            new String[]{"POTION_OF_SWIFTNESS", "FERMENTED_SPIDER_EYE"}, 
            new ItemStack("POTION_OF_SLOWNESS", 1)));
    }
    
    /**
     * Додає рецепти кузнечного справа
     */
    private void addSmithingRecipes() {
        List<Recipe> smithingRecipes = recipes.get(CraftingType.SMITHING);
        
        smithingRecipes.add(new Recipe("NETHERITE_SWORD", 
            new String[]{"DIAMOND_SWORD", "NETHERITE_INGOT"}, 
            new ItemStack("NETHERITE_SWORD", 1)));
        
        smithingRecipes.add(new Recipe("NETHERITE_PICKAXE", 
            new String[]{"DIAMOND_PICKAXE", "NETHERITE_INGOT"}, 
            new ItemStack("NETHERITE_PICKAXE", 1)));
        
        smithingRecipes.add(new Recipe("NETHERITE_CHESTPLATE", 
            new String[]{"DIAMOND_CHESTPLATE", "NETHERITE_INGOT"}, 
            new ItemStack("NETHERITE_CHESTPLATE", 1)));
    }
    
    /**
     * Додає спеціальні рецепти
     */
    private void addSpecialRecipes() {
        if (!enableSpecialRecipes) return;
        
        List<Recipe> basicRecipes = recipes.get(CraftingType.BASIC);
        
        // Рецепт з секретним результатом
        basicRecipes.add(new Recipe("SECRET_ITEM", 
            new String[]{"GOLD_INGOT", "DIAMOND", "GOLD_INGOT", 
                         "DIAMOND", "EMERALD", "DIAMOND", 
                         "GOLD_INGOT", "DIAMOND", "GOLD_INGOT"}, 
            new ItemStack("RANDOM_TREASURE", 1)));
        
        // Рецепт з кількома можливими результатами
        basicRecipes.add(new Recipe("MYSTERY_BOX", 
            new String[]{"IRON_INGOT", "REDSTONE", "IRON_INGOT", 
                         "REDSTONE", "DIAMOND", "REDSTONE", 
                         "IRON_INGOT", "REDSTONE", "IRON_INGOT"}, 
            new ItemStack("RANDOM_ITEM", 1)));
    }
    
    /**
     * Додає новий рецепт
     * 
     * @param type тип крафтингу
     * @param recipe рецепт для додавання
     */
    public void addRecipe(CraftingType type, Recipe recipe) {
        if (recipes.containsKey(type)) {
            recipes.get(type).add(recipe);
        }
    }
    
    /**
     * Видаляє рецепт
     * 
     * @param type тип крафтингу
     * @param recipeName назва рецепту для видалення
     * @return true, якщо рецепт було видалено, false інакше
     */
    public boolean removeRecipe(CraftingType type, String recipeName) {
        if (recipes.containsKey(type)) {
            List<Recipe> typeRecipes = recipes.get(type);
            return typeRecipes.removeIf(recipe -> recipe.getName().equals(recipeName));
        }
        return false;
    }
    
    /**
     * Знаходить рецепт за типом крафтингу та інгредієнтами
     * 
     * @param type тип крафтингу
     * @param ingredients інгредієнти
     * @return знайдений рецепт або null
     */
    public Recipe findRecipe(CraftingType type, String[] ingredients) {
        if (!recipes.containsKey(type)) {
            return null;
        }
        
        List<Recipe> typeRecipes = recipes.get(type);
        for (Recipe recipe : typeRecipes) {
            if (recipe.matches(ingredients)) {
                return recipe;
            }
        }
        
        return null;
    }
    
    /**
     * Крафтить предмет за рецептом
     * 
     * @param type тип крафтингу
     * @param ingredients інгредієнти
     * @param playerLevel рівень гравця
     * @return результат крафтингу
     */
    public CraftingResult craftItem(CraftingType type, String[] ingredients, int playerLevel) {
        // Знаходимо рецепт
        Recipe recipe = findRecipe(type, ingredients);
        if (recipe == null) {
            return new CraftingResult(false, null, "Recipe not found");
        }
        
        // Перевіряємо, чи є можливість крафтингу провалитись
        if (enableCraftingFailures && random.nextDouble() < 0.1) {
            return new CraftingResult(false, null, "Crafting failed");
        }
        
        // Перевіряємо, чи є критичний крафтинг
        boolean isCritical = false;
        ItemStack result = recipe.getResult();
        
        if (enableCriticalCrafting && random.nextDouble() < 0.05) {
            isCritical = true;
            // Подвоюємо результат при критичному крафтингу
            result = new ItemStack(result.getType(), result.getAmount() * 2);
        }
        
        // Отримуємо досвід за крафтинг
        int experienceGained = 0;
        if (enableExperienceGain) {
            experienceGained = calculateExperienceGain(recipe, playerLevel);
        }
        
        // Створюємо результат крафтингу
        String message = isCritical ? "Critical craft! " : "";
        message += "Successfully crafted " + result.getType();
        
        return new CraftingResult(true, result, message, experienceGained, isCritical);
    }
    
    /**
     * Обчислює отриманий досвід за крафтинг
     * 
     * @param recipe рецепт
     * @param playerLevel рівень гравця
     * @return кількість отриманого досвіду
     */
    private int calculateExperienceGain(Recipe recipe, int playerLevel) {
        // Базова кількість досвіду
        int baseExp = recipe.getResult().getAmount();
        
        // Модифікатор складності рецепту
        int complexityModifier = recipe.getIngredients().length;
        
        // Модифікатор рівня гравця (чим нижче рівень, тим більше досвіду)
        double levelModifier = Math.max(0.5, 2.0 - (playerLevel / 50.0));
        
        return (int) (baseExp * complexityModifier * levelModifier);
    }
    
    /**
     * Отримує всі рецепти певного типу
     * 
     * @param type тип крафтингу
     * @return список рецептів
     */
    public List<Recipe> getRecipesByType(CraftingType type) {
        return new ArrayList<>(recipes.getOrDefault(type, new ArrayList<>()));
    }
    
    /**
     * Отримує всі доступні рецепти
     * 
     * @return мапа всіх рецептів
     */
    public Map<CraftingType, List<Recipe>> getAllRecipes() {
        return new HashMap<>(recipes);
    }
    
    /**
     * Перевіряє, чи можна крафтити з заданими інгредієнтами
     * 
     * @param type тип крафтингу
     * @param ingredients інгредієнти
     * @return true, якщо можна крафтити, false інакше
     */
    public boolean canCraft(CraftingType type, String[] ingredients) {
        return findRecipe(type, ingredients) != null;
    }
    
    /**
     * Отримує випадковий предмет як результат секретного рецепту
     * 
     * @return випадковий предмет
     */
    private ItemStack getRandomTreasure() {
        String[] treasures = {
            "DIAMOND", "EMERALD", "GOLD_INGOT", "IRON_INGOT", 
            "ENCHANTED_BOOK", "NETHER_STAR", "ELYTRA", "TOTEM_OF_UNDYING"
        };
        
        String treasure = treasures[random.nextInt(treasures.length)];
        int amount = random.nextInt(3) + 1; // 1-3 предмети
        
        return new ItemStack(treasure, amount);
    }
    
    /**
     * Отримує випадковий предмет для mystery box
     * 
     * @return випадковий предмет
     */
    private ItemStack getRandomItem() {
        String[] items = {
            "APPLE", "BREAD", "COOKED_CHICKEN", "IRON_SWORD", 
            "BOW", "ARROW", "GOLDEN_APPLE", "ENCHANTED_GOLDEN_APPLE"
        };
        
        String item = items[random.nextInt(items.length)];
        int amount = random.nextInt(5) + 1; // 1-5 предметів
        
        return new ItemStack(item, amount);
    }
    
    /**
     * Представляє результат крафтингу
     */
    public static class CraftingResult {
        private boolean success;
        private ItemStack result;
        private String message;
        private int experienceGained;
        private boolean isCritical;
        
        public CraftingResult(boolean success, ItemStack result, String message) {
            this(success, result, message, 0, false);
        }
        
        public CraftingResult(boolean success, ItemStack result, String message, int experienceGained, boolean isCritical) {
            this.success = success;
            this.result = result;
            this.message = message;
            this.experienceGained = experienceGained;
            this.isCritical = isCritical;
        }
        
        // Геттери
        public boolean isSuccess() {
            return success;
        }
        
        public ItemStack getResult() {
            return result;
        }
        
        public String getMessage() {
            return message;
        }
        
        public int getExperienceGained() {
            return experienceGained;
        }
        
        public boolean isCritical() {
            return isCritical;
        }
    }
}