package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;
import java.util.List;
import java.util.ArrayList;

/**
 * Система освітлення для 3D рендерингу.
 *
 * @author Андрій Будильников
 */
public class LightingSystem {
    private List<Light> lights;
    private Vector3D ambientLight;
    
    public LightingSystem() {
        this.lights = new ArrayList<>();
        this.ambientLight = new Vector3D(0.2, 0.2, 0.2); // м'яке навколишнє освітлення
    }
    
    // ініціалізує стандартні джерела світла
    public void initializeDefaultLights() {
        // додаємо направлене світло (сонце)
        DirectionalLight sun = new DirectionalLight(
            new Vector3D(1, 1, 1), // біле світло
            new Vector3D(-1, -1, -1).normalize(), // напрямок
            1.0 // інтенсивність
        );
        lights.add(sun);
        
        System.out.println("Default lights initialized");
    }
    
    // додає джерело світла
    public void addLight(Light light) {
        lights.add(light);
    }
    
    // видаляє джерело світла
    public void removeLight(Light light) {
        lights.remove(light);
    }
    
    // застосовує освітлення до точки
    public Vector3D applyLighting(Vector3D position) {
        Vector3D finalColor = new Vector3D(ambientLight);
        
        // застосовуємо всі джерела світла
        for (Light light : lights) {
            Vector3D lightContribution = light.calculateLighting(position);
            finalColor = finalColor.add(lightContribution);
        }
        
        // обмежуємо значення між 0 та 1
        return new Vector3D(
            Math.max(0, Math.min(1, finalColor.getX())),
            Math.max(0, Math.min(1, finalColor.getY())),
            Math.max(0, Math.min(1, finalColor.getZ()))
        );
    }
    
    // встановлює навколишнє освітлення
    public void setAmbientLight(Vector3D ambientLight) {
        this.ambientLight = new Vector3D(ambientLight);
    }
    
    // отримує навколишнє освітлення
    public Vector3D getAmbientLight() {
        return new Vector3D(ambientLight);
    }
    
    // отримує всі джерела світла
    public List<Light> getLights() {
        return new ArrayList<>(lights);
    }
}