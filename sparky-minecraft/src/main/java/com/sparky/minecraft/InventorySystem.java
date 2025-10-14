package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;
import com.sparky.ecs.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Система для управління інвентарем гравців та сутностей.
 *
 * @author Андрій Будильников
 */
public class InventorySystem extends com.sparky.ecs.System {
    private EntityManager entityManager;
    
    public InventorySystem() {
    }
    
    public InventorySystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.hasComponent(InventoryComponent.class)) {
                InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
                processInventoryUpdate(inventory);
            }
        }
    }
    
    /**
     * Обробляє оновлення інвентарю.
     */
    private void processInventoryUpdate(InventoryComponent inventory) {
    }
    
    /**
     * Додає предмет до інвентарю сутності.
     *
     * @param entityId ID сутності
     * @param slot слот для предмета
     * @param item предмет для додавання
     * @return true, якщо предмет додано успішно
     */
    public boolean addItemToEntity(int entityId, int slot, ItemStack item) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(InventoryComponent.class)) {
            return false;
        }
        
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        return inventory.addItem(slot, item);
    }
    
    /**
     * Видаляє предмет з інвентарю сутності.
     *
     * @param entityId ID сутності
     * @param slot слот для видалення
     * @return видалений предмет або null
     */
    public ItemStack removeItemFromEntity(int entityId, int slot) {
        if (entityManager == null) {
            return null;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(InventoryComponent.class)) {
            return null;
        }
        
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        return inventory.removeItem(slot);
    }
    
    /**
     * Отримує предмет з інвентарю сутності.
     *
     * @param entityId ID сутності
     * @param slot слот для отримання
     * @return предмет або null
     */
    public ItemStack getItemFromEntity(int entityId, int slot) {
        if (entityManager == null) {
            return null;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(InventoryComponent.class)) {
            return null;
        }
        
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        return inventory.getItem(slot);
    }
    
    /**
     * Створює інвентар для сутності.
     *
     * @param entityId ID сутності
     * @param size розмір інвентарю
     * @param type тип інвентарю
     * @return true, якщо інвентар створено успішно
     */
    public boolean createInventoryForEntity(int entityId, int size, String type) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null) {
            return false;
        }
        
        if (entity.hasComponent(InventoryComponent.class)) {
            entity.removeComponent(InventoryComponent.class);
        }
        
        InventoryComponent inventory = new InventoryComponent(size, type);
        
        entityManager.addComponentToEntity(entity, inventory);
        
        return true;
    }
    
    /**
     * Перевіряє, чи сутність має певний предмет.
     *
     * @param entityId ID сутності
     * @param item предмет для перевірки
     * @return true, якщо предмет знайдено
     */
    public boolean entityHasItem(int entityId, ItemStack item) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(InventoryComponent.class)) {
            return false;
        }
        
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        return inventory.containsItem(item);
    }
    
    /**
     * Підраховує кількість певного предмета в інвентарі сутності.
     *
     * @param entityId ID сутності
     * @param item предмет для підрахунку
     * @return кількість предметів
     */
    public int countItemInEntityInventory(int entityId, ItemStack item) {
        if (entityManager == null) {
            return 0;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(InventoryComponent.class)) {
            return 0;
        }
        
        InventoryComponent inventory = entity.getComponent(InventoryComponent.class);
        return inventory.countItem(item);
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(InventoryComponent.class);
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