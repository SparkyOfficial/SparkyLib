package com.sparky.minecraft;

import com.sparky.minecraft.audio.*;
import com.sparky.minecraft.redstone.*;
import com.sparky.minecraft.rendering.*;
import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.particles.*;
import com.sparky.minecraft.weather.WeatherSystem;

/**
 * Комплексна демонстрація всіх систем бібліотеки Sparky Minecraft.
 *
 * @author Андрій Будильников
 */
public class CompleteMinecraftDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Комплексна демонстрація Sparky Minecraft бібліотеки ===\n");
        
        // Демонстрація аудіо системи
        demonstrateAudioSystem();
        
        System.out.println();
        
        // Демонстрація редстоун системи
        demonstrateRedstoneSystem();
        
        System.out.println();
        
        // Демонстрація рендеринг системи
        demonstrateRenderingSystem();
        
        System.out.println();
        
        // Демонстрація системи частинок
        demonstrateParticleSystem();
        
        System.out.println();
        
        // Демонстрація системи погоди
        demonstrateWeatherSystem();
        
        System.out.println("\n=== Демонстрація завершена ===");
    }
    
    /**
     * Демонстрація аудіо системи
     */
    private static void demonstrateAudioSystem() {
        System.out.println("1. Аудіо система:");
        
        // Створюємо менеджер аудіо
        AudioManager audioManager = new AudioManager();
        
        // Встановлюємо налаштування
        audioManager.setMasterVolume(0.8);
        System.out.println("  Налаштування аудіо:");
        System.out.println("    Загальна гучність: " + audioManager.getMasterVolume());
        
        // Завантажуємо звуки
        audioManager.loadSound("cave_ambience", "ambient/cave_ambience.wav");
        audioManager.loadSound("game_music", "music/game_music.mp3");
        audioManager.loadSound("creeper_hiss", "entity/creeper/hiss.wav");
        System.out.println("  Звуки завантажено");
        
        // Відтворюємо звуки
        audioManager.playSound("cave_ambience");
        AudioSource musicSource = audioManager.playLoopingSound("game_music");
        System.out.println("  Музика відтворюється в циклі");
        
        // Створюємо 3D джерело звуку
        AudioSound creeperSound = new AudioSound("creeper_hiss", "entity/creeper/hiss.wav");
        AudioSource creeperSource = new AudioSource(creeperSound);
        Vector3D creeperPosition = new Vector3D(10.0, 5.0, 15.0);
        creeperSource.setPosition(creeperPosition);
        creeperSource.setVolume(1.0);
        System.out.println("  Створено 3D джерело звуку в позиції: " + creeperPosition);
        
        // Встановлюємо позицію слухача
        Vector3D listenerPosition = new Vector3D(0.0, 0.0, 0.0);
        audioManager.setListenerPosition(listenerPosition);
        System.out.println("  Позиція слухача: " + listenerPosition);
        
        // Оновлюємо аудіо систему
        audioManager.update();
        System.out.println("  Аудіо система оновлена");
    }
    
    /**
     * Демонстрація редстоун системи
     */
    private static void demonstrateRedstoneSystem() {
        System.out.println("2. Редстоун система:");
        
        // Створюємо редстоун систему
        RedstoneSystem redstoneSystem = new RedstoneSystem();
        
        // Створюємо просту схему: важіль -> провід -> лампа
        Vector3D leverPos = new Vector3D(0, 64, 0);
        Vector3D wirePos = new Vector3D(1, 64, 0);
        Vector3D lampPos = new Vector3D(2, 64, 0);
        
        RedstoneSystem.RedstoneComponent lever = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.LEVER, leverPos);
        RedstoneSystem.RedstoneComponent wire = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE, wirePos);
        RedstoneSystem.RedstoneComponent lamp = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_LAMP, lampPos);
        
        redstoneSystem.addComponent(lever);
        redstoneSystem.addComponent(wire);
        redstoneSystem.addComponent(lamp);
        
        System.out.println("  Створено просту редстоун схему:");
        System.out.println("    Важіль -> Провід -> Лампа");
        
        // Активуємо важіль
        lever.setPowerLevel(15);
        redstoneSystem.updateRedstoneSystem();
        
        System.out.println("  Важіль активовано з потужністю 15");
        System.out.println("  Лампа отримала потужність: " + lamp.getPowerLevel());
        
        // Деактивуємо важіль
        lever.setPowerLevel(0);
        redstoneSystem.updateRedstoneSystem();
        
        System.out.println("  Важіль деактивовано");
        System.out.println("  Лампа отримала потужність: " + lamp.getPowerLevel());
    }
    
    /**
     * Демонстрація рендеринг системи
     */
    private static void demonstrateRenderingSystem() {
        System.out.println("3. Рендеринг система:");
        
        // Створюємо рушій рендерингу
        RenderEngine renderEngine = new RenderEngine();
        renderEngine.initialize();
        System.out.println("  Рушій рендерингу ініціалізовано");
        
        // Налаштовуємо камеру
        Camera camera = renderEngine.getCamera();
        camera.setPosition(new Vector3D(0, 10, 20));
        camera.setRotation(new Vector3D(0, 0, 0));
        camera.setFov(70.0);
        System.out.println("  Камера налаштована:");
        System.out.println("    Позиція: " + camera.getPosition());
        System.out.println("    FOV: " + camera.getFov());
        
        // Налаштовуємо освітлення
        LightingSystem lightingSystem = renderEngine.getLightingSystem();
        System.out.println("  Система освітлення ініціалізована");
        
        // Створюємо точкове світло
        PointLight pointLight = new PointLight(
            new Vector3D(1.0f, 0.8f, 0.6f), // Тепле біле світло
            new Vector3D(5, 15, 5),
            1.0
        );
        pointLight.setAttenuation(1.0, 0.09, 0.032);
        lightingSystem.addLight(pointLight);
        System.out.println("  Додано точкове світло");
        
        // Створюємо направлене світло (сонце)
        DirectionalLight sunLight = new DirectionalLight(
            new Vector3D(1.0f, 0.95f, 0.8f), // Світло сонця
            new Vector3D(-0.5f, -1.0f, -0.3f),
            0.8
        );
        lightingSystem.addLight(sunLight);
        System.out.println("  Додано направлене світло (сонце)");
        
        // Налаштовуємо текстури
        TextureManager textureManager = renderEngine.getTextureManager();
        textureManager.loadTexture("grass", "textures/blocks/grass.png");
        textureManager.loadTexture("stone", "textures/blocks/stone.png");
        System.out.println("  Текстури завантажено: " + textureManager.getTextureCount());
        
        // Налаштовуємо шейдери
        ShaderManager shaderManager = renderEngine.getShaderManager();
        shaderManager.loadShader("basic", "shaders/basic_vertex.glsl", "shaders/basic_fragment.glsl");
        shaderManager.loadShader("lighting", "shaders/lighting_vertex.glsl", "shaders/lighting_fragment.glsl");
        System.out.println("  Шейдери завантажено: " + shaderManager.getShaderCount());
    }
    
    /**
     * Демонстрація системи частинок
     */
    private static void demonstrateParticleSystem() {
        System.out.println("4. Система частинок:");
        
        // Створюємо систему частинок
        ParticleSystem particleSystem = new ParticleSystem();
        particleSystem.start();
        System.out.println("  Система частинок створена та запущена");
        
        // Створюємо ефект вибуху
        ParticleEffect3D explosionEffect = ParticleEffect3D.createExplosion(
            "explosion", 
            new Vector3D(0, 64, 0), // Позиція
            50 // Кількість частинок
        );
        particleSystem.addEffect(explosionEffect);
        System.out.println("  Створено ефект вибуху з 50 частинками");
        
        // Створюємо ефект дощу
        ParticleEffect3D rainEffect = ParticleEffect3D.createRain(
            "rain",
            new Vector3D(0, 100, 0), // Позиція
            new Vector3D(50, 0, 50), // Розмір області
            100 // Кількість частинок
        );
        particleSystem.addEffect(rainEffect);
        System.out.println("  Створено ефект дощу з 100 частинками");
        
        // Створюємо фізику частинок
        ParticlePhysics particlePhysics = new ParticlePhysics(new Vector3D(0, 64, 0));
        System.out.println("  Фізика частинок створена з позицією: " + particlePhysics.getPosition());
        
        // Оновлюємо систему частинок
        particleSystem.update(0.016); // 60 FPS
        System.out.println("  Система частинок оновлена");
    }
    
    /**
     * Демонстрація системи погоди
     */
    private static void demonstrateWeatherSystem() {
        System.out.println("5. Система погоди:");
        
        // Створюємо систему погоди
        WeatherSystem weatherSystem = new WeatherSystem();
        System.out.println("  Система погоди створена");
        
        // Встановлюємо ясну погоду
        weatherSystem.setCurrentWeather(WeatherSystem.WeatherType.CLEAR);
        System.out.println("  Встановлено ясну погоду");
        
        // Встановлюємо дощ
        weatherSystem.setTargetWeather(WeatherSystem.WeatherType.RAIN);
        weatherSystem.changeWeather(WeatherSystem.WeatherType.RAIN, 1.0f);
        System.out.println("  Встановлено дощ");
        
        // Оновлюємо систему погоди
        weatherSystem.update(1.0f, new Vector3D(0, 0, 0));
        System.out.println("  Система погоди оновлена");
    }
}