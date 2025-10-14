package com.sparky.minecraft;

/**
 * Представляє результат крафтингу.
 *
 * @author Андрій Будильников
 */
public class CraftingResult {
    private boolean success;
    private ItemStack resultItem;
    private String message;
    
    public CraftingResult(boolean success, ItemStack resultItem, String message) {
        this.success = success;
        this.resultItem = resultItem;
        this.message = message;
    }
    
    
    public boolean isSuccess() {
        return success;
    }
    
    public ItemStack getResultItem() {
        return resultItem;
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return "CraftingResult{" +
                "success=" + success +
                ", resultItem=" + resultItem +
                ", message='" + message + '\'' +
                '}';
    }
}