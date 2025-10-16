package com.sparky.minecraft.audio;

import com.sparky.minecraft.math.Vector3D;

/**
 * Демонстрація аудіо системи.
 *
 * @author Андрій Будильников
 */
public class AudioDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрація аудіо системи ===\n");
        
        // Демонстрація менеджера аудіо
        demonstrateAudioManager();
        
        System.out.println();
        
        // Демонстрація джерел звуку
        demonstrateAudioSources();
        
        System.out.println();
        
        // Демонстрація звуків
        demonstrateAudioSounds();
        
        System.out.println();
        
        // Демонстрація 3D аудіо
        demonstrate3DAudio();
        
        System.out.println("\n=== Демонстрація завершена ===");
    }
    
    /**
     * Демонстрація менеджера аудіо
     */
    private static void demonstrateAudioManager() {
        System.out.println("1. Менеджер аудіо:");
        
        // Створюємо менеджер аудіо
        AudioManager audioManager = new AudioManager();
        
        // Встановлюємо налаштування
        audioManager.setMasterVolume(0.8);
        
        System.out.println("  Налаштування аудіо:");
        System.out.println("    Загальна гучність: " + audioManager.getMasterVolume());
        
        // Завантажуємо звуки
        audioManager.loadSound("cave_ambience", "ambient/cave_ambience.wav");
        audioManager.loadSound("game_music", "music/game_music.mp3");
        
        System.out.println("  Звуки завантажено");
    }
    
    /**
     * Демонстрація джерел звуку
     */
    private static void demonstrateAudioSources() {
        System.out.println("2. Джерела звуку:");
        
        // Створюємо звук
        AudioSound sound = new AudioSound("test_sound", "sounds/test.wav");
        
        // Створюємо джерело звуку
        AudioSource audioSource = new AudioSource(sound);
        
        // Встановлюємо параметри джерела
        audioSource.setVolume(0.8);
        audioSource.setLooping(false);
        
        System.out.println("  Параметри джерела звуку:");
        System.out.println("    Гучність: " + audioSource.getVolume());
        System.out.println("    Зациклення: " + audioSource.isLooping());
        System.out.println("    Відтворюється: " + audioSource.isPlaying());
        
        // Встановлюємо позицію джерела
        Vector3D position = new Vector3D(10.0, 5.0, 15.0);
        audioSource.setPosition(position);
        System.out.println("  Позиція джерела: " + audioSource.getPosition());
        
        // Встановлюємо панорамування
        audioSource.setPan(-0.5);
        System.out.println("  Панорамування: " + audioSource.getPan());
    }
    
    /**
     * Демонстрація звуків
     */
    private static void demonstrateAudioSounds() {
        System.out.println("3. Звуки:");
        
        // Створюємо звук
        AudioSound sound = new AudioSound("cave_ambience", "ambient/cave_ambience.wav");
        
        System.out.println("  Створено звук: " + sound.getName());
        System.out.println("  Шлях до файлу: " + sound.getFilePath());
        System.out.println("  Довжина звуку: " + sound.getDuration() + " секунд");
        System.out.println("  Частота дискретизації: " + sound.getSampleRate() + " Гц");
        System.out.println("  Кількість каналів: " + sound.getChannels());
        
        // Створюємо ще один звук
        AudioSound music = new AudioSound("game_music", "music/game_music.mp3");
        
        System.out.println("  Створено музику: " + music.getName());
    }
    
    /**
     * Демонстрація 3D аудіо
     */
    private static void demonstrate3DAudio() {
        System.out.println("4. 3D аудіо:");
        
        // Створюємо менеджер аудіо
        AudioManager audioManager = new AudioManager();
        
        // Встановлюємо позицію слухача
        Vector3D listenerPosition = new Vector3D(0.0, 0.0, 0.0);
        audioManager.setListenerPosition(listenerPosition);
        System.out.println("  Позиція слухача: " + listenerPosition);
        
        // Встановлюємо орієнтацію слухача
        Vector3D listenerOrientation = new Vector3D(0.0, 0.0, -1.0);
        audioManager.setListenerOrientation(listenerOrientation);
        System.out.println("  Орієнтація слухача встановлена");
        
        // Створюємо звук
        AudioSound sound = new AudioSound("creeper_hiss", "entity/creeper/hiss.wav");
        
        // Створюємо джерело звуку в 3D просторі
        AudioSource source = new AudioSource(sound);
        Vector3D sourcePosition = new Vector3D(5.0, 0.0, 10.0);
        source.setPosition(sourcePosition);
        source.setVolume(1.0);
        
        System.out.println("  Джерело звуку створено в позиції: " + source.getPosition());
        
        // Додаємо джерело до менеджера
        audioManager.playSound("creeper_hiss");
        
        // Симулюємо відтворення звуку
        System.out.println("  Симуляція відтворення 3D звуку...");
        System.out.println("  Звук буде чутно зліва та трохи позаду слухача");
        
        // Оновлюємо аудіо систему
        audioManager.update();
        System.out.println("  Аудіо система оновлена");
    }
}