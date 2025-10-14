package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.minecraft.math.Vector3D;

/**
 * Розширена демонстрація бібліотеки Sparky для Minecraft з новими компонентами та системами.
 *
 * @author Андрій Будильников
 */
public class MinecraftExtendedDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Розширена демонстрація бібліотеки Sparky для Minecraft ===\n");
        
        // Створюємо менеджер сутностей
        EntityManager entityManager = new EntityManager();
        
        // Демонстрація інвентарю
        demonstrateInventorySystem(entityManager);
        
        System.out.println();
        
        // Демонстрація крафтингу
        demonstrateCraftingSystem(entityManager);
        
        System.out.println();
        
        // Демонстрація квестів
        demonstrateQuestSystem(entityManager);
        
        System.out.println();
        
        // Демонстрація NPC
        demonstrateNPCSystem(entityManager);
        
        System.out.println();
        
        // Демонстрація погоди
        demonstrateWeatherSystem(entityManager);
        
        System.out.println("\n=== Демонстрація завершена ===");
    }
    
    /**
     * Демонстрація системи інвентарю.
     */
    private static void demonstrateInventorySystem(EntityManager entityManager) {
        System.out.println("1. Система інвентарю:");
        
        // Створюємо систему інвентарю
        InventorySystem inventorySystem = new InventorySystem(entityManager);
        inventorySystem.setEntityManager(entityManager);
        
        // Створюємо сутність гравця
        Entity playerEntity = entityManager.createEntity();
        System.out.println("  1.1. Створено сутність гравця з ID: " + playerEntity.getId());
        
        // Створюємо інвентар для гравця
        boolean inventoryCreated = inventorySystem.createInventoryForEntity(playerEntity.getId(), 36, "player");
        System.out.println("  1.2. Інвентар створено: " + inventoryCreated);
        
        // Створюємо предмети
        ItemStack sword = new ItemStack("diamond_sword", 1, (short) 1561);
        ItemStack apples = new ItemStack("apple", 5);
        ItemStack blocks = new ItemStack("cobblestone", 64);
        
        System.out.println("  1.3. Створено предмети:");
        System.out.println("    - " + sword);
        System.out.println("    - " + apples);
        System.out.println("    - " + blocks);
        
        // Додаємо предмети до інвентарю
        boolean swordAdded = inventorySystem.addItemToEntity(playerEntity.getId(), 0, sword);
        boolean applesAdded = inventorySystem.addItemToEntity(playerEntity.getId(), 1, apples);
        boolean blocksAdded = inventorySystem.addItemToEntity(playerEntity.getId(), 2, blocks);
        
        System.out.println("  1.4. Предмети додано до інвентарю:");
        System.out.println("    - Меч: " + swordAdded);
        System.out.println("    - Яблука: " + applesAdded);
        System.out.println("    - Блоки: " + blocksAdded);
        
        // Перевіряємо наявність предметів
        boolean hasApples = inventorySystem.entityHasItem(playerEntity.getId(), new ItemStack("apple", 1));
        int appleCount = inventorySystem.countItemInEntityInventory(playerEntity.getId(), new ItemStack("apple", 1));
        
        System.out.println("  1.5. Перевірка предметів:");
        System.out.println("    - Має яблука: " + hasApples);
        System.out.println("    - Кількість яблук: " + appleCount);
    }
    
    /**
     * Демонстрація системи крафтингу.
     */
    private static void demonstrateCraftingSystem(EntityManager entityManager) {
        System.out.println("2. Система крафтингу:");
        
        // Створюємо систему крафтингу
        CraftingSystem craftingSystem = new CraftingSystem(entityManager);
        craftingSystem.setEntityManager(entityManager);
        
        // Створюємо сутність гравця
        Entity playerEntity = entityManager.createEntity();
        System.out.println("  2.1. Створено сутність гравця з ID: " + playerEntity.getId());
        
        // Створюємо компонент крафтингу для гравця
        boolean craftingCreated = craftingSystem.createCraftingForEntity(playerEntity.getId());
        System.out.println("  2.2. Компонент крафтингу створено: " + craftingCreated);
        
        // Створюємо рецепт
        Recipe stickRecipe = new Recipe("stick", new ItemStack("stick", 4));
        stickRecipe.addIngredient("planks", 2);
        stickRecipe.setExperienceReward(1);
        
        System.out.println("  2.3. Створено рецепт: " + stickRecipe.getRecipeId());
        System.out.println("    - Результат: " + stickRecipe.getResult());
        System.out.println("    - Інгредієнти: " + stickRecipe.getIngredients());
        
        // Додаємо рецепт до гравця
        boolean recipeAdded = craftingSystem.addRecipeToEntity(playerEntity.getId(), stickRecipe);
        System.out.println("  2.4. Рецепт додано до гравця: " + recipeAdded);
        
        // Перевіряємо, чи гравець знає рецепт
        boolean knowsRecipe = craftingSystem.entityKnowsRecipe(playerEntity.getId(), stickRecipe);
        System.out.println("  2.5. Гравець знає рецепт: " + knowsRecipe);
    }
    
    /**
     * Демонстрація системи квестів.
     */
    private static void demonstrateQuestSystem(EntityManager entityManager) {
        System.out.println("3. Система квестів:");
        
        // Створюємо систему квестів
        QuestSystem questSystem = new QuestSystem(entityManager);
        questSystem.setEntityManager(entityManager);
        
        // Створюємо сутність гравця
        Entity playerEntity = entityManager.createEntity();
        System.out.println("  3.1. Створено сутність гравця з ID: " + playerEntity.getId());
        
        // Створюємо компонент квестів для гравця
        boolean questsCreated = questSystem.createQuestsForEntity(playerEntity.getId());
        System.out.println("  3.2. Компонент квестів створено: " + questsCreated);
        
        // Створюємо квест
        Quest miningQuest = new Quest("mine_stone", "Добути камінь", "Добудьте 10 блоків каменю");
        miningQuest.setRequiredProgress(10);
        miningQuest.setRewardPoints(5);
        miningQuest.addReward(new ItemStack("torch", 5));
        
        System.out.println("  3.3. Створено квест: " + miningQuest.getQuestName());
        System.out.println("    - Опис: " + miningQuest.getDescription());
        System.out.println("    - Необхідний прогрес: " + miningQuest.getRequiredProgress());
        System.out.println("    - Нагороди: " + miningQuest.getRewards().length + " предметів");
        
        // Додаємо квест до гравця
        boolean questAdded = questSystem.addQuestToEntity(playerEntity.getId(), miningQuest);
        System.out.println("  3.4. Квест додано до гравця: " + questAdded);
        
        // Оновлюємо прогрес квесту
        boolean progressUpdated = questSystem.updateQuestProgress(playerEntity.getId(), miningQuest, 7);
        System.out.println("  3.5. Прогрес квесту оновлено: " + progressUpdated);
        
        // Перевіряємо прогрес
        int progress = questSystem.getQuestProgressForEntity(playerEntity.getId(), miningQuest);
        System.out.println("  3.6. Поточний прогрес квесту: " + progress + "/" + miningQuest.getRequiredProgress());
        
        // Перевіряємо, чи квест активний
        boolean isQuestActive = questSystem.isQuestActiveForEntity(playerEntity.getId(), miningQuest);
        System.out.println("  3.7. Квест активний: " + isQuestActive);
    }
    
    /**
     * Демонстрація системи NPC.
     */
    private static void demonstrateNPCSystem(EntityManager entityManager) {
        System.out.println("4. Система NPC:");
        
        // Створюємо систему NPC
        NPCSystem npcSystem = new NPCSystem(entityManager);
        npcSystem.setEntityManager(entityManager);
        
        // Створюємо NPC
        Entity npcEntity = npcSystem.createNPC("villager_1", "Торговець", "villager", 
                                              new Vector3D(10, 64, 10), new Vector3D(0, 0, 0));
        System.out.println("  4.1. Створено NPC з ID: " + npcEntity.getId());
        
        // Отримуємо компонент NPC
        NPCComponent npcComponent = npcEntity.getComponent(NPCComponent.class);
        System.out.println("  4.2. NPC створено:");
        System.out.println("    - ID: " + npcComponent.getNpcId());
        System.out.println("    - Ім'я: " + npcComponent.getNpcName());
        System.out.println("    - Тип: " + npcComponent.getEntityType());
        System.out.println("    - Позиція: " + npcComponent.getPosition());
        System.out.println("    - Здоров'я: " + npcComponent.getHealth() + "/" + npcComponent.getMaxHealth());
        
        // Завдаємо шкоди NPC
        boolean damageDone = npcSystem.damageNPC(npcEntity.getId(), 5);
        System.out.println("  4.3. Завдано шкоди NPC: " + damageDone);
        System.out.println("    - Нове здоров'я: " + npcComponent.getHealth());
        
        // Лікуємо NPC
        boolean healed = npcSystem.healNPC(npcEntity.getId(), 3);
        System.out.println("  4.4. Лікування NPC: " + healed);
        System.out.println("    - Нове здоров'я: " + npcComponent.getHealth());
        
        // Перевіряємо, чи NPC живий
        boolean isAlive = npcSystem.isNPCAlive(npcEntity.getId());
        System.out.println("  4.5. NPC живий: " + isAlive);
    }
    
    /**
     * Демонстрація системи погоди.
     */
    private static void demonstrateWeatherSystem(EntityManager entityManager) {
        System.out.println("5. Система погоди:");
        
        // Створюємо систему погоди
        WeatherSystem weatherSystem = new WeatherSystem(entityManager);
        weatherSystem.setEntityManager(entityManager);
        
        // Створюємо сутність світу
        Entity worldEntity = entityManager.createEntity();
        System.out.println("  5.1. Створено сутність світу з ID: " + worldEntity.getId());
        
        // Створюємо компонент погоди для світу
        boolean weatherCreated = weatherSystem.createWeatherForWorld(worldEntity);
        System.out.println("  5.2. Компонент погоди створено: " + weatherCreated);
        
        // Отримуємо компонент погоди
        WeatherComponent weatherComponent = worldEntity.getComponent(WeatherComponent.class);
        System.out.println("  5.3. Поточна погода:");
        System.out.println("    - Тип: " + weatherComponent.getCurrentWeather());
        System.out.println("    - Інтенсивність: " + weatherComponent.getIntensity());
        System.out.println("    - Час до закінчення: " + weatherComponent.getTimeRemaining() + " мс");
        
        // Змінюємо погоду
        boolean weatherChanged = weatherSystem.changeWeather(worldEntity.getId(), 
                                                           WeatherComponent.WeatherType.RAIN, 
                                                           0.8f, 300000);
        System.out.println("  5.4. Погоду змінено: " + weatherChanged);
        System.out.println("    - Нова погода: " + weatherComponent.getCurrentWeather());
        System.out.println("    - Нова інтенсивність: " + weatherComponent.getIntensity());
        
        // Перевіряємо, чи іде дощ
        boolean isRaining = weatherSystem.isRaining(worldEntity.getId());
        System.out.println("  5.5. Зараз іде дощ: " + isRaining);
    }
}