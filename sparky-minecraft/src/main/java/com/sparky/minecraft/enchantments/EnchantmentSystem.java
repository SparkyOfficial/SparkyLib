package com.sparky.minecraft.enchantments;

import com.sparky.minecraft.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Андрій Будильников
 * 
 * Система зачарування предметів з просунутими механіками
 */
public class EnchantmentSystem {
    
    // Типи зачарувань
    public enum EnchantmentType {
        // Захисні зачарування
        PROTECTION,           // Захист
        FIRE_PROTECTION,      // Захист від вогню
        FEATHER_FALLING,      // М'яке падіння
        BLAST_PROTECTION,     // Захист від вибухів
        PROJECTILE_PROTECTION, // Захист від снарядів
        RESPIRATION,          // Дихання
        AQUA_AFFINITY,        // Рідне зілля
        THORNS,               // Шипи
        DEPTH_STRIDER,        // Ходіння по воді
        FROST_WALKER,         // Льодохід
        BINDING_CURSE,        // Прокляття невід'ємності
        VANISHING_CURSE,      // Прокляття зникнення
        
        // Бойові зачарування
        SHARPNESS,            // Гострота
        SMITE,                // Карта
        BANE_OF_ARTHROPODS,   // Загибель членистоногих
        KNOCKBACK,            // Відкидання
        FIRE_ASPECT,          // Запалення
        LOOTING,              // Здобич
        SWEEPING,             // Розмашиста атака
        
        // Зачарування інструментів
        EFFICIENCY,           // Ефективність
        SILK_TOUCH,           // Шовковий дотик
        UNBREAKING,           // Незламність
        FORTUNE,              // Удача
        
        // Зачарування луків
        POWER,                // Сила
        PUNCH,                // Відкидання стріл
        FLAME,                // Полум'я
        INFINITY,             // Нескінченність
        
        // Зачарування риболовлі
        LUCK_OF_THE_SEA,      // Риболовна удача
        LURE,                 // Приманка
        
        // Зачарування для книжок
        MENDING,              // Полагодження
        FROG_LIGHT,           // Жаб'яче світло
        SOUL_SPEED,           // Швидкість душ
        
        // Зачарування для броні
        SWIFT_SNEAK,          // Швидке підкрадання
        
        // Зачарування для незеритових предметів
        SMITHING_TEMPLATE     // Шаблон коваля
    }
    
    // Рідкість зачарувань
    public enum Rarity {
        COMMON(10),      // Звичайна
        UNCOMMON(5),     // Незвичайна
        RARE(2),         // Рідкісна
        VERY_RARE(1);    // Дуже рідкісна
        
        private final int weight;
        
        Rarity(int weight) {
            this.weight = weight;
        }
        
        public int getWeight() {
            return weight;
        }
    }
    
    // Структура зачарування
    public static class Enchantment {
        private EnchantmentType type;
        private int level;
        private Rarity rarity;
        private List<EnchantmentType> incompatibleEnchantments;
        private String displayName;
        private String description;
        
        public Enchantment(EnchantmentType type, int level, Rarity rarity, 
                          String displayName, String description) {
            this.type = type;
            this.level = level;
            this.rarity = rarity;
            this.displayName = displayName;
            this.description = description;
            this.incompatibleEnchantments = new ArrayList<>();
        }
        
        // Геттери
        public EnchantmentType getType() {
            return type;
        }
        
        public int getLevel() {
            return level;
        }
        
        public void setLevel(int level) {
            this.level = level;
        }
        
        public Rarity getRarity() {
            return rarity;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getDescription() {
            return description;
        }
        
        public List<EnchantmentType> getIncompatibleEnchantments() {
            return incompatibleEnchantments;
        }
        
        public void addIncompatibleEnchantment(EnchantmentType enchantment) {
            incompatibleEnchantments.add(enchantment);
        }
        
        @Override
        public String toString() {
            return displayName + " " + level;
        }
    }
    
    // Мапа всіх зачарувань
    private Map<EnchantmentType, Enchantment> enchantments;
    private Random random;
    
