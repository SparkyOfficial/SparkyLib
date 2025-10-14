package com.sparky.minecraft;

import com.sparky.ecs.Component;

/**
 * Компонент погоди для представлення поточних погодних умов у світі.
 *
 * @author Андрій Будильников
 */
public class WeatherComponent extends Component {
    public enum WeatherType {
        CLEAR, RAIN, THUNDERSTORM, SNOW
    }
    
    private WeatherType currentWeather;
    private float intensity;
    private long weatherDuration;
    private long timeRemaining;
    private boolean isCyclic;
    
    public WeatherComponent() {
        this(WeatherType.CLEAR, 0.0f, 600000, 600000, true); // 10 хвилин за замовчуванням
    }
    
    public WeatherComponent(WeatherType currentWeather, float intensity, 
                           long weatherDuration, long timeRemaining, boolean isCyclic) {
        this.currentWeather = currentWeather;
        this.intensity = Math.max(0.0f, Math.min(1.0f, intensity));
        this.weatherDuration = weatherDuration;
        this.timeRemaining = timeRemaining;
        this.isCyclic = isCyclic;
    }
    
    /**
     * Оновлює погоду з плином часу.
     *
     * @param deltaTime час, що пройшов з останнього оновлення (в мілісекундах)
     */
    public void update(long deltaTime) {
        timeRemaining -= deltaTime;
        
        if (timeRemaining <= 0) {
            if (isCyclic) {
                // Скидаємо погоду до початкового стану
                timeRemaining = weatherDuration;
            } else {
                // Переходимо до ясної погоди
                currentWeather = WeatherType.CLEAR;
                intensity = 0.0f;
                timeRemaining = 0;
            }
        }
    }
    
    /**
     * Змінює погоду.
     *
     * @param newWeather новий тип погоди
     * @param newIntensity нова інтенсивність (0.0 - 1.0)
     * @param duration тривалість нової погоди в мілісекундах
     */
    public void changeWeather(WeatherType newWeather, float newIntensity, long duration) {
        this.currentWeather = newWeather;
        this.intensity = Math.max(0.0f, Math.min(1.0f, newIntensity));
        this.weatherDuration = duration;
        this.timeRemaining = duration;
    }
    
    /**
     * Перевіряє, чи зараз іде дощ.
     *
     * @return true, якщо зараз іде дощ
     */
    public boolean isRaining() {
        return currentWeather == WeatherType.RAIN || currentWeather == WeatherType.THUNDERSTORM;
    }
    
    /**
     * Перевіряє, чи зараз громова буря.
     *
     * @return true, якщо зараз громова буря
     */
    public boolean isThundering() {
        return currentWeather == WeatherType.THUNDERSTORM;
    }
    
    /**
     * Перевіряє, чи зараз іде сніг.
     *
     * @return true, якщо зараз іде сніг
     */
    public boolean isSnowing() {
        return currentWeather == WeatherType.SNOW;
    }
    
    /**
     * Перевіряє, чи погода ясна.
     *
     * @return true, якщо погода ясна
     */
    public boolean isClear() {
        return currentWeather == WeatherType.CLEAR;
    }
    
    
    public WeatherType getCurrentWeather() {
        return currentWeather;
    }
    
    public void setCurrentWeather(WeatherType currentWeather) {
        this.currentWeather = currentWeather;
    }
    
    public float getIntensity() {
        return intensity;
    }
    
    public void setIntensity(float intensity) {
        this.intensity = Math.max(0.0f, Math.min(1.0f, intensity));
    }
    
    public long getWeatherDuration() {
        return weatherDuration;
    }
    
    public void setWeatherDuration(long weatherDuration) {
        this.weatherDuration = weatherDuration;
    }
    
    public long getTimeRemaining() {
        return timeRemaining;
    }
    
    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
    
    public boolean isCyclic() {
        return isCyclic;
    }
    
    public void setCyclic(boolean cyclic) {
        isCyclic = cyclic;
    }
    
    @Override
    public String toString() {
        return "WeatherComponent{" +
                "currentWeather=" + currentWeather +
                ", intensity=" + intensity +
                ", timeRemaining=" + timeRemaining +
                '}';
    }
}