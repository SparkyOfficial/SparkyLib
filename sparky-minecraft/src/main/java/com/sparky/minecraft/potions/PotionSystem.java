package com.sparky.minecraft.potions;

import com.sparky.minecraft.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Андрій Будильников
 * 
 * Система зілля з просунутими ефектами та механіками
 */
public class PotionSystem {
    
    // Типи зілля
    public enum PotionType {
        // Базові зілля
        AWKWARD,              // Незручне зілля
        THICK,                // Густе зілля
        MUNDANE,              // Звичайне зілля
        WATER,                // Вода
        WATER_BREATHING,      // Дихання під водою
        HEALING,              // Лікування
        HARMING,              // Завдання шкоди
        POISON,               // Отруєння
        REGENERATION,         // Регенерація
        STRENGTH,             // Сила
        WEAKNESS,             // Слабкість
        SLOWNESS,             // Сповільнення
        SWIFTNESS,            // Швидкість
        FIRE_RESISTANCE,      // Вогнестійкість
        NIGHT_VISION,         // Нічне бачення
        INVISIBILITY,         // Невидимість
        LEAPING,              // Стрибучість
        LUCK,                 // Удача
        TURTLE_MASTER,        // Майстер черепахи
        SLOW_FALLING,         // Повільне падіння
        
        // Просунуті зілля
        LONG_WATER_BREATHING, // Довге дихання під водою
        STRONG_HEALING,       // Потужне лікування
        STRONG_HARMING,       // Потужна шкода
        LONG_POISON,          // Довге отруєння
        STRONG_POISON,        // Потужне отруєння
        LONG_REGENERATION,    // Довга регенерація
        STRONG_REGENERATION,  // Потужна регенерація
        LONG_STRENGTH,        // Довга сила
        STRONG_STRENGTH,      // Потужна сила
        LONG_WEAKNESS,        // Довга слабкість
        LONG_SLOWNESS,        // Довге сповільнення
        LONG_SWIFTNESS,       // Довга швидкість
        STRONG_SWIFTNESS,     // Потужна швидкість
        LONG_FIRE_RESISTANCE, // Довга вогнестійкість
        LONG_NIGHT_VISION,    // Довге нічне бачення
        LONG_INVISIBILITY,    // Довга невидимість
        LONG_LEAPING,         // Довга стрибучість
        STRONG_LEAPING,       // Потужна стрибучість
        LONG_SLOW_FALLING,    // Довге повільне падіння
        
        // Спеціальні зілля
        LINGERING,            // Осадні зілля
        SPLASH,               // Вибухові зілля
        UNCRAFTABLE           // Невиготовлювані зілля
    }
    
    // Ефекти зілля
    public enum PotionEffect {
        // Позитивні ефекти
        SPEED,                // Швидкість
        SLOWNESS,             // Сповільнення
        HASTE,                // Поспіх
        MINING_FATIGUE,       // Втома від видобутку
        STRENGTH,             // Сила
        INSTANT_HEALTH,       // Миттєве здоров'я
        INSTANT_DAMAGE,       // Миттєва шкода
        JUMP_BOOST,           // Підсилення стрибка
        NAUSEA,               // Нудота
        REGENERATION,         // Регенерація
        RESISTANCE,           // Опір
        FIRE_RESISTANCE,      // Вогнестійкість
        WATER_BREATHING,      // Дихання під водою
        INVISIBILITY,         // Невидимість
        BLINDNESS,            // Сліпота
        NIGHT_VISION,         // Нічне бачення
        HUNGER,               // Голод
        WEAKNESS,             // Слабкість
        POISON,               // Отруєння
        WITHER,               // Висушування
        HEALTH_BOOST,         // Підсилення здоров'я
        ABSORPTION,           // Поглинання
        SATURATION,           // Насичення
        GLOWING,              // Світіння
        LEVITATION,           // Левітація
        LUCK,                 // Удача
        UNLUCK,               // Невдача
        SLOW_FALLING,         // Повільне падіння
        CONDUIT_POWER,        // Енергія провідника
        DOLPHINS_GRACE,       // Грація дельфіна
        BAD_OMEN,             // Погане передчуття
        HERO_OF_THE_VILLAGE   // Герой села
    }
    
