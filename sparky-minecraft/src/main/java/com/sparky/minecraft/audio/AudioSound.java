package com.sparky.minecraft.audio;

/**
 * Представляє завантажений звуковий файл.
 *
 * @author Андрій Будильников
 */
public class AudioSound {
    private String name;
    private String filePath;
    private double duration; // тривалість у секундах
    private int sampleRate;
    private int channels;
    
    public AudioSound(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
        this.duration = 0.0;
        this.sampleRate = 44100;
        this.channels = 2;
        
        // тут була б логіка завантаження та аналізу звуку
        loadSoundFile();
    }
    
    // завантажує звуковий файл
    private void loadSoundFile() {
        // тут була б реальна логіка завантаження
        // для прикладу просто встановлюємо фіктивні значення
        this.duration = 5.0; // 5 секунд
        System.out.println("Loading sound file: " + filePath);
    }
    
    // отримує назву звуку
    public String getName() {
        return name;
    }
    
    // отримує шлях до файлу
    public String getFilePath() {
        return filePath;
    }
    
    // отримує тривалість
    public double getDuration() {
        return duration;
    }
    
    // отримує частоту дискретизації
    public int getSampleRate() {
        return sampleRate;
    }
    
    // отримує кількість каналів
    public int getChannels() {
        return channels;
    }
    
    @Override
    public String toString() {
        return "AudioSound{" +
                "name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                ", duration=" + duration +
                "s}";
    }
}