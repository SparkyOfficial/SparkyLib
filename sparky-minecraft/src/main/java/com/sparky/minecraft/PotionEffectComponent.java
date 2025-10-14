package com.sparky.minecraft;

import com.sparky.ecs.Component;

/**
 * Компонент для представлення ефекту зілля в Minecraft.
 * Використовується для управління ефектами, які впливають на гравців та інших істот.
 *
 * @author Богдан Кравчук
 */
public class PotionEffectComponent extends Component {
    public enum PotionEffectType {
        SPEED,
        SLOWNESS,
        HASTE,
        MINING_FATIGUE,
        STRENGTH,
        INSTANT_HEALTH,
        INSTANT_DAMAGE,
        JUMP_BOOST,
        NAUSEA,
        REGENERATION,
        RESISTANCE,
        FIRE_RESISTANCE,
        WATER_BREATHING,
        INVISIBILITY,
        BLINDNESS,
        NIGHT_VISION,
        HUNGER,
        WEAKNESS,
        POISON,
        WITHER,
        HEALTH_BOOST,
        ABSORPTION,
        SATURATION,
        GLOWING,
        LEVITATION,
        LUCK,
        UNLUCK,
        SLOW_FALLING,
        CONDUIT_POWER,
        DOLPHINS_GRACE,
        BAD_OMEN,
        HERO_OF_THE_VILLAGE
    }
    
    public enum EffectParticles {
        NONE,
        SMALL,
        MEDIUM,
        LARGE
    }
    
    private PotionEffectType effectType;
    private int amplifier;
    private int duration;
    private boolean ambient;
    private boolean showParticles;
    private boolean showIcon;
    private EffectParticles particleType;
    private int color;
    
    public PotionEffectComponent() {
        this(PotionEffectType.SPEED, 0, 300, false, true, true, EffectParticles.MEDIUM, 0x7CAFC6);
    }
    
    public PotionEffectComponent(PotionEffectType effectType, int amplifier, int duration,
                                boolean ambient, boolean showParticles, boolean showIcon,
                                EffectParticles particleType, int color) {
        this.effectType = effectType;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
        this.particleType = particleType;
        this.color = color;
    }
    
    public PotionEffectType getEffectType() {
        return effectType;
    }
    
    public void setEffectType(PotionEffectType effectType) {
        this.effectType = effectType;
    }
    
    public int getAmplifier() {
        return amplifier;
    }
    
    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public boolean isAmbient() {
        return ambient;
    }
    
    public void setAmbient(boolean ambient) {
        this.ambient = ambient;
    }
    
    public boolean shouldShowParticles() {
        return showParticles;
    }
    
    public void setShowParticles(boolean showParticles) {
        this.showParticles = showParticles;
    }
    
    public boolean shouldShowIcon() {
        return showIcon;
    }
    
    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
    }
    
    public EffectParticles getParticleType() {
        return particleType;
    }
    
    public void setParticleType(EffectParticles particleType) {
        this.particleType = particleType;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
    }
    
    /**
     * Перевіряє, чи ефект активний.
     *
     * @return true, якщо ефект активний
     */
    public boolean isActive() {
        return duration > 0;
    }
    
    /**
     * Зменшує тривалість ефекту.
     *
     * @param ticks кількість тіків для зменшення
     */
    public void reduceDuration(int ticks) {
        duration = Math.max(0, duration - ticks);
    }
    
    /**
     * Оновлює ефект.
     */
    public void update() {
        if (duration > 0) {
            duration--;
        }
    }
    
    /**
     * Перевіряє, чи ефект є позитивним.
     *
     * @return true, якщо ефект позитивний
     */
    public boolean isBeneficial() {
        switch (effectType) {
            case SPEED:
            case HASTE:
            case STRENGTH:
            case INSTANT_HEALTH:
            case JUMP_BOOST:
            case REGENERATION:
            case RESISTANCE:
            case FIRE_RESISTANCE:
            case WATER_BREATHING:
            case INVISIBILITY:
            case NIGHT_VISION:
            case HEALTH_BOOST:
            case ABSORPTION:
            case SATURATION:
            case CONDUIT_POWER:
            case DOLPHINS_GRACE:
            case HERO_OF_THE_VILLAGE:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Отримує назву ефекту.
     *
     * @return назва ефекту
     */
    public String getEffectName() {
        return effectType.name().toLowerCase().replace("_", " ");
    }
    
    /**
     * Отримує модифікатор ефекту.
     *
     * @return модифікатор у вигляді римського числа
     */
    public String getAmplifierRoman() {
        switch (amplifier) {
            case 0: return "I";
            case 1: return "II";
            case 2: return "III";
            case 3: return "IV";
            case 4: return "V";
            default: return "I";
        }
    }
}