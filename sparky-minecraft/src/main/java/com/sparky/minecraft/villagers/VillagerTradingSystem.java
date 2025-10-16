package com.sparky.minecraft.villagers;

import com.sparky.minecraft.ItemStack;
import com.sparky.minecraft.NPCComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Андрій Будильников
 * 
 * система торгівлі з селянами з просунутими механіками
 */
public class VillagerTradingSystem {
    
    // професії селян
    public enum VillagerProfession {
        FARMER,           // фермер
        FISHERMAN,        // рибалка
        SHEPHERD,         // пастух
        FLETCHER,         // лучник
        LIBRARIAN,        // бібліотекар
        CARTOGRAPHER,     // картограф
        CLERIC,           // священник
        ARMORER,          // бронник
        WEAPONSMITH,      // зброяр
        TOOLSMITH,        // інструментальник
        BUTCHER,          // м'ясник
        LEATHERWORKER,    // шкіряник
        MASON,            // каменяр
        NITWIT            // нікчема
    }
    
    // рівні селян
    public enum VillagerLevel {
        NOVICE,      // новачок
        APPRENTICE,  // учень
        JOURNEYMAN,  // майстер
        EXPERT,      // експерт
        MASTER       // майстер-клас
    }
    
    // типи торгівель
    public enum TradeType {
        BUY,    // покупка
        SELL,   // продаж
        TRADE   // обмін
    }
    
    // структура торгівлі
    public static class VillagerTrade {
        private TradeType type;
        private ItemStack input1;
        private ItemStack input2; // для обміну може бути null
        private ItemStack output;
        private int maxUses;
        private int uses;
        private int xpReward;
        private float priceMultiplier;
        private boolean isLocked;
        
        public VillagerTrade(TradeType type, ItemStack input1, ItemStack input2, ItemStack output) {
            this.type = type;
            this.input1 = input1;
            this.input2 = input2;
            this.output = output;
            this.maxUses = 4;
            this.uses = 0;
            this.xpReward = 1;
            this.priceMultiplier = 0.05f;
            this.isLocked = false;
        }
        
        // геттери та сеттери
        public TradeType getType() {
            return type;
        }
        
        public ItemStack getInput1() {
            return input1;
        }
        
        public ItemStack getInput2() {
            return input2;
        }
        
        public ItemStack getOutput() {
            return output;
        }
        
        public int getMaxUses() {
            return maxUses;
        }
        
        public void setMaxUses(int maxUses) {
            this.maxUses = maxUses;
        }
        
        public int getUses() {
            return uses;
        }
        
        public void setUses(int uses) {
            this.uses = uses;
        }
        
        public int getXpReward() {
            return xpReward;
        }
        
        public void setXpReward(int xpReward) {
            this.xpReward = xpReward;
        }
        
        public float getPriceMultiplier() {
            return priceMultiplier;
        }
        
        public void setPriceMultiplier(float priceMultiplier) {
            this.priceMultiplier = priceMultiplier;
        }
        
        public boolean isLocked() {
            return isLocked;
        }
        
        public void setLocked(boolean locked) {
            isLocked = locked;
        }
        
        // перевіряє, чи торгівля доступна
        public boolean isAvailable() {
            return !isLocked && uses < maxUses;
        }
        
        // виконує торгівлю
        public boolean executeTrade() {
            if (!isAvailable()) {
                return false;
            }
            
            uses++;
            return true;
        }
        
        @Override
        public String toString() {
            return "VillagerTrade{" +
                    "type=" + type +
                    ", input1=" + input1 +
                    ", input2=" + input2 +
                    ", output=" + output +
                    ", uses=" + uses + "/" + maxUses +
                    '}';
        }
    }
    
    // дані про селянина
    public static class VillagerData {
        private VillagerProfession profession;
        private VillagerLevel level;
        private List<VillagerTrade> trades;
        private int xp;
        private boolean isWillingToTrade;
        private Map<String, Integer> demandItems;
        
        public VillagerData(VillagerProfession profession) {
            this.profession = profession;
            this.level = VillagerLevel.NOVICE;
            this.trades = new ArrayList<>();
            this.xp = 0;
            this.isWillingToTrade = true;
            this.demandItems = new HashMap<>();
            
            // ініціалізуємо початкові торгівлі
            initializeTrades();
        }
        
