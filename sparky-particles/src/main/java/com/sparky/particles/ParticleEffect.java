package com.sparky.particles;

import java.util.List;

/**
 * Представляє ефект частинок, що складається з кількох специфікацій.
 *
 * @author Андрій Будильников
 */
public class ParticleEffect {
    private String name;
    private List<ParticleSpec> specs;
    
    public ParticleEffect() {}
    
    public ParticleEffect(String name, List<ParticleSpec> specs) {
        this.name = name;
        this.specs = specs;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<ParticleSpec> getSpecs() {
        return specs;
    }
    
    public void setSpecs(List<ParticleSpec> specs) {
        this.specs = specs;
    }
}