    // Рідкість зілля
    public enum PotionRarity {
        COMMON,      // Звичайна
        UNCOMMON,    // Незвичайна
        RARE,        // Рідкісна
        EPIC,        // Епічна
        LEGENDARY    // Легендарна
    }
    
    // Структура зілля
    public static class Potion {
        private PotionType type;
        private String name;
        private String displayName;
        private PotionRarity rarity;
        private List<PotionEffectData> effects;
        private int duration;
        private int amplifier;
        private boolean isSplash;
        private boolean isLingering;
        
        public Potion(PotionType type, String name, String displayName, PotionRarity rarity) {
            this.type = type;
            this.name = name;
            this.displayName = displayName;
            this.rarity = rarity;
            this.effects = new ArrayList<>();
            this.duration = 0;
            this.amplifier = 0;
            this.isSplash = false;
            this.isLingering = false;
        }
        
        // Геттери та сеттери
        public PotionType getType() {
            return type;
        }
        
        public String getName() {
            return name;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public PotionRarity getRarity() {
            return rarity;
        }
        
        public List<PotionEffectData> getEffects() {
            return effects;
        }
        
        public int getDuration() {
            return duration;
        }
        
        public void setDuration(int duration) {
            this.duration = duration;
        }
        
        public int getAmplifier() {
            return amplifier;
        }
        
        public void setAmplifier(int amplifier) {
            this.amplifier = amplifier;
        }
        
        public boolean isSplash() {
            return isSplash;
        }
        
        public void setSplash(boolean splash) {
            isSplash = splash;
        }
        
        public boolean isLingering() {
            return isLingering;
        }
        
        public void setLingering(boolean lingering) {
            isLingering = lingering;
        }
        
        public void addEffect(PotionEffect effect, int duration, int amplifier) {
            effects.add(new PotionEffectData(effect, duration, amplifier));
        }
        
        @Override
        public String toString() {
            return displayName + (amplifier > 0 ? " " + toRoman(amplifier + 1) : "");
        }
        
        // Конвертує число в римські цифри
        private String toRoman(int number) {
            if (number <= 0) return "";
            
            String[] romanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
            return number <= 10 ? romanNumerals[number - 1] : String.valueOf(number);
        }
    }
    
    // Дані про ефект зілля
    public static class PotionEffectData {
        private PotionEffect effect;
        private int duration;
        private int amplifier;
        
        public PotionEffectData(PotionEffect effect, int duration, int amplifier) {
            this.effect = effect;
            this.duration = duration;
            this.amplifier = amplifier;
        }
        
        // Геттери
        public PotionEffect getEffect() {
            return effect;
        }
        
        public int getDuration() {
            return duration;
        }
        
        public int getAmplifier() {
            return amplifier;
        }
    }
    
    // Мапа всіх зілля
    private Map<PotionType, Potion> potions;
    private Random random;
    
    // Параметри системи зілля
    private boolean enableSplashPotions;
    private boolean enableLingeringPotions;
    private boolean enablePotionEffects;
    private int maxPotionAmplifier;
    private int maxPotionDuration;
    
    public PotionSystem() {
        this.potions = new HashMap<>();
        this.random = new Random();
        
        // Стандартні параметри
        this.enableSplashPotions = true;
        this.enableLingeringPotions = true;
        this.enablePotionEffects = true;
        this.maxPotionAmplifier = 3;
        this.maxPotionDuration = 36000; // 1800 секунд = 30 хвилин
        
        // Ініціалізуємо зілля
        initializePotions();
    }
    
    // Геттери та сеттери для параметрів
    public boolean isEnableSplashPotions() {
        return enableSplashPotions;
    }
    
    public void setEnableSplashPotions(boolean enableSplashPotions) {
        this.enableSplashPotions = enableSplashPotions;
    }
    
    public boolean isEnableLingeringPotions() {
        return enableLingeringPotions;
    }
    
    public void setEnableLingeringPotions(boolean enableLingeringPotions) {
        this.enableLingeringPotions = enableLingeringPotions;
    }
    
    public boolean isEnablePotionEffects() {
        return enablePotionEffects;
    }
    
