package com.sparky.minecraft;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sparky.ecs.Component;
import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;

/**
 * Система для управління крафтингом гравців та сутностей.
 *
 * @author Андрій Будильников
 */
public class CraftingSystem extends com.sparky.ecs.System {
    private EntityManager entityManager;
    
    public CraftingSystem() {
    }
    
    public CraftingSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.hasComponent(CraftingComponent.class)) {
                CraftingComponent crafting = entity.getComponent(CraftingComponent.class);
                processCraftingUpdate(crafting);
            }
        }
    }
    
    /**
     * Обробляє оновлення крафтингу.
     */
    private void processCraftingUpdate(CraftingComponent crafting) {
    }
    
    /**
     * Додає рецепт до сутності.
     *
     * @param entityId ID сутності
     * @param recipe рецепт для додавання
     * @return true, якщо рецепт додано успішно
     */
    public boolean addRecipeToEntity(int entityId, Recipe recipe) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(CraftingComponent.class)) {
            return false;
        }
        
        CraftingComponent crafting = entity.getComponent(CraftingComponent.class);
        crafting.addRecipe(recipe);
        return true;
    }
    
    /**
     * Видаляє рецепт з сутності.
     *
     * @param entityId ID сутності
     * @param recipe рецепт для видалення
     * @return true, якщо рецепт видалено успішно
     */
    public boolean removeRecipeFromEntity(int entityId, Recipe recipe) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(CraftingComponent.class)) {
            return false;
        }
        
        CraftingComponent crafting = entity.getComponent(CraftingComponent.class);
        crafting.removeRecipe(recipe);
        return true;
    }
    
    /**
     * Створює компонент крафтингу для сутності.
     *
     * @param entityId ID сутності
     * @return true, якщо компонент створено успішно
     */
    public boolean createCraftingForEntity(int entityId) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null) {
            return false;
        }
        
        if (entity.hasComponent(CraftingComponent.class)) {
            entity.removeComponent(CraftingComponent.class);
        }
        
        CraftingComponent crafting = new CraftingComponent();
        
        entityManager.addComponentToEntity(entity, crafting);
        
        return true;
    }
    
    /**
     * Виконує крафтинг для сутності.
     *
     * @param entityId ID сутності
     * @param recipe рецепт для крафтингу
     * @return результат крафтингу
     */
    public CraftingResult craftForEntity(int entityId, Recipe recipe) {
        if (entityManager == null) {
            return new CraftingResult(false, null, "Система не ініціалізована");
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null) {
            return new CraftingResult(false, null, "Сутність не знайдена");
        }
        
        if (!entity.hasComponent(CraftingComponent.class)) {
            return new CraftingResult(false, null, "Сутність не має компонента крафтингу");
        }
        
        if (!entity.hasComponent(InventoryComponent.class)) {
            return new CraftingResult(false, null, "Сутність не має інвентарю");
        }
        
        CraftingComponent crafting = entity.getComponent(CraftingComponent.class);
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        
        return crafting.craft(recipe, inventory);
    }
    
    /**
     * Перевіряє, чи сутність знає певний рецепт.
     *
     * @param entityId ID сутності
     * @param recipe рецепт для перевірки
     * @return true, якщо рецепт відомий
     */
    public boolean entityKnowsRecipe(int entityId, Recipe recipe) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(CraftingComponent.class)) {
            return false;
        }
        
        CraftingComponent crafting = entity.getComponent(CraftingComponent.class);
        return crafting.knowsRecipe(recipe);
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(CraftingComponent.class);
        return required;
    }
    
    /**
     * Встановлює менеджер сутностей.
     */
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}