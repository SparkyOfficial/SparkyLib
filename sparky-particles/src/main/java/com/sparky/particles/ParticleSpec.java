package com.sparky.particles;

import java.util.Map;

/**
 * Специфікація частинки для декларативного опису ефектів.
 *
 * @author Андрій Будильников
 */
public class ParticleSpec {
    private String type;
    private float speed;
    private int count;
    private Map<String, Object> properties;
    
    public ParticleSpec() {}
    
    public ParticleSpec(String type, float speed, int count, Map<String, Object> properties) {
        this.type = type;
        this.speed = speed;
        this.count = count;
        this.properties = properties;
    }
    
    // Getters and setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}