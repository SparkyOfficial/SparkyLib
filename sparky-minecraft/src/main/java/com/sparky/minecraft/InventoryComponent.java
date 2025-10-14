package com.sparky.minecraft;

import com.sparky.ecs.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * Компонент інвентарю для представлення інвентарю гравця або сутності в ECS.
 *
 * @author Андрій Будильников
 */
public class InventoryComponent extends Component {
    private Map<Integer, ItemStack> items;
    private int size;
    private String inventoryType;
    
    public InventoryComponent() {
        this(36, "player"); // Стандартний інвентар гравця Minecraft
    }
    
    public InventoryComponent(int size, String inventoryType) {
        this.items = new HashMap<>();
        this.size = size;
        this.inventoryType = inventoryType;
    }
    
    /**
     * Додає предмет до інвентарю.
     *
     * @param slot слот для предмета
     * @param item предмет для додавання
     * @return true, якщо предмет додано успішно
     */
    public boolean addItem(int slot, ItemStack item) {
        if (slot < 0 || slot >= size) {
            return false;
        }
        
        if (items.containsKey(slot) && items.get(slot) != null) {
            // Якщо в слоті вже є предмет, намагаємось об'єднати стаки
            ItemStack existingItem = items.get(slot);
            if (existingItem.canMergeWith(item)) {
                existingItem.mergeWith(item);
                return true;
            } else {
                return false;
            }
        } else {
            // Слот порожній, просто додаємо предмет
            items.put(slot, item);
            return true;
        }
    }
    
    /**
     * Видаляє предмет з інвентарю.
     *
     * @param slot слот для видалення
     * @return видалений предмет або null, якщо слот був порожнім
     */
    public ItemStack removeItem(int slot) {
        if (slot < 0 || slot >= size) {
            return null;
        }
        
        return items.remove(slot);
    }
    
    /**
     * Отримує предмет з інвентарю.
     *
     * @param slot слот для отримання
     * @return предмет або null, якщо слот порожній
     */
    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= size) {
            return null;
        }
        
        return items.get(slot);
    }
    
    /**
     * Перевіряє, чи інвентар містить певний предмет.
     *
     * @param item предмет для пошуку
     * @return true, якщо предмет знайдено
     */
    public boolean containsItem(ItemStack item) {
        for (ItemStack stack : items.values()) {
            if (stack != null && stack.isSameType(item)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Підраховує кількість певного предмета в інвентарі.
     *
     * @param item предмет для підрахунку
     * @return загальна кількість предметів
     */
    public int countItem(ItemStack item) {
        int count = 0;
        for (ItemStack stack : items.values()) {
            if (stack != null && stack.isSameType(item)) {
                count += stack.getAmount();
            }
        }
        return count;
    }
    
    /**
     * Отримує розмір інвентарю.
     *
     * @return розмір інвентарю
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Встановлює розмір інвентарю.
     *
     * @param size новий розмір інвентарю
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * Отримує тип інвентарю.
     *
     * @return тип інвентарю
     */
    public String getInventoryType() {
        return inventoryType;
    }
    
    /**
     * Встановлює тип інвентарю.
     *
     * @param inventoryType новий тип інвентарю
     */
    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }
    
    /**
     * Отримує кількість заповнених слотів.
     *
     * @return кількість заповнених слотів
     */
    public int getFilledSlots() {
        return items.size();
    }
    
    /**
     * Перевіряє, чи інвентар повний.
     *
     * @return true, якщо інвентар повний
     */
    public boolean isFull() {
        return items.size() >= size;
    }
    
    /**
     * Перевіряє, чи інвентар порожній.
     *
     * @return true, якщо інвентар порожній
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Очищує інвентар.
     */
    public void clear() {
        items.clear();
    }
    
    /**
     * Отримує всі предмети в інвентарі.
     *
     * @return копія мапи предметів
     */
    public Map<Integer, ItemStack> getItems() {
        return new HashMap<>(items);
    }
}