    // Параметри системи зачарування
    private boolean enableTreasureEnchantments;
    private boolean enableCursedEnchantments;
    private int maxEnchantmentLevel;
    private int enchantmentPowerMultiplier;
    
    public EnchantmentSystem() {
        this.enchantments = new HashMap<>();
        this.random = new Random();
        
        // Стандартні параметри
        this.enableTreasureEnchantments = true;
        this.enableCursedEnchantments = true;
        this.maxEnchantmentLevel = 5;
        this.enchantmentPowerMultiplier = 1;
        
        // Ініціалізуємо зачарування
        initializeEnchantments();
    }
    
    // Геттери та сеттери для параметрів
    public boolean isEnableTreasureEnchantments() {
        return enableTreasureEnchantments;
    }
    
    public void setEnableTreasureEnchantments(boolean enableTreasureEnchantments) {
        this.enableTreasureEnchantments = enableTreasureEnchantments;
    }
    
    public boolean isEnableCursedEnchantments() {
        return enableCursedEnchantments;
    }
    
    public void setEnableCursedEnchantments(boolean enableCursedEnchantments) {
        this.enableCursedEnchantments = enableCursedEnchantments;
    }
    
    public int getMaxEnchantmentLevel() {
        return maxEnchantmentLevel;
    }
    
    public void setMaxEnchantmentLevel(int maxEnchantmentLevel) {
        this.maxEnchantmentLevel = maxEnchantmentLevel;
    }
    
    public int getEnchantmentPowerMultiplier() {
        return enchantmentPowerMultiplier;
    }
    
    public void setEnchantmentPowerMultiplier(int enchantmentPowerMultiplier) {
        this.enchantmentPowerMultiplier = enchantmentPowerMultiplier;
    }
    
    /**
     * Ініціалізує всі зачарування
     */
    private void initializeEnchantments() {
        // Захисні зачарування
        addEnchantment(new Enchantment(EnchantmentType.PROTECTION, 1, Rarity.COMMON, 
            "Захист", "Зменшує загальну кількість отриманої шкоди"));
        
        addEnchantment(new Enchantment(EnchantmentType.FIRE_PROTECTION, 1, Rarity.UNCOMMON, 
            "Захист від вогню", "Зменшує шкоду від вогню та лави"));
        
        addEnchantment(new Enchantment(EnchantmentType.FEATHER_FALLING, 1, Rarity.UNCOMMON, 
            "М'яке падіння", "Зменшує шкоду від падіння"));
        
        addEnchantment(new Enchantment(EnchantmentType.BLAST_PROTECTION, 1, Rarity.RARE, 
            "Захист від вибухів", "Зменшує шкоду від вибухів"));
        
        addEnchantment(new Enchantment(EnchantmentType.PROJECTILE_PROTECTION, 1, Rarity.UNCOMMON, 
            "Захист від снарядів", "Зменшує шкоду від снарядів"));
        
        // Бойові зачарування
        addEnchantment(new Enchantment(EnchantmentType.SHARPNESS, 1, Rarity.COMMON, 
            "Гострота", "Збільшує шкоду від атак"));
        
        addEnchantment(new Enchantment(EnchantmentType.SMITE, 1, Rarity.UNCOMMON, 
            "Карта", "Збільшує шкоду по нежиті"));
        
        addEnchantment(new Enchantment(EnchantmentType.BANE_OF_ARTHROPODS, 1, Rarity.UNCOMMON, 
            "Загибель членистоногих", "Збільшує шкоду по членистоногим"));
        
        addEnchantment(new Enchantment(EnchantmentType.KNOCKBACK, 1, Rarity.UNCOMMON, 
            "Відкидання", "Відкидає ворогів під час атаки"));
        
        addEnchantment(new Enchantment(EnchantmentType.FIRE_ASPECT, 1, Rarity.RARE, 
            "Запалення", "Підпалює ворогів під час атаки"));
        
        // Зачарування інструментів
        addEnchantment(new Enchantment(EnchantmentType.EFFICIENCY, 1, Rarity.COMMON, 
            "Ефективність", "Збільшує швидкість видобутку"));
        
        addEnchantment(new Enchantment(EnchantmentType.SILK_TOUCH, 1, Rarity.VERY_RARE, 
            "Шовковий дотик", "Дозволяє збирати блоки в їхньому оригінальному вигляді"));
        
        addEnchantment(new Enchantment(EnchantmentType.UNBREAKING, 1, Rarity.UNCOMMON, 
            "Незламність", "Збільшує міцність предмета"));
        
        addEnchantment(new Enchantment(EnchantmentType.FORTUNE, 1, Rarity.RARE, 
            "Удача", "Збільшує кількість отриманих предметів під час видобутку"));
        
        // Прокляття
        if (enableCursedEnchantments) {
            addEnchantment(new Enchantment(EnchantmentType.BINDING_CURSE, 1, Rarity.VERY_RARE, 
                "Прокляття невід'ємності", "Забороняє зняти предмет з броні"));
            
            addEnchantment(new Enchantment(EnchantmentType.VANISHING_CURSE, 1, Rarity.VERY_RARE, 
                "Прокляття зникнення", "Предмет зникає після смерті"));
        }
        
        // Встановлюємо несумісності
        setEnchantmentIncompatibilities();
    }
    
    /**
     * Додає зачарування до мапи
     */
    private void addEnchantment(Enchantment enchantment) {
        enchantments.put(enchantment.getType(), enchantment);
    }
    
    /**
     * Встановлює несумісності між зачаруваннями
     */
    private void setEnchantmentIncompatibilities() {
        // Захист
        Enchantment protection = enchantments.get(EnchantmentType.PROTECTION);
        if (protection != null) {
            protection.addIncompatibleEnchantment(EnchantmentType.FIRE_PROTECTION);
            protection.addIncompatibleEnchantment(EnchantmentType.BLAST_PROTECTION);
            protection.addIncompatibleEnchantment(EnchantmentType.PROJECTILE_PROTECTION);
        }
        
        // Захист від вогню
        Enchantment fireProtection = enchantments.get(EnchantmentType.FIRE_PROTECTION);
        if (fireProtection != null) {
            fireProtection.addIncompatibleEnchantment(EnchantmentType.PROTECTION);
            fireProtection.addIncompatibleEnchantment(EnchantmentType.BLAST_PROTECTION);
            fireProtection.addIncompatibleEnchantment(EnchantmentType.PROJECTILE_PROTECTION);
        }
        
        // Захист від вибухів
        Enchantment blastProtection = enchantments.get(EnchantmentType.BLAST_PROTECTION);
        if (blastProtection != null) {
            blastProtection.addIncompatibleEnchantment(EnchantmentType.PROTECTION);
            blastProtection.addIncompatibleEnchantment(EnchantmentType.FIRE_PROTECTION);
            blastProtection.addIncompatibleEnchantment(EnchantmentType.PROJECTILE_PROTECTION);
        }
        
        // Захист від снарядів
        Enchantment projectileProtection = enchantments.get(EnchantmentType.PROJECTILE_PROTECTION);
        if (projectileProtection != null) {
            projectileProtection.addIncompatibleEnchantment(EnchantmentType.PROTECTION);
            projectileProtection.addIncompatibleEnchantment(EnchantmentType.FIRE_PROTECTION);
            projectileProtection.addIncompatibleEnchantment(EnchantmentType.BLAST_PROTECTION);
        }
        
        // Гострота
        Enchantment sharpness = enchantments.get(EnchantmentType.SHARPNESS);
        if (sharpness != null) {
            sharpness.addIncompatibleEnchantment(EnchantmentType.SMITE);
            sharpness.addIncompatibleEnchantment(EnchantmentType.BANE_OF_ARTHROPODS);
        }
        
        // Карта
        Enchantment smite = enchantments.get(EnchantmentType.SMITE);
        if (smite != null) {
            smite.addIncompatibleEnchantment(EnchantmentType.SHARPNESS);
            smite.addIncompatibleEnchantment(EnchantmentType.BANE_OF_ARTHROPODS);
        }
        
        // Загибель членистоногих
        Enchantment baneOfArthropods = enchantments.get(EnchantmentType.BANE_OF_ARTHROPODS);
        if (baneOfArthropods != null) {
            baneOfArthropods.addIncompatibleEnchantment(EnchantmentType.SHARPNESS);
            baneOfArthropods.addIncompatibleEnchantment(EnchantmentType.SMITE);
        }
        
        // Шовковий дотик
        Enchantment silkTouch = enchantments.get(EnchantmentType.SILK_TOUCH);
        if (silkTouch != null) {
            silkTouch.addIncompatibleEnchantment(EnchantmentType.FORTUNE);
        }
        
        // Удача
        Enchantment fortune = enchantments.get(EnchantmentType.FORTUNE);
        if (fortune != null) {
            fortune.addIncompatibleEnchantment(EnchantmentType.SILK_TOUCH);
        }
    }
    
    /**
     * Отримує зачарування за типом
     */
    public Enchantment getEnchantment(EnchantmentType type) {
        return enchantments.get(type);
    }
    
    /**
     * Отримує всі зачарування
     */
    public Map<EnchantmentType, Enchantment> getAllEnchantments() {
        return new HashMap<>(enchantments);
    }
    
    /**
     * Зачаровує предмет
     */
    public boolean enchantItem(ItemStack item, EnchantmentType enchantmentType, int level) {
        // Перевіряємо, чи предмет може мати це зачарування
        if (!isEnchantable(item, enchantmentType)) {
            return false;
        }
        
        // Перевіряємо, чи рівень не перевищує максимальний
        if (level > maxEnchantmentLevel) {
            return false;
        }
        
        // Створюємо нове зачарування
        Enchantment baseEnchantment = enchantments.get(enchantmentType);
        if (baseEnchantment == null) {
            return false;
        }
        
        Enchantment enchantment = new Enchantment(
            baseEnchantment.getType(),
            level,
            baseEnchantment.getRarity(),
            baseEnchantment.getDisplayName(),
            baseEnchantment.getDescription()
        );
        
        // Додаємо зачарування до предмета
        item.addEnchantment(enchantment);
        return true;
    }
    
    /**
     * Перевіряє, чи предмет може бути зачарований певним зачаруванням
     */
    private boolean isEnchantable(ItemStack item, EnchantmentType enchantmentType) {
        // Тут була б реалізація перевірки сумісності предмета та зачарування
        // Для спрощення припускаємо, що всі предмети можуть бути зачаровані
        return true;
    }
    
    /**
     * Генерує випадкові зачарування для предмета
     */
    public List<Enchantment> generateRandomEnchantments(ItemStack item, int enchantmentPower) {
        List<Enchantment> result = new ArrayList<>();
        
        // Модифікуємо силу зачарування
        enchantmentPower *= enchantmentPowerMultiplier;
        
        // Визначаємо кількість зачарувань
        int enchantmentCount = 1;
        if (enchantmentPower > 15) {
            enchantmentCount = 2;
        }
        if (enchantmentPower > 30) {
            enchantmentCount = 3;
        }
        
        // Вибираємо випадкові зачарування
        for (int i = 0; i < enchantmentCount; i++) {
            Enchantment enchantment = selectRandomEnchantment(enchantmentPower);
            if (enchantment != null && !isConflicting(result, enchantment)) {
                result.add(enchantment);
            }
        }
        
        return result;
    }
    
    /**
     * Вибирає випадкове зачарування на основі сили
     */
    private Enchantment selectRandomEnchantment(int enchantmentPower) {
        // Створюємо список доступних зачарувань
        List<Enchantment> availableEnchantments = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        int totalWeight = 0;
        
        for (Enchantment enchantment : enchantments.values()) {
            // Перевіряємо, чи зачарування доступне
            if (!isEnchantmentAvailable(enchantment)) {
                continue;
            }
            
            // Перевіряємо, чи сила достатня для цього зачарування
            if (enchantmentPower >= getMinPowerForEnchantment(enchantment)) {
                availableEnchantments.add(enchantment);
                int weight = enchantment.getRarity().getWeight();
                weights.add(weight);
                totalWeight += weight;
            }
        }
        
        // Якщо немає доступних зачарувань, повертаємо null
        if (availableEnchantments.isEmpty()) {
            return null;
        }
        
        // Вибираємо випадкове зачарування на основі ваги
        int randomValue = random.nextInt(totalWeight);
        int currentWeight = 0;
        
        for (int i = 0; i < availableEnchantments.size(); i++) {
            currentWeight += weights.get(i);
            if (randomValue < currentWeight) {
                Enchantment selected = availableEnchantments.get(i);
                // Визначаємо рівень зачарування
                int level = determineEnchantmentLevel(selected, enchantmentPower);
                return new Enchantment(
                    selected.getType(),
                    level,
                    selected.getRarity(),
                    selected.getDisplayName(),
                    selected.getDescription()
                );
            }
        }
        
        return null;
    }
    
    /**
     * Перевіряє, чи зачарування доступне
     */
    private boolean isEnchantmentAvailable(Enchantment enchantment) {
        // Перевіряємо, чи ввімкнені скарбові зачарування
        if (!enableTreasureEnchantments && 
            (enchantment.getType() == EnchantmentType.FROG_LIGHT || 
             enchantment.getType() == EnchantmentType.SOUL_SPEED)) {
            return false;
        }
        
        // Перевіряємо, чи ввімкнені прокляття
        if (!enableCursedEnchantments && 
            (enchantment.getType() == EnchantmentType.BINDING_CURSE || 
             enchantment.getType() == EnchantmentType.VANISHING_CURSE)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Отримує мінімальну силу для зачарування
     */
    private int getMinPowerForEnchantment(Enchantment enchantment) {
        switch (enchantment.getRarity()) {
            case COMMON:
                return 1;
            case UNCOMMON:
                return 5;
            case RARE:
                return 15;
            case VERY_RARE:
                return 25;
            default:
                return 1;
        }
    }
    
    /**
     * Визначає рівень зачарування
     */
    private int determineEnchantmentLevel(Enchantment enchantment, int enchantmentPower) {
        // Базовий рівень на основі сили
        int baseLevel = Math.max(1, enchantmentPower / 10);
        
        // Додаємо випадковість
        int randomBonus = random.nextInt(3);
        
        // Обмежуємо максимальним рівнем
        return Math.min(maxEnchantmentLevel, baseLevel + randomBonus);
    }
    
    /**
     * Перевіряє, чи зачарування конфліктує з вже наявними
     */
    private boolean isConflicting(List<Enchantment> existingEnchantments, Enchantment newEnchantment) {
        for (Enchantment existing : existingEnchantments) {
            // Перевіряємо прямі несумісності
            if (existing.getIncompatibleEnchantments().contains(newEnchantment.getType()) ||
                newEnchantment.getIncompatibleEnchantments().contains(existing.getType())) {
                return true;
            }
            
            // Перевіряємо, чи це те саме зачарування (але з іншим рівнем)
            if (existing.getType() == newEnchantment.getType()) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Розраховує вартість зачарування
     */
    public int calculateEnchantmentCost(Enchantment enchantment) {
        int baseCost = 1;
        
        switch (enchantment.getRarity()) {
            case COMMON:
                baseCost = 1;
                break;
            case UNCOMMON:
                baseCost = 2;
                break;
            case RARE:
                baseCost = 4;
                break;
            case VERY_RARE:
                baseCost = 8;
                break;
        }
        
        return baseCost * enchantment.getLevel();
    }
    
    /**
     * Отримує опис зачарування
     */
    public String getEnchantmentDescription(EnchantmentType type) {
        Enchantment enchantment = enchantments.get(type);
        return enchantment != null ? enchantment.getDescription() : "Невідоме зачарування";
    }
    
    /**
     * Отримує список зачарувань для предмета
     */
    public List<Enchantment> getItemEnchantments(ItemStack item) {
        // У реальній реалізації тут була б логіка отримання зачарувань з предмета
        return new ArrayList<>();
    }
}