    public void setEnablePotionEffects(boolean enablePotionEffects) {
        this.enablePotionEffects = enablePotionEffects;
    }
    
    public int getMaxPotionAmplifier() {
        return maxPotionAmplifier;
    }
    
    public void setMaxPotionAmplifier(int maxPotionAmplifier) {
        this.maxPotionAmplifier = maxPotionAmplifier;
    }
    
    public int getMaxPotionDuration() {
        return maxPotionDuration;
    }
    
    public void setMaxPotionDuration(int maxPotionDuration) {
        this.maxPotionDuration = maxPotionDuration;
    }
    
    /**
     * Ініціалізує всі зілля
     */
    private void initializePotions() {
        // Базові зілля
        addPotion(new Potion(PotionType.WATER, "water", "Вода", PotionRarity.COMMON));
        addPotion(new Potion(PotionType.AWKWARD, "awkward", "Незручне зілля", PotionRarity.COMMON));
        addPotion(new Potion(PotionType.THICK, "thick", "Густе зілля", PotionRarity.COMMON));
        addPotion(new Potion(PotionType.MUNDANE, "mundane", "Звичайне зілля", PotionRarity.COMMON));
        
        // Зілля лікування
        Potion healing = new Potion(PotionType.HEALING, "healing", "Зілля лікування", PotionRarity.UNCOMMON);
        healing.addEffect(PotionEffect.INSTANT_HEALTH, 1, 0);
        addPotion(healing);
        
        // Потужне зілля лікування
        Potion strongHealing = new Potion(PotionType.STRONG_HEALING, "strong_healing", "Потужне зілля лікування", PotionRarity.RARE);
        strongHealing.addEffect(PotionEffect.INSTANT_HEALTH, 1, 1);
        addPotion(strongHealing);
        
        // Зілля шкоди
        Potion harming = new Potion(PotionType.HARMING, "harming", "Зілля шкоди", PotionRarity.UNCOMMON);
        harming.addEffect(PotionEffect.INSTANT_DAMAGE, 1, 0);
        addPotion(harming);
        
        // Потужне зілля шкоди
        Potion strongHarming = new Potion(PotionType.STRONG_HARMING, "strong_harming", "Потужне зілля шкоди", PotionRarity.RARE);
        strongHarming.addEffect(PotionEffect.INSTANT_DAMAGE, 1, 1);
        addPotion(strongHarming);
        
        // Зілля регенерації
        Potion regeneration = new Potion(PotionType.REGENERATION, "regeneration", "Зілля регенерації", PotionRarity.RARE);
        regeneration.addEffect(PotionEffect.REGENERATION, 900, 0);
        addPotion(regeneration);
        
        // Довге зілля регенерації
        Potion longRegeneration = new Potion(PotionType.LONG_REGENERATION, "long_regeneration", "Довге зілля регенерації", PotionRarity.RARE);
        longRegeneration.addEffect(PotionEffect.REGENERATION, 1800, 0);
        addPotion(longRegeneration);
        
        // Потужне зілля регенерації
        Potion strongRegeneration = new Potion(PotionType.STRONG_REGENERATION, "strong_regeneration", "Потужне зілля регенерації", PotionRarity.EPIC);
        strongRegeneration.addEffect(PotionEffect.REGENERATION, 450, 1);
        addPotion(strongRegeneration);
        
        // Зілля сили
        Potion strength = new Potion(PotionType.STRENGTH, "strength", "Зілля сили", PotionRarity.RARE);
        strength.addEffect(PotionEffect.STRENGTH, 1800, 0);
        addPotion(strength);
        
        // Довге зілля сили
        Potion longStrength = new Potion(PotionType.LONG_STRENGTH, "long_strength", "Довге зілля сили", PotionRarity.RARE);
        longStrength.addEffect(PotionEffect.STRENGTH, 3600, 0);
        addPotion(longStrength);
        
        // Потужне зілля сили
        Potion strongStrength = new Potion(PotionType.STRONG_STRENGTH, "strong_strength", "Потужне зілля сили", PotionRarity.EPIC);
        strongStrength.addEffect(PotionEffect.STRENGTH, 900, 1);
        addPotion(strongStrength);
        
        // Зілля швидкості
        Potion swiftness = new Potion(PotionType.SWIFTNESS, "swiftness", "Зілля швидкості", PotionRarity.UNCOMMON);
        swiftness.addEffect(PotionEffect.SPEED, 1800, 0);
        addPotion(swiftness);
        
        // Довге зілля швидкості
        Potion longSwiftness = new Potion(PotionType.LONG_SWIFTNESS, "long_swiftness", "Довге зілля швидкості", PotionRarity.UNCOMMON);
        longSwiftness.addEffect(PotionEffect.SPEED, 3600, 0);
        addPotion(longSwiftness);
        
        // Потужне зілля швидкості
        Potion strongSwiftness = new Potion(PotionType.STRONG_SWIFTNESS, "strong_swiftness", "Потужне зілля швидкості", PotionRarity.RARE);
        strongSwiftness.addEffect(PotionEffect.SPEED, 900, 1);
        addPotion(strongSwiftness);
        
        // Зілля повільного падіння
        Potion slowFalling = new Potion(PotionType.SLOW_FALLING, "slow_falling", "Зілля повільного падіння", PotionRarity.UNCOMMON);
        slowFalling.addEffect(PotionEffect.SLOW_FALLING, 1800, 0);
        addPotion(slowFalling);
        
        // Довге зілля повільного падіння
        Potion longSlowFalling = new Potion(PotionType.LONG_SLOW_FALLING, "long_slow_falling", "Довге зілля повільного падіння", PotionRarity.UNCOMMON);
        longSlowFalling.addEffect(PotionEffect.SLOW_FALLING, 3600, 0);
        addPotion(longSlowFalling);
        
        // Зілля вогнестійкості
        Potion fireResistance = new Potion(PotionType.FIRE_RESISTANCE, "fire_resistance", "Зілля вогнестійкості", PotionRarity.RARE);
        fireResistance.addEffect(PotionEffect.FIRE_RESISTANCE, 3600, 0);
        addPotion(fireResistance);
        
        // Довге зілля вогнестійкості
        Potion longFireResistance = new Potion(PotionType.LONG_FIRE_RESISTANCE, "long_fire_resistance", "Довге зілля вогнестійкості", PotionRarity.RARE);
        longFireResistance.addEffect(PotionEffect.FIRE_RESISTANCE, 7200, 0);
        addPotion(longFireResistance);
        
        // Зілля дихання під водою
        Potion waterBreathing = new Potion(PotionType.WATER_BREATHING, "water_breathing", "Зілля дихання під водою", PotionRarity.UNCOMMON);
        waterBreathing.addEffect(PotionEffect.WATER_BREATHING, 3600, 0);
        addPotion(waterBreathing);
        
        // Довге зілля дихання під водою
        Potion longWaterBreathing = new Potion(PotionType.LONG_WATER_BREATHING, "long_water_breathing", "Довге зілля дихання під водою", PotionRarity.UNCOMMON);
        longWaterBreathing.addEffect(PotionEffect.WATER_BREATHING, 7200, 0);
        addPotion(longWaterBreathing);
        
        // Зілля нічного бачення
        Potion nightVision = new Potion(PotionType.NIGHT_VISION, "night_vision", "Зілля нічного бачення", PotionRarity.RARE);
        nightVision.addEffect(PotionEffect.NIGHT_VISION, 3600, 0);
        addPotion(nightVision);
        
        // Довге зілля нічного бачення
        Potion longNightVision = new Potion(PotionType.LONG_NIGHT_VISION, "long_night_vision", "Довге зілля нічного бачення", PotionRarity.RARE);
        longNightVision.addEffect(PotionEffect.NIGHT_VISION, 7200, 0);
        addPotion(longNightVision);
        
        // Зілля невидимості
        Potion invisibility = new Potion(PotionType.INVISIBILITY, "invisibility", "Зілля невидимості", PotionRarity.EPIC);
        invisibility.addEffect(PotionEffect.INVISIBILITY, 3600, 0);
        addPotion(invisibility);
        
        // Довге зілля невидимості
        Potion longInvisibility = new Potion(PotionType.LONG_INVISIBILITY, "long_invisibility", "Довге зілля невидимості", PotionRarity.EPIC);
        longInvisibility.addEffect(PotionEffect.INVISIBILITY, 7200, 0);
        addPotion(longInvisibility);
        
        // Зілля стрибучості
        Potion leaping = new Potion(PotionType.LEAPING, "leaping", "Зілля стрибучості", PotionRarity.UNCOMMON);
        leaping.addEffect(PotionEffect.JUMP_BOOST, 1800, 0);
        addPotion(leaping);
        
        // Довге зілля стрибучості
        Potion longLeaping = new Potion(PotionType.LONG_LEAPING, "long_leaping", "Довге зілля стрибучості", PotionRarity.UNCOMMON);
        longLeaping.addEffect(PotionEffect.JUMP_BOOST, 3600, 0);
        addPotion(longLeaping);
        
        // Потужне зілля стрибучості
        Potion strongLeaping = new Potion(PotionType.STRONG_LEAPING, "strong_leaping", "Потужне зілля стрибучості", PotionRarity.RARE);
        strongLeaping.addEffect(PotionEffect.JUMP_BOOST, 900, 1);
        addPotion(strongLeaping);
        
        // Зілля отруєння
        Potion poison = new Potion(PotionType.POISON, "poison", "Зілля отруєння", PotionRarity.UNCOMMON);
        poison.addEffect(PotionEffect.POISON, 900, 0);
        addPotion(poison);
        
        // Довге зілля отруєння
        Potion longPoison = new Potion(PotionType.LONG_POISON, "long_poison", "Довге зілля отруєння", PotionRarity.UNCOMMON);
        longPoison.addEffect(PotionEffect.POISON, 1800, 0);
        addPotion(longPoison);
        
        // Потужне зілля отруєння
        Potion strongPoison = new Potion(PotionType.STRONG_POISON, "strong_poison", "Потужне зілля отруєння", PotionRarity.RARE);
        strongPoison.addEffect(PotionEffect.POISON, 450, 1);
        addPotion(strongPoison);
        
        // Зілля слабкості
        Potion weakness = new Potion(PotionType.WEAKNESS, "weakness", "Зілля слабкості", PotionRarity.UNCOMMON);
        weakness.addEffect(PotionEffect.WEAKNESS, 1800, 0);
        addPotion(weakness);
        
        // Довге зілля слабкості
        Potion longWeakness = new Potion(PotionType.LONG_WEAKNESS, "long_weakness", "Довге зілля слабкості", PotionRarity.UNCOMMON);
        longWeakness.addEffect(PotionEffect.WEAKNESS, 3600, 0);
        addPotion(longWeakness);
        
        // Зілля сповільнення
        Potion slowness = new Potion(PotionType.SLOWNESS, "slowness", "Зілля сповільнення", PotionRarity.UNCOMMON);
        slowness.addEffect(PotionEffect.SLOWNESS, 1800, 0);
        addPotion(slowness);
        
        // Довге зілля сповільнення
        Potion longSlowness = new Potion(PotionType.LONG_SLOWNESS, "long_slowness", "Довге зілля сповільнення", PotionRarity.UNCOMMON);
        longSlowness.addEffect(PotionEffect.SLOWNESS, 3600, 0);
        addPotion(longSlowness);
        
        // Майстер черепахи
        Potion turtleMaster = new Potion(PotionType.TURTLE_MASTER, "turtle_master", "Зілля майстра черепахи", PotionRarity.EPIC);
        turtleMaster.addEffect(PotionEffect.RESISTANCE, 400, 3);
        turtleMaster.addEffect(PotionEffect.SLOWNESS, 400, 3);
        addPotion(turtleMaster);
        
        // Удача
        Potion luck = new Potion(PotionType.LUCK, "luck", "Зілля удачі", PotionRarity.LEGENDARY);
        luck.addEffect(PotionEffect.LUCK, 3600, 0);
        addPotion(luck);
    }
    
