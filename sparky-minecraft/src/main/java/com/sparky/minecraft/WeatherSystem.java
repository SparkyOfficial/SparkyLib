package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;
import com.sparky.ecs.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

/**
 * Система для управління погодою в світі Minecraft.
 *
 * @author Андрій Будильников
 */
public class WeatherSystem extends com.sparky.ecs.System {
    private EntityManager entityManager;
    private Random random;
    private long lastUpdateTime;
    
    public WeatherSystem() {
        this.random = new Random();
        this.lastUpdateTime = System.currentTimeMillis();
    }
    
    public WeatherSystem(EntityManager entityManager) {
        this();
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastUpdateTime;
        this.lastUpdateTime = currentTime;
        
        
        for (Entity entity : entities) {
            if (entity.hasComponent(WeatherComponent.class)) {
                WeatherComponent weather = entity.getComponent(WeatherComponent.class);
                weather.update(deltaTime);
                
                // Якщо погода не циклічна і закінчилася, можемо згенерувати нову
                if (!weather.isCyclic() && weather.getTimeRemaining() <= 0) {
                    generateRandomWeather(weather);
                }
            }
        }
    }
    
    /**
     * Генерує випадкову погоду.
     *
     * @param weather компонент погоди для оновлення
     */
    private void generateRandomWeather(WeatherComponent weather) {
        int chance = random.nextInt(100);
        
        if (chance < 70) {
            // 70% шанс ясної погоди
            weather.changeWeather(WeatherComponent.WeatherType.CLEAR, 0.0f, 300000); // 5 хвилин
        } else if (chance < 90) {
            // 20% шанс дощу
            weather.changeWeather(WeatherComponent.WeatherType.RAIN, 0.7f, 180000); // 3 хвилини
        } else if (chance < 95) {
            // 5% шанс громової бурі
            weather.changeWeather(WeatherComponent.WeatherType.THUNDERSTORM, 1.0f, 120000); // 2 хвилини
        } else {
            // 5% шанс снігу
            weather.changeWeather(WeatherComponent.WeatherType.SNOW, 0.5f, 240000); // 4 хвилини
        }
    }
    
    /**
     * Створює компонент погоди для світу.
     *
     * @param worldEntity сутність світу
     * @return true, якщо компонент створено успішно
     */
    public boolean createWeatherForWorld(Entity worldEntity) {
        if (worldEntity == null) {
            return false;
        }
        
        // Видаляємо існуючий компонент погоди, якщо він є
        if (worldEntity.hasComponent(WeatherComponent.class)) {
            worldEntity.removeComponent(WeatherComponent.class);
        }
        
        // Створюємо новий компонент погоди
        WeatherComponent weather = new WeatherComponent();
        
        // Додаємо компонент до сутності через EntityManager
        if (entityManager != null) {
            entityManager.addComponentToEntity(worldEntity, weather);
        }
        
        return true;
    }
    
    /**
     * Змінює погоду в світі.
     *
     * @param worldEntityId ID сутності світу
     * @param weatherType новий тип погоди
     * @param intensity інтенсивність (0.0 - 1.0)
     * @param duration тривалість в мілісекундах
     * @return true, якщо погоду змінено успішно
     */
    public boolean changeWeather(int worldEntityId, WeatherComponent.WeatherType weatherType, 
                                float intensity, long duration) {
        if (entityManager == null) {
            return false;
        }
        
        Entity worldEntity = entityManager.getEntity(worldEntityId);
        if (worldEntity == null || !worldEntity.hasComponent(WeatherComponent.class)) {
            return false;
        }
        
        WeatherComponent weather = worldEntity.getComponent(WeatherComponent.class);
        weather.changeWeather(weatherType, intensity, duration);
        return true;
    }
    
    /**
     * Отримує поточну погоду в світі.
     *
     * @param worldEntityId ID сутності світу
     * @return поточний тип погоди або null, якщо помилка
     */
    public WeatherComponent.WeatherType getCurrentWeather(int worldEntityId) {
        if (entityManager == null) {
            return null;
        }
        
        Entity worldEntity = entityManager.getEntity(worldEntityId);
        if (worldEntity == null || !worldEntity.hasComponent(WeatherComponent.class)) {
            return null;
        }
        
        WeatherComponent weather = worldEntity.getComponent(WeatherComponent.class);
        return weather.getCurrentWeather();
    }
    
    /**
     * Перевіряє, чи зараз іде дощ в світі.
     *
     * @param worldEntityId ID сутності світу
     * @return true, якщо зараз іде дощ
     */
    public boolean isRaining(int worldEntityId) {
        if (entityManager == null) {
            return false;
        }
        
        Entity worldEntity = entityManager.getEntity(worldEntityId);
        if (worldEntity == null || !worldEntity.hasComponent(WeatherComponent.class)) {
            return false;
        }
        
        WeatherComponent weather = worldEntity.getComponent(WeatherComponent.class);
        return weather.isRaining();
    }
    
    /**
     * Перевіряє, чи зараз громова буря в світі.
     *
     * @param worldEntityId ID сутності світу
     * @return true, якщо зараз громова буря
     */
    public boolean isThundering(int worldEntityId) {
        if (entityManager == null) {
            return false;
        }
        
        Entity worldEntity = entityManager.getEntity(worldEntityId);
        if (worldEntity == null || !worldEntity.hasComponent(WeatherComponent.class)) {
            return false;
        }
        
        WeatherComponent weather = worldEntity.getComponent(WeatherComponent.class);
        return weather.isThundering();
    }
    
    /**
     * Встановлює циклічність погоди.
     *
     * @param worldEntityId ID сутності світу
     * @param cyclic чи погода циклічна
     * @return true, якщо циклічність встановлено успішно
     */
    public boolean setWeatherCyclic(int worldEntityId, boolean cyclic) {
        if (entityManager == null) {
            return false;
        }
        
        Entity worldEntity = entityManager.getEntity(worldEntityId);
        if (worldEntity == null || !worldEntity.hasComponent(WeatherComponent.class)) {
            return false;
        }
        
        WeatherComponent weather = worldEntity.getComponent(WeatherComponent.class);
        weather.setCyclic(cyclic);
        return true;
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(WeatherComponent.class);
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