        // геттери та сеттери
        public VillagerProfession getProfession() {
            return profession;
        }
        
        public VillagerLevel getLevel() {
            return level;
        }
        
        public void setLevel(VillagerLevel level) {
            this.level = level;
        }
        
        public List<VillagerTrade> getTrades() {
            return trades;
        }
        
        public int getXp() {
            return xp;
        }
        
        public void addXp(int xp) {
            this.xp += xp;
            checkLevelUp();
        }
        
        public boolean isWillingToTrade() {
            return isWillingToTrade;
        }
        
        public void setWillingToTrade(boolean willingToTrade) {
            isWillingToTrade = willingToTrade;
        }
        
        public Map<String, Integer> getDemandItems() {
            return demandItems;
        }
        
        // ініціалізує початкові торгівлі
        private void initializeTrades() {
            // додаємо початкові торгівлі залежно від професії
            switch (profession) {
                case FARMER:
                    addFarmerTrades();
                    break;
                case FISHERMAN:
                    addFishermanTrades();
                    break;
                case LIBRARIAN:
                    addLibrarianTrades();
                    break;
                case WEAPONSMITH:
                    addWeaponsmithTrades();
                    break;
                default:
                    addGenericTrades();
                    break;
            }
        }
        
        // додає торгівлі фермера
        private void addFarmerTrades() {
            trades.add(new VillagerTrade(TradeType.BUY, new ItemStack("WHEAT", 20), null, new ItemStack("EMERALD", 1)));
            trades.add(new VillagerTrade(TradeType.SELL, new ItemStack("EMERALD", 1), null, new ItemStack("BREAD", 6)));
            trades.add(new VillagerTrade(TradeType.BUY, new ItemStack("POTATO", 15), null, new ItemStack("EMERALD", 1)));
        }
        
        // додає торгівлі рибалки
        private void addFishermanTrades() {
            trades.add(new VillagerTrade(TradeType.BUY, new ItemStack("COD", 6), null, new ItemStack("EMERALD", 1)));
            trades.add(new VillagerTrade(TradeType.SELL, new ItemStack("EMERALD", 1), null, new ItemStack("FISHING_ROD", 1)));
        }
        
        // додає торгівлі бібліотекаря
        private void addLibrarianTrades() {
            trades.add(new VillagerTrade(TradeType.BUY, new ItemStack("BOOK", 1), null, new ItemStack("EMERALD", 1)));
            trades.add(new VillagerTrade(TradeType.SELL, new ItemStack("EMERALD", 5), null, new ItemStack("ENCHANTED_BOOK", 1)));
        }
        
        // додає торгівлі зброяря
        private void addWeaponsmithTrades() {
            trades.add(new VillagerTrade(TradeType.BUY, new ItemStack("COAL", 15), null, new ItemStack("EMERALD", 1)));
            trades.add(new VillagerTrade(TradeType.SELL, new ItemStack("EMERALD", 4), null, new ItemStack("IRON_AXE", 1)));
        }
        
        // додає загальні торгівлі
        private void addGenericTrades() {
            trades.add(new VillagerTrade(TradeType.BUY, new ItemStack("EMERALD", 1), null, new ItemStack("WHEAT", 10)));
            trades.add(new VillagerTrade(TradeType.SELL, new ItemStack("WHEAT", 10), null, new ItemStack("EMERALD", 1)));
        }
        
        // перевіряє підвищення рівня
        private void checkLevelUp() {
            VillagerLevel newLevel = level;
            
            switch (level) {
                case NOVICE:
                    if (xp >= 10) newLevel = VillagerLevel.APPRENTICE;
                    break;
                case APPRENTICE:
                    if (xp >= 70) newLevel = VillagerLevel.JOURNEYMAN;
                    break;
                case JOURNEYMAN:
                    if (xp >= 150) newLevel = VillagerLevel.EXPERT;
                    break;
                case EXPERT:
                    if (xp >= 250) newLevel = VillagerLevel.MASTER;
                    break;
            }
            
            if (newLevel != level) {
                level = newLevel;
                unlockNewTrades();
            }
        }
        