    /**
     * Додає зілля до мапи
     */
    private void addPotion(Potion potion) {
        potions.put(potion.getType(), potion);
    }
    
    /**
     * Отримує зілля за типом
     */
    public Potion getPotion(PotionType type) {
        return potions.get(type);
    }
    
    /**
     * Отримує всі зілля
     */
    public Map<PotionType, Potion> getAllPotions() {
        return new HashMap<>(potions);
    }
    
    /**
     * Створює зілля з ефектами
     */
    public ItemStack createPotion(PotionType type) {
        Potion potion = potions.get(type);
        if (potion == null) {
            return null;
        }
        
        // Створюємо предмет зілля
        String potionName = potion.isSplash() ? "SPLASH_POTION" : 
                           potion.isLingering() ? "LINGERING_POTION" : "POTION";
        
        ItemStack potionItem = new ItemStack(potionName, 1);
        // Note: ItemStack doesn't have setDisplayName method, using ItemComponent instead
        // For now, we'll just create the potion item without setting display name
        
        return potionItem;
    }
    
    /**
     * Змішує два зілля для створення нового
     */
    public PotionType mixPotions(PotionType basePotion, String ingredient) {
        // Реалізація змішування зілля
        // Це спрощена версія, в реальності буде більш складна логіка
        
        switch (basePotion) {
            case AWKWARD:
                return getAwkwardPotionResult(ingredient);
            case WATER:
                return getWaterPotionResult(ingredient);
            case THICK:
                return getThickPotionResult(ingredient);
            case MUNDANE:
                return getMundanePotionResult(ingredient);
            default:
                return getPotionMixResult(basePotion, ingredient);
        }
    }
    
    /**
     * Отримує результат змішування незручного зілля
     */
    private PotionType getAwkwardPotionResult(String ingredient) {
        switch (ingredient) {
            case "NETHER_WART":
                return PotionType.AWKWARD;
            case "GOLDEN_CARROT":
                return PotionType.NIGHT_VISION;
            case "RABBIT_FOOT":
                return PotionType.LEAPING;
            case "MAGMA_CREAM":
                return PotionType.FIRE_RESISTANCE;
            case "SUGAR":
                return PotionType.SWIFTNESS;
            case "ROTTEN_FLESH":
                return PotionType.POISON;
            case "SPIDER_EYE":
                return PotionType.POISON;
            case "GHAST_TEAR":
                return PotionType.REGENERATION;
            case "BLAZE_POWDER":
                return PotionType.STRENGTH;
            case "GLOWSTONE_DUST":
                return PotionType.THICK;
            case "REDSTONE":
                return PotionType.MUNDANE;
            case "GUNPOWDER":
                return PotionType.SPLASH;
            case "DRAGON_BREATH":
                return PotionType.LINGERING;
            default:
                return PotionType.AWKWARD;
        }
    }
    
    /**
     * Отримує результат змішування води
     */
    private PotionType getWaterPotionResult(String ingredient) {
        switch (ingredient) {
            case "NETHER_WART":
                return PotionType.AWKWARD;
            case "GLISTERING_MELON":
                return PotionType.HEALING;
            case "GHAST_TEAR":
                return PotionType.REGENERATION;
            case "RABBIT_FOOT":
                return PotionType.LEAPING;
            case "SPIDER_EYE":
                return PotionType.HARMING;
            case "MAGMA_CREAM":
                return PotionType.FIRE_RESISTANCE;
            case "SUGAR":
                return PotionType.SWIFTNESS;
            case "ROTTEN_FLESH":
                return PotionType.POISON;
            case "BLAZE_POWDER":
                return PotionType.STRENGTH;
            case "GOLDEN_CARROT":
                return PotionType.NIGHT_VISION;
            default:
                return PotionType.WATER;
        }
    }
    
    /**
     * Отримує результат змішування густого зілля
     */
    private PotionType getThickPotionResult(String ingredient) {
        switch (ingredient) {
            case "GLOWSTONE_DUST":
                return PotionType.THICK;
            default:
                return PotionType.THICK;
        }
    }
    
    /**
     * Отримує результат змішування звичайного зілля
     */
    private PotionType getMundanePotionResult(String ingredient) {
        switch (ingredient) {
            case "REDSTONE":
                return PotionType.MUNDANE;
            default:
                return PotionType.MUNDANE;
        }
    }
    
    /**
     * Отримує результат змішування двох зілля
     */
    private PotionType getPotionMixResult(PotionType basePotion, String ingredient) {
        // Реалізація змішування двох зілля
        // Це спрощена версія, в реальності буде більш складна логіка
        
        switch (ingredient) {
            case "REDSTONE":
                return getLongPotionVariant(basePotion);
            case "GLOWSTONE_DUST":
                return getStrongPotionVariant(basePotion);
            case "GUNPOWDER":
                return getSplashPotionVariant(basePotion);
            case "DRAGON_BREATH":
                return getLingeringPotionVariant(basePotion);
            default:
                return basePotion;
        }
    }
    
    /**
     * Отримує довгу версію зілля
     */
    private PotionType getLongPotionVariant(PotionType basePotion) {
        switch (basePotion) {
            case NIGHT_VISION:
                return PotionType.LONG_NIGHT_VISION;
            case INVISIBILITY:
                return PotionType.LONG_INVISIBILITY;
            case LEAPING:
                return PotionType.LONG_LEAPING;
            case FIRE_RESISTANCE:
                return PotionType.LONG_FIRE_RESISTANCE;
            case SWIFTNESS:
                return PotionType.LONG_SWIFTNESS;
            case SLOWNESS:
                return PotionType.LONG_SLOWNESS;
            case WATER_BREATHING:
                return PotionType.LONG_WATER_BREATHING;
            case POISON:
                return PotionType.LONG_POISON;
            case REGENERATION:
                return PotionType.LONG_REGENERATION;
            case STRENGTH:
                return PotionType.LONG_STRENGTH;
            case WEAKNESS:
                return PotionType.LONG_WEAKNESS;
            case SLOW_FALLING:
                return PotionType.LONG_SLOW_FALLING;
            default:
                return basePotion;
        }
    }
    
    /**
     * Отримує потужну версію зілля
     */
    private PotionType getStrongPotionVariant(PotionType basePotion) {
        switch (basePotion) {
            case HEALING:
                return PotionType.STRONG_HEALING;
            case HARMING:
                return PotionType.STRONG_HARMING;
            case POISON:
                return PotionType.STRONG_POISON;
            case REGENERATION:
                return PotionType.STRONG_REGENERATION;
            case STRENGTH:
                return PotionType.STRONG_STRENGTH;
            case LEAPING:
                return PotionType.STRONG_LEAPING;
            case SWIFTNESS:
                return PotionType.STRONG_SWIFTNESS;
            default:
                return basePotion;
        }
    }
    
    /**
     * Отримує вибухову версію зілля
     */
    private PotionType getSplashPotionVariant(PotionType basePotion) {
        // В реальній реалізації тут була б логіка створення вибухового зілля
        return basePotion;
    }
    
    /**
     * Отримує осадну версію зілля
     */
    private PotionType getLingeringPotionVariant(PotionType basePotion) {
        // В реальній реалізації тут була б логіка створення осадного зілля
        return basePotion;
    }
    
    /**
     * Застосовує ефекти зілля до гравця
     */
    public void applyPotionEffects(ItemStack potionItem, Object player) {
        if (!enablePotionEffects) {
            return;
        }
        
        // У реальній реалізації тут була б логіка застосування ефектів до гравця
        System.out.println("Applying potion effects to player");
    }
    
    /**
     * Отримує випадкове зілля
     */
    public PotionType getRandomPotion() {
        List<PotionType> potionTypes = new ArrayList<>(potions.keySet());
        return potionTypes.get(random.nextInt(potionTypes.size()));
    }
    
    /**
     * Перевіряє, чи зілля має певний ефект
     */
    public boolean potionHasEffect(PotionType potionType, PotionEffect effect) {
        Potion potion = potions.get(potionType);
        if (potion == null) {
            return false;
        }
        
        for (PotionEffectData effectData : potion.getEffects()) {
            if (effectData.getEffect() == effect) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Отримує тривалість ефекту зілля
     */
    public int getPotionEffectDuration(PotionType potionType, PotionEffect effect) {
        Potion potion = potions.get(potionType);
        if (potion == null) {
            return 0;
        }
        
        for (PotionEffectData effectData : potion.getEffects()) {
            if (effectData.getEffect() == effect) {
                return effectData.getDuration();
            }
        }
        
        return 0;
    }
}