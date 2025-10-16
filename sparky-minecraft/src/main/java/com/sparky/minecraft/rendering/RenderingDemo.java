package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.math.Matrix4x4;

/**
 * Демонстрація рендеринг системи.
 *
 * @author Андрій Будильников
 */
public class RenderingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрація рендеринг системи ===\n");
        
        // Демонстрація камери
        demonstrateCamera();
        
        System.out.println();
        
        // Демонстрація освітлення
        demonstrateLighting();
        
        System.out.println();
        
        // Демонстрація текстур
        demonstrateTextures();
        
        System.out.println();
        
        // Демонстрація шейдерів
        demonstrateShaders();
        
        System.out.println();
        
        // Демонстрація спеціалізованих рендерерів
        demonstrateSpecializedRenderers();
        
        System.out.println("\n=== Демонстрація завершена ===");
    }
    
    /**
     * Демонстрація камери
     */
    private static void demonstrateCamera() {
        System.out.println("1. Камера:");
        
        // Створюємо камеру
        Camera camera = new Camera();
        
        // Встановлюємо позицію камери
        camera.setPosition(new Vector3D(0, 10, 0));
        System.out.println("  Позиція камери: " + camera.getPosition());
        
        // Встановлюємо обертання камери
        camera.setRotation(new Vector3D(0, 0, 0));
        System.out.println("  Обертання камери: " + camera.getRotation());
        
        // Встановлюємо FOV
        camera.setFov(70.0);
        System.out.println("  FOV камери: " + camera.getFov());
        
        // Отримуємо матрицю перегляду
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        System.out.println("  Матриця перегляду створена");
        
        // Отримуємо матрицю проекції
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();
        System.out.println("  Матриця проекції створена");
    }
    
    /**
     * Демонстрація освітлення
     */
    private static void demonstrateLighting() {
        System.out.println("2. Освітлення:");
        
        // Створюємо систему освітлення
        LightingSystem lightingSystem = new LightingSystem();
        
        // Створюємо точкове світло
        PointLight pointLight = new PointLight(
            new Vector3D(1.0f, 0.8f, 0.6f), // Тепле біле світло
            new Vector3D(5, 10, 5),
            1.0
        );
        pointLight.setAttenuation(1.0, 0.09, 0.032);
        
        // Додаємо світло до системи
        lightingSystem.addLight(pointLight);
        System.out.println("  Додано точкове світло в позиції " + pointLight.getPosition());
        
        // Створюємо направлене світло (сонце)
        DirectionalLight directionalLight = new DirectionalLight(
            new Vector3D(1.0f, 0.95f, 0.8f), // Світло сонця
            new Vector3D(-0.5f, -1.0f, -0.3f),
            0.8
        );
        
        // Додаємо направлене світло
        lightingSystem.addLight(directionalLight);
        System.out.println("  Додано направлене світло");
        
        // Отримуємо кількість світла
        System.out.println("  Загальна кількість джерел світла: " + lightingSystem.getLights().size());
    }
    
    /**
     * Демонстрація текстур
     */
    private static void demonstrateTextures() {
        System.out.println("3. Текстури:");
        
        // Створюємо менеджер текстур
        TextureManager textureManager = new TextureManager();
        
        // Створюємо текстуру
        Texture texture = new Texture("grass_block.png");
        System.out.println("  Створено текстуру: " + texture.getFilePath());
        System.out.println("    Розмір: " + texture.getWidth() + "x" + texture.getHeight());
        
        // Додаємо текстуру до менеджера
        textureManager.loadTexture("grass_block", "grass_block.png");
        System.out.println("  Текстура додана до менеджера");
        
        // Отримуємо текстуру з менеджера
        Texture retrievedTexture = textureManager.getTexture("grass_block");
        if (retrievedTexture != null) {
            System.out.println("  Текстура отримана з менеджера: " + retrievedTexture.getFilePath());
        }
        
        // Створюємо ще одну текстуру
        textureManager.loadTexture("stone", "stone.png");
        System.out.println("  Додано ще одну текстуру: stone.png");
        
        // Отримуємо кількість текстур
        System.out.println("  Загальна кількість текстур: " + textureManager.getTextureCount());
    }
    
    /**
     * Демонстрація шейдерів
     */
    private static void demonstrateShaders() {
        System.out.println("4. Шейдери:");
        
        // Створюємо менеджер шейдерів
        ShaderManager shaderManager = new ShaderManager();
        
        // Створюємо програму шейдера
        ShaderProgram shaderProgram = new ShaderProgram(
            "vertex_shader.glsl", 
            "fragment_shader.glsl"
        );
        
        System.out.println("  Створено програму шейдера:");
        System.out.println("    Вершинний шейдер: " + shaderProgram.getVertexShaderFile());
        System.out.println("    Фрагментний шейдер: " + shaderProgram.getFragmentShaderFile());
        System.out.println("    Скомпільовано: " + shaderProgram.isCompiled());
        
        // Додаємо програму шейдера до менеджера
        shaderManager.loadShader("basic_shader", "vertex_shader.glsl", "fragment_shader.glsl");
        System.out.println("  Програма шейдера додана до менеджера");
        
        // Створюємо ще одну програму шейдера
        shaderManager.loadShader("lighting_shader", "lighting_vertex.glsl", "lighting_fragment.glsl");
        System.out.println("  Додано програму шейдера для освітлення");
        
        // Отримуємо кількість програм шейдерів
        System.out.println("  Загальна кількість програм шейдерів: " + shaderManager.getShaderCount());
    }
    
    /**
     * Демонстрація спеціалізованих рендерерів
     */
    private static void demonstrateSpecializedRenderers() {
        System.out.println("5. Спеціалізовані рендерери:");
        
        // Створюємо рушій рендерингу
        RenderEngine renderEngine = new RenderEngine();
        renderEngine.initialize();
        System.out.println("  Створено та ініціалізовано рушій рендерингу");
        
        // Отримуємо підсистеми
        Camera camera = renderEngine.getCamera();
        LightingSystem lightingSystem = renderEngine.getLightingSystem();
        ShaderManager shaderManager = renderEngine.getShaderManager();
        TextureManager textureManager = renderEngine.getTextureManager();
        
        System.out.println("  Отримано підсистеми рендерингу");
        System.out.println("    Камера: " + camera.getClass().getSimpleName());
        System.out.println("    Система освітлення: " + lightingSystem.getClass().getSimpleName());
        System.out.println("    Менеджер шейдерів: " + shaderManager.getClass().getSimpleName());
        System.out.println("    Менеджер текстур: " + textureManager.getClass().getSimpleName());
    }
}