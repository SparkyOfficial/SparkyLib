package com.sparky.minecraft.villagers;

import com.sparky.minecraft.NPCComponent;
import com.sparky.minecraft.ItemStack;
import com.sparky.minecraft.villagers.VillagerTradingSystem.VillagerData;
import com.sparky.minecraft.villagers.VillagerTradingSystem.VillagerProfession;
import com.sparky.minecraft.villagers.VillagerTradingSystem.VillagerTrade;

/**
 * @author Андрій Будильников
 * 
 * демонстрація системи торгівлі з селянами
 */
public class VillagerTradingDemo {
    
    private VillagerTradingSystem tradingSystem;
    
    public VillagerTradingDemo() {
        this.tradingSystem = new VillagerTradingSystem();
    }
    
    /**
     * демонструє базову торгівлю з селянином-фермером
     */
    public void demonstrateFarmerTrading() {
        System.out.println("=== Демонстрація торгівлі з фермером ===");
        
        // створюємо селянина-фермера
        NPCComponent farmer = new NPCComponent();
        farmer.setEntityType("VILLAGER");
        farmer.setNpcName("Фермер Джон");
        
        // додаємо його до системи торгівлі
        tradingSystem.addVillager(farmer, VillagerProfession.FARMER);
        
        // отримуємо дані селянина
        VillagerData farmerData = tradingSystem.getVillagerData(farmer);
        
        System.out.println("Селянин: " + farmer.getNpcName());
        System.out.println("Професія: " + farmerData.getProfession());
        System.out.println("Рівень: " + farmerData.getLevel());
        System.out.println("Торгівлі:");
        
        // показуємо всі доступні торгівлі
        for (int i = 0; i < farmerData.getTrades().size(); i++) {
            VillagerTrade trade = farmerData.getTrades().get(i);
            System.out.println("  " + (i + 1) + ". " + trade);
        }
        
        // виконуємо кілька торгівель
        System.out.println("\nВиконання торгівель:");
        tradingSystem.executeTrade(farmer, 0); // купівля пшениці
        tradingSystem.executeTrade(farmer, 1); // продаж хліба
        
        System.out.println("Після торгівлі:");
        System.out.println("Рівень: " + farmerData.getLevel());
        System.out.println("Досвід: " + farmerData.getXp());
    }
    
    /**
     * демонструє просунуту торгівлю з селянином-бібліотекарем
     */
    public void demonstrateLibrarianTrading() {
        System.out.println("\n=== Демонстрація торгівлі з бібліотекарем ===");
        
        // створюємо селянина-бібліотекаря
        NPCComponent librarian = new NPCComponent();
        librarian.setEntityType("VILLAGER");
        librarian.setNpcName("Бібліотекар Емма");
        
        // додаємо його до системи торгівлі
        tradingSystem.addVillager(librarian, VillagerProfession.LIBRARIAN);
        
        // отримуємо дані селянина
        VillagerData librarianData = tradingSystem.getVillagerData(librarian);
        
        System.out.println("Селянин: " + librarian.getNpcName());
        System.out.println("Професія: " + librarianData.getProfession());
        System.out.println("Рівень: " + librarianData.getLevel());
        System.out.println("Торгівлі:");
        
        // показуємо всі доступні торгівлі
        for (int i = 0; i < librarianData.getTrades().size(); i++) {
            VillagerTrade trade = librarianData.getTrades().get(i);
            System.out.println("  " + (i + 1) + ". " + trade);
        }
        
        // виконуємо торгівлю заклятої книги
        System.out.println("\nВиконання торгівлі заклятої книги:");
        boolean success = tradingSystem.executeTrade(librarian, 1);
        System.out.println("Торгівля успішна: " + success);
        
        // показуємо новий рівень та досвід
        System.out.println("Рівень: " + librarianData.getLevel());
        System.out.println("Досвід: " + librarianData.getXp());
    }
    
    /**
     * демонструє систему попиту
     */
    public void demonstrateDemandSystem() {
        System.out.println("\n=== Демонстрація системи попиту ===");
        
        // створюємо селянина-фермера
        NPCComponent farmer = new NPCComponent();
        farmer.setEntityType("VILLAGER");
        farmer.setNpcName("Фермер Джон");
        
        // додаємо його до системи торгівлі
        tradingSystem.addVillager(farmer, VillagerProfession.FARMER);
        
        // виконуємо кілька торгівель пшениці, щоб збільшити попит
        System.out.println("Виконання кількох торгівель пшениці:");
        for (int i = 0; i < 3; i++) {
            tradingSystem.executeTrade(farmer, 0);
        }
        
        // отримуємо найпопулярніші предмети
        System.out.println("Найпопулярніші предмети для покупки:");
        for (String item : tradingSystem.getMostDemandedItems(farmer)) {
            System.out.println("  " + item);
        }
    }
    
    /**
     * демонструє спеціальні події
     */
    public void demonstrateEventTrades() {
        System.out.println("\n=== Демонстрація спеціальних подій ===");
        
        // створюємо селянина
        NPCComponent villager = new NPCComponent();
        villager.setEntityType("VILLAGER");
        villager.setNpcName("Селянин");
        
        // додаємо його до системи торгівлі
        tradingSystem.addVillager(villager, VillagerProfession.FARMER);
        
        // отримуємо початкові торгівлі
        VillagerData data = tradingSystem.getVillagerData(villager);
        System.out.println("Початкові торгівлі: " + data.getTrades().size());
        
        // генеруємо спеціальну торгівлю на Хелловін
        tradingSystem.generateEventTrade(villager, "HALLOWEEN");
        
        // показуємо нові торгівлі
        System.out.println("Торгівлі після Хелловіну: " + data.getTrades().size());
        System.out.println("Остання торгівля: " + data.getTrades().get(data.getTrades().size() - 1));
    }
    
    /**
     * демонструє всі можливості системи торгівлі
     */
    public void demonstrateAllFeatures() {
        System.out.println("=== Демонстрація системи торгівлі з селянами ===\n");
        
        demonstrateFarmerTrading();
        demonstrateLibrarianTrading();
        demonstrateDemandSystem();
        demonstrateEventTrades();
        
        System.out.println("\n=== Симуляція випадкової торгівлі ===");
        tradingSystem.simulateRandomTrade();
    }
    
    public static void main(String[] args) {
        // Запускаємо швидкий тест
        quickTest();
        
        // Запускаємо повну демонстрацію
        VillagerTradingDemo demo = new VillagerTradingDemo();
        demo.demonstrateAllFeatures();
    }
    
    /**
     * швидкий тест системи торгівлі
     */
    public static void quickTest() {
        System.out.println("=== Швидкий тест системи торгівлі ===");
        
        VillagerTradingSystem system = new VillagerTradingSystem();
        NPCComponent villager = new NPCComponent();
        villager.setEntityType("VILLAGER");
        villager.setNpcName("Тестовий селянин");
        
        system.addVillager(villager, VillagerProfession.FARMER);
        
        var data = system.getVillagerData(villager);
        System.out.println("Селянин створений: " + data.getProfession());
        System.out.println("Кількість торгівель: " + data.getTrades().size());
        
        if (!data.getTrades().isEmpty()) {
            boolean success = system.executeTrade(villager, 0);
            System.out.println("Торгівля успішна: " + success);
        }
        
        System.out.println("=== Тест завершено ===");
    }
}