        // розблоковує нові торгівлі при підвищенні рівня
        private void unlockNewTrades() {
            // додаємо нові торгівлі залежно від професії та рівня
            switch (profession) {
                case FARMER:
                    if (level == VillagerLevel.APPRENTICE) {
                        trades.add(new VillagerTrade(TradeType.SELL, new ItemStack("EMERALD", 1), null, new ItemStack("PUMPKIN_PIE", 4)));
                    }
                    break;
                case FISHERMAN:
                    if (level == VillagerLevel.APPRENTICE) {
                        trades.add(new VillagerTrade(TradeType.BUY, new ItemStack("SALMON", 5), null, new ItemStack("EMERALD", 1)));
                    }
                    break;
            }
        }
    }
    
    // мапа всіх селян
    private Map<NPCComponent, VillagerData> villagers;
    private Random random;
    
    // параметри системи торгівлі
    private boolean enableDynamicPricing;
    private boolean enableTradeLocking;
    private boolean enableDemandSystem;
    private float basePriceMultiplier;
    
    public VillagerTradingSystem() {
        this.villagers = new HashMap<>();
        this.random = new Random();
        
        // стандартні параметри
        this.enableDynamicPricing = true;
        this.enableTradeLocking = true;
        this.enableDemandSystem = true;
        this.basePriceMultiplier = 0.05f;
    }
    
    // геттери та сеттери для параметрів
    public boolean isEnableDynamicPricing() {
        return enableDynamicPricing;
    }
    
    public void setEnableDynamicPricing(boolean enableDynamicPricing) {
        this.enableDynamicPricing = enableDynamicPricing;
    }
    
    public boolean isEnableTradeLocking() {
        return enableTradeLocking;
    }
    
    public void setEnableTradeLocking(boolean enableTradeLocking) {
        this.enableTradeLocking = enableTradeLocking;
    }
    
    public boolean isEnableDemandSystem() {
        return enableDemandSystem;
    }
    
    public void setEnableDemandSystem(boolean enableDemandSystem) {
        this.enableDemandSystem = enableDemandSystem;
    }
    
    public float getBasePriceMultiplier() {
        return basePriceMultiplier;
    }
    
    public void setBasePriceMultiplier(float basePriceMultiplier) {
        this.basePriceMultiplier = basePriceMultiplier;
    }
    
    /**
     * додає селянина до системи
     */
    public void addVillager(NPCComponent villager, VillagerProfession profession) {
        villagers.put(villager, new VillagerData(profession));
    }
    
    /**
     * видаляє селянина з системи
     */
    public void removeVillager(NPCComponent villager) {
        villagers.remove(villager);
    }
    
    /**
     * отримує дані селянина
     */
    public VillagerData getVillagerData(NPCComponent villager) {
        return villagers.get(villager);
    }
    
    /**
     * отримує всіх селян
     */
    public Map<NPCComponent, VillagerData> getAllVillagers() {
        return new HashMap<>(villagers);
    }
    
    /**
     * виконує торгівлю з селянином
     */
    public boolean executeTrade(NPCComponent villager, int tradeIndex) {
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return false;
        }
        
        // перевіряємо, чи селянин готовий торгувати
        if (!data.isWillingToTrade()) {
            return false;
        }
        
        // перевіряємо, чи індекс торгівлі коректний
        if (tradeIndex < 0 || tradeIndex >= data.getTrades().size()) {
            return false;
        }
        
        VillagerTrade trade = data.getTrades().get(tradeIndex);
        
        // перевіряємо, чи торгівля доступна
        if (!trade.isAvailable()) {
            return false;
        }
        
        // виконуємо торгівлю
        boolean success = trade.executeTrade();
        if (success) {
            // додаємо досвід
            data.addXp(trade.getXpReward());
            
            // оновлюємо ціни при динамічному ціноутворенні
            if (enableDynamicPricing) {
                updateTradePrices(villager);
            }
            
            // оновлюємо систему попиту
            if (enableDemandSystem) {
                updateDemandSystem(villager, trade);
            }
        }
        
        return success;
    }
    
    /**
     * оновлює ціни торгівель
     */
    private void updateTradePrices(NPCComponent villager) {
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return;
        }
        
        for (VillagerTrade trade : data.getTrades()) {
            // збільшуємо множник ціни залежно від кількості використань
            float newMultiplier = basePriceMultiplier + (trade.getUses() * 0.01f);
            trade.setPriceMultiplier(newMultiplier);
        }
    }
    
    /**
     * оновлює систему попиту
     */
    private void updateDemandSystem(NPCComponent villager, VillagerTrade trade) {
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return;
        }
        
        // збільшуємо попит на предмети, які селянин купує
        if (trade.getType() == TradeType.BUY) {
            String itemType = trade.getInput1().getItemType();
            int currentDemand = data.getDemandItems().getOrDefault(itemType, 0);
            data.getDemandItems().put(itemType, currentDemand + 1);
        }
    }
    
    /**
     * отримує знижку на торгівлю залежно від попиту
     */
    public float getDiscountForTrade(NPCComponent villager, VillagerTrade trade) {
        if (!enableDemandSystem) {
            return 0.0f;
        }
        
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return 0.0f;
        }
        
        // якщо селянин купує цей предмет, даємо знижку
        if (trade.getType() == TradeType.BUY) {
            String itemType = trade.getInput1().getItemType();
            int demand = data.getDemandItems().getOrDefault(itemType, 0);
            return Math.min(0.5f, demand * 0.05f); // максимум 50% знижки
        }
        
        return 0.0f;
    }
    
    /**
     * блокує торгівлю
     */
    public void lockTrade(NPCComponent villager, int tradeIndex) {
        if (!enableTradeLocking) {
            return;
        }
        
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return;
        }
        
        if (tradeIndex >= 0 && tradeIndex < data.getTrades().size()) {
            data.getTrades().get(tradeIndex).setLocked(true);
        }
    }
    
    /**
     * розблоковує всі торгівлі селянина
     */
    public void unlockAllTrades(NPCComponent villager) {
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return;
        }
        
        for (VillagerTrade trade : data.getTrades()) {
            trade.setLocked(false);
        }
    }
    
    /**
     * генерує спеціальну торгівлю на основі подій
     */
    public void generateEventTrade(NPCComponent villager, String eventType) {
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return;
        }
        
        VillagerTrade specialTrade = null;
        
        switch (eventType) {
            case "HALLOWEEN":
                specialTrade = new VillagerTrade(TradeType.SELL, new ItemStack("EMERALD", 1), null, new ItemStack("PUMPKIN", 1));
                specialTrade.setXpReward(3);
                break;
            case "CHRISTMAS":
                specialTrade = new VillagerTrade(TradeType.SELL, new ItemStack("EMERALD", 2), null, new ItemStack("COOKIE", 3));
                specialTrade.setXpReward(2);
                break;
            case "EASTER":
                specialTrade = new VillagerTrade(TradeType.BUY, new ItemStack("EGG", 5), null, new ItemStack("EMERALD", 1));
                specialTrade.setXpReward(2);
                break;
        }
        
        if (specialTrade != null) {
            data.getTrades().add(specialTrade);
        }
    }
    
    /**
     * симулює торгівлю з випадковим селянином
     */
    public void simulateRandomTrade() {
        if (villagers.isEmpty()) {
            return;
        }
        
        // вибираємо випадкового селянина
        List<NPCComponent> villagerList = new ArrayList<>(villagers.keySet());
        NPCComponent randomVillager = villagerList.get(random.nextInt(villagerList.size()));
        
        // отримуємо його дані
        VillagerData data = villagers.get(randomVillager);
        if (data == null || data.getTrades().isEmpty()) {
            return;
        }
        
        // вибираємо випадкову доступну торгівлю
        List<VillagerTrade> availableTrades = new ArrayList<>();
        for (int i = 0; i < data.getTrades().size(); i++) {
            if (data.getTrades().get(i).isAvailable()) {
                availableTrades.add(data.getTrades().get(i));
            }
        }
        
        if (!availableTrades.isEmpty()) {
            VillagerTrade randomTrade = availableTrades.get(random.nextInt(availableTrades.size()));
            System.out.println("Simulated trade: " + randomTrade);
        }
    }
    
    /**
     * отримує найпопулярніші предмети для покупки
     */
    public List<String> getMostDemandedItems(NPCComponent villager) {
        VillagerData data = villagers.get(villager);
        if (data == null) {
            return new ArrayList<>();
        }
        
        List<String> result = new ArrayList<>();
        Map<String, Integer> demandItems = data.getDemandItems();
        
        // сортуємо предмети за попитом
        demandItems.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> result.add(entry.getKey()));
        
        return result;
    }
}