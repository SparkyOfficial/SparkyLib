package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;
import com.sparky.ecs.EntityManager;
import com.sparky.ecs.System;
import com.sparky.minecraft.PotionEffectComponent.PotionEffectType;
import com.sparky.minecraft.PotionEffectComponent.EffectParticles;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Система для управління ефектами зілля в Minecraft.
 * Відповідає за застосування, оновлення та видалення ефектів зілля.
 *
 * @author Богдан Кравчук
 */
public class PotionEffectSystem extends System {
    private EntityManager entityManager;
    private Map<Integer, List<PotionEffectComponent>> entityEffects;
    
    public PotionEffectSystem() {
        this.entityEffects = new HashMap<>();
    }
    
    public PotionEffectSystem(EntityManager entityManager) {
        this();
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            List<PotionEffectComponent> effects = entityEffects.get(entity.getId());
            if (effects != null) {
                effects.removeIf(effect -> {
                    effect.update();
                    return !effect.isActive();
                });
                
                for (PotionEffectComponent effect : effects) {
                    applyEffectToEntity(entity, effect);
                }
            }
        }
    }
    
    /**
     * Застосовує ефект до сутності.
     */
    private void applyEffectToEntity(Entity entity, PotionEffectComponent effect) {
        switch (effect.getEffectType()) {
            case SPEED:
                break;
            case SLOWNESS:
                break;
            case STRENGTH:
                break;
            case REGENERATION:
                break;
            case POISON:
                break;
        }
    }
    
    /**
     * Додає ефект зілля до сутності.
     *
     * @param entityId ID сутності
     * @param effect ефект зілля
     * @return true, якщо ефект додано успішно
     */
    public boolean addEffectToEntity(int entityId, PotionEffectComponent effect) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null) {
            return false;
        }
        
        entityEffects.computeIfAbsent(entityId, k -> new ArrayList<>()).add(effect);
        return true;
    }
    
    /**
     * Видаляє конкретний ефект зілля з сутності.
     *
     * @param entityId ID сутності
     * @param effectType тип ефекту
     * @return true, якщо ефект видалено успішно
     */
    public boolean removeEffectFromEntity(int entityId, PotionEffectType effectType) {
        List<PotionEffectComponent> effects = entityEffects.get(entityId);
        if (effects != null) {
            return effects.removeIf(effect -> effect.getEffectType() == effectType);
        }
        return false;
    }
    
    /**
     * Видаляє всі ефекти зілля з сутності.
     *
     * @param entityId ID сутності
     * @return true, якщо ефекти видалено успішно
     */
    public boolean removeAllEffectsFromEntity(int entityId) {
        return entityEffects.remove(entityId) != null;
    }
    
    /**
     * Перевіряє, чи сутність має певний ефект.
     *
     * @param entityId ID сутності
     * @param effectType тип ефекту
     * @return true, якщо сутність має ефект
     */
    public boolean entityHasEffect(int entityId, PotionEffectType effectType) {
        List<PotionEffectComponent> effects = entityEffects.get(entityId);
        if (effects != null) {
            return effects.stream().anyMatch(effect -> effect.getEffectType() == effectType);
        }
        return false;
    }
    
    /**
     * Отримує всі ефекти сутності.
     *
     * @param entityId ID сутності
     * @return список ефектів
     */
    public List<PotionEffectComponent> getEntityEffects(int entityId) {
        List<PotionEffectComponent> effects = entityEffects.get(entityId);
        if (effects != null) {
            return new ArrayList<>(effects);
        }
        return new ArrayList<>();
    }
    
    /**
     * Отримує конкретний ефект сутності.
     *
     * @param entityId ID сутності
     * @param effectType тип ефекту
     * @return ефект або null, якщо не знайдено
     */
    public PotionEffectComponent getEntityEffect(int entityId, PotionEffectType effectType) {
        List<PotionEffectComponent> effects = entityEffects.get(entityId);
        if (effects != null) {
            return effects.stream()
                    .filter(effect -> effect.getEffectType() == effectType)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
    
    /**
     * Отримує кількість активних ефектів у сутності.
     *
     * @param entityId ID сутності
     * @return кількість активних ефектів
     */
    public int getActiveEffectCount(int entityId) {
        List<PotionEffectComponent> effects = entityEffects.get(entityId);
        if (effects != null) {
            return (int) effects.stream().filter(PotionEffectComponent::isActive).count();
        }
        return 0;
    }
    
    /**
     * Створює новий ефект зілля.
     *
     * @param effectType тип ефекту
     * @param amplifier посилення ефекту
     * @param duration тривалість ефекту (в тіках)
     * @return новий ефект зілля
     */
    public PotionEffectComponent createPotionEffect(PotionEffectType effectType, int amplifier, int duration) {
        int color = 0x7CAFC6;
        EffectParticles particleType = EffectParticles.MEDIUM;
        
        switch (effectType) {
            case SPEED:
                color = 0x7CAFC6;
                break;
            case SLOWNESS:
                color = 0x5A6C81;
                break;
            case STRENGTH:
                color = 0x932423;
                break;
            case INSTANT_HEALTH:
                color = 0xF82423;
                particleType = EffectParticles.LARGE;
                break;
            case INSTANT_DAMAGE:
                color = 0x430A09;
                particleType = EffectParticles.LARGE;
                break;
            case JUMP_BOOST:
                color = 0x22FF4C;
                break;
            case REGENERATION:
                color = 0xCD5CAB;
                break;
            case RESISTANCE:
                color = 0x99453A;
                break;
            case FIRE_RESISTANCE:
                color = 0xE49A3A;
                break;
            case WATER_BREATHING:
                color = 0x2E5299;
                break;
            case INVISIBILITY:
                color = 0x7F8392;
                particleType = EffectParticles.SMALL;
                break;
            case BLINDNESS:
                color = 0x1F1F23;
                break;
            case NIGHT_VISION:
                color = 0x1F1FA1;
                break;
            case HUNGER:
                color = 0x587653;
                break;
            case WEAKNESS:
                color = 0x484D48;
                break;
            case POISON:
                color = 0x4E9331;
                break;
            case WITHER:
                color = 0x352A27;
                break;
        }
        
        return new PotionEffectComponent(
            effectType, amplifier, duration, false, true, true, particleType, color);
    }
    
    /**
     * Отримує всі сутності з певним ефектом.
     *
     * @param effectType тип ефекту
     * @return список сутностей з цим ефектом
     */
    public List<Entity> getEntitiesWithEffect(PotionEffectType effectType) {
        List<Entity> result = new ArrayList<>();
        if (entityManager != null) {
            for (Map.Entry<Integer, List<PotionEffectComponent>> entry : entityEffects.entrySet()) {
                int entityId = entry.getKey();
                List<PotionEffectComponent> effects = entry.getValue();
                
                if (effects != null && effects.stream().anyMatch(effect -> effect.getEffectType() == effectType)) {
                    Entity entity = entityManager.getEntity(entityId);
                    if (entity != null) {
                        result.add(entity);
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        return new HashSet<>();
    }
    
    /**
     * Встановлює менеджер сутностей.
     */
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}