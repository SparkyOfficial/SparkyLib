package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.math.Matrix4x4;
import java.util.List;
import java.util.ArrayList;

/**
 * Двигун рендерингу для візуалізації світу Minecraft.
 *
 * @author Андрій Будильников
 */
public class RenderEngine {
    private Camera camera;
    private List<Renderable> renderables;
    private LightingSystem lightingSystem;
    private ShaderManager shaderManager;
    private TextureManager textureManager;
    
    public RenderEngine() {
        this.camera = new Camera();
        this.renderables = new ArrayList<>();
        this.lightingSystem = new LightingSystem();
        this.shaderManager = new ShaderManager();
        this.textureManager = new TextureManager();
    }
    
    // ініціалізує двигун рендерингу
    public void initialize() {
        // ініціалізуємо всі підсистеми
        shaderManager.loadDefaultShaders();
        textureManager.loadDefaultTextures();
        lightingSystem.initializeDefaultLights();
        
        System.out.println("Render engine initialized");
    }
    
    // додає об'єкт для рендерингу
    public void addRenderable(Renderable renderable) {
        renderables.add(renderable);
    }
    
    // видаляє об'єкт з рендерингу
    public void removeRenderable(Renderable renderable) {
        renderables.remove(renderable);
    }
    
    // рендерить кадр
    public void renderFrame() {
        // оновлюємо камеру
        camera.update();
        
        // підготовлюємо матриці перетворення
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();
        
        // очищуємо екран
        clearScreen();
        
        // рендеримо всі об'єкти
        for (Renderable renderable : renderables) {
            renderObject(renderable, viewMatrix, projectionMatrix);
        }
        
        // рендеримо інтерфейс
        renderUI();
        
        // обмін буферів
        swapBuffers();
    }
    
    // рендерить окремий об'єкт
    private void renderObject(Renderable renderable, Matrix4x4 viewMatrix, Matrix4x4 projectionMatrix) {
        // отримуємо модельну матрицю об'єкта
        Matrix4x4 modelMatrix = renderable.getModelMatrix();
        
        // обчислюємо матрицю моделі-вигляду-проекції
        Matrix4x4 mvpMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);
        
        // отримуємо текстуру об'єкта
        String textureName = renderable.getTextureName();
        
        // отримуємо шейдер для об'єкта
        String shaderName = renderable.getShaderName();
        
        // застосовуємо освітлення
        lightingSystem.applyLighting(renderable.getPosition());
        
        // рендеримо об'єкт
        // тут була б реальна логіка рендерингу
        System.out.println("Rendering object: " + renderable.getClass().getSimpleName());
    }
    
    // очищує екран
    private void clearScreen() {
        // тут була б логіка очищення екрану
        System.out.println("Clearing screen");
    }
    
    // рендерить інтерфейс
    private void renderUI() {
        // тут була б логіка рендерингу інтерфейсу
        System.out.println("Rendering UI");
    }
    
    // обмін буферів
    private void swapBuffers() {
        // тут була б логіка обміну буферів
        System.out.println("Swapping buffers");
    }
    
    // встановлює камеру
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    
    // отримує камеру
    public Camera getCamera() {
        return camera;
    }
    
    // отримує систему освітлення
    public LightingSystem getLightingSystem() {
        return lightingSystem;
    }
    
    // отримує менеджер шейдерів
    public ShaderManager getShaderManager() {
        return shaderManager;
    }
    
    // отримує менеджер текстур
    public TextureManager getTextureManager() {
        return textureManager;
    }
    
    // встановлює розмір вікна
    public void setWindowSize(int width, int height) {
        camera.setAspectRatio((double) width / height);
        System.out.println("Window size set to: " + width + "x" + height);
    }
}