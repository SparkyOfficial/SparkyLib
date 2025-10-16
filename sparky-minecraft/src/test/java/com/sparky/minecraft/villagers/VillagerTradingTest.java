package com.sparky.minecraft.villagers;

import com.sparky.minecraft.NPCComponent;
import com.sparky.minecraft.ItemStack;
import com.sparky.minecraft.villagers.VillagerTradingSystem.VillagerProfession;
import com.sparky.minecraft.villagers.VillagerTradingSystem.VillagerLevel;
import com.sparky.minecraft.villagers.VillagerTradingSystem.VillagerTrade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для системи торгівлі з селянами.
 *
 * @author Андрій Будильников
 */
public class VillagerTradingTest {
    
    private VillagerTradingSystem tradingSystem;
    private NPCComponent villager;
    
    @BeforeEach
    public void setUp() {
        tradingSystem = new VillagerTradingSystem();
        villager = new NPCComponent();
    }
    
    @Test
    public void testAddVillager() {
        // додаємо селянина
        tradingSystem.addVillager(villager, VillagerProfession.FARMER);
        
        // перевіряємо, чи селянин доданий
        assertNotNull(tradingSystem.getVillagerData(villager));
        assertEquals(VillagerProfession.FARMER, tradingSystem.getVillagerData(villager).getProfession());
    }
    
    @Test
    public void testRemoveVillager() {
        // додаємо селянина
        tradingSystem.addVillager(villager, VillagerProfession.FARMER);
        
        // перевіряємо, чи селянин доданий
        assertNotNull(tradingSystem.getVillagerData(villager));
        
        // видаляємо селянина
        tradingSystem.removeVillager(villager);
        
        // перевіряємо, чи селянин видалений
        assertNull(tradingSystem.getVillagerData(villager));
    }
    
    @Test
    public void testExecuteTrade() {
        // додаємо селянина
        tradingSystem.addVillager(villager, VillagerProfession.FARMER);
        
        // отримуємо дані селянина
        var villagerData = tradingSystem.getVillagerData(villager);
        
        // перевіряємо, що є торгівлі
        assertFalse(villagerData.getTrades().isEmpty());
        
        // виконуємо першу торгівлю
        boolean result = tradingSystem.executeTrade(villager, 0);
        
        // перевіряємо, що торгівля успішна
        assertTrue(result);
        
        // перевіряємо, що торгівля використана
        assertEquals(1, villagerData.getTrades().get(0).getUses());
    }
    
    @Test
    public void testLevelUp() {
        // додаємо селянина
        tradingSystem.addVillager(villager, VillagerProfession.FARMER);
        
        // отримуємо дані селянина
        var villagerData = tradingSystem.getVillagerData(villager);
        
        // перевіряємо початковий рівень
        assertEquals(VillagerLevel.NOVICE, villagerData.getLevel());
        
        // додаємо досвід
        villagerData.addXp(10);
        
        // перевіряємо новий рівень
        assertEquals(VillagerLevel.APPRENTICE, villagerData.getLevel());
    }
    
    @Test
    public void testTradeLocking() {
        // вмикаємо блокування торгівель
        tradingSystem.setEnableTradeLocking(true);
        
        // додаємо селянина
        tradingSystem.addVillager(villager, VillagerProfession.FARMER);
        
        // отримуємо дані селянина
        var villagerData = tradingSystem.getVillagerData(villager);
        
        // перевіряємо, що торгівля не заблокована
        assertFalse(villagerData.getTrades().get(0).isLocked());
        
        // блокуємо торгівлю
        tradingSystem.lockTrade(villager, 0);
        
        // перевіряємо, що торгівля заблокована
        assertTrue(villagerData.getTrades().get(0).isLocked());
    }
    
    @Test
    public void testDemandSystem() {
        // вмикаємо систему попиту
        tradingSystem.setEnableDemandSystem(true);
        
        // додаємо селянина
        tradingSystem.addVillager(villager, VillagerProfession.FARMER);
        
        // виконуємо кілька торгівель
        for (int i = 0; i < 3; i++) {
            tradingSystem.executeTrade(villager, 0);
        }
        
        // перевіряємо, що попит збільшився
        var demandItems = tradingSystem.getVillagerData(villager).getDemandItems();
        assertTrue(demandItems.containsKey("WHEAT"));
        assertEquals(3, demandItems.get("WHEAT").intValue());
    }
}