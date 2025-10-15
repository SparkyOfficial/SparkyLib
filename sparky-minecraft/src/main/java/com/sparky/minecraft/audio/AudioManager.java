package com.sparky.minecraft.audio;

import com.sparky.minecraft.math.Vector3D;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Менеджер аудіо для тривимірного звуку в Minecraft.
 *
 * @author Андрій Будильников
 */
public class AudioManager {
    private Map<String, AudioSound> loadedSounds;
    private List<AudioSource> activeSources;
    private Vector3D listenerPosition;
    private Vector3D listenerOrientation;
    private double masterVolume;
    
    public AudioManager() {
        this.loadedSounds = new HashMap<>();
        this.activeSources = new ArrayList<>();
        this.listenerPosition = new Vector3D(0, 0, 0);
        this.listenerOrientation = new Vector3D(0, 0, 1);
        this.masterVolume = 1.0;
    }
    
    // завантажує звук з файлу
    public void loadSound(String name, String filePath) {
        // тут була б логіка завантаження звуку
        // для прикладу просто створюємо порожній звук
        AudioSound sound = new AudioSound(name, filePath);
        loadedSounds.put(name, sound);
        System.out.println("Loaded sound: " + name);
    }
    
    // відтворює звук
    public AudioSource playSound(String soundName) {
        AudioSound sound = loadedSounds.get(soundName);
        if (sound == null) {
            System.err.println("Sound not found: " + soundName);
            return null;
        }
        
        AudioSource source = new AudioSource(sound);
        source.setPosition(listenerPosition); // за замовчуванням біля слухача
        source.setVolume(masterVolume);
        activeSources.add(source);
        
        // тут була б логіка відтворення звуку
        System.out.println("Playing sound: " + soundName);
        return source;
    }
    
    // відтворює звук у певній позиції
    public AudioSource playSoundAt(String soundName, Vector3D position) {
        AudioSource source = playSound(soundName);
        if (source != null) {
            source.setPosition(position);
            // тут була б логіка 3D звуку
        }
        return source;
    }
    
    // відтворює звук з певною гучністю
    public AudioSource playSoundWithVolume(String soundName, double volume) {
        AudioSource source = playSound(soundName);
        if (source != null) {
            source.setVolume(volume * masterVolume);
        }
        return source;
    }
    
    // відтворює звук у циклі
    public AudioSource playLoopingSound(String soundName) {
        AudioSource source = playSound(soundName);
        if (source != null) {
            source.setLooping(true);
        }
        return source;
    }
    
    // зупиняє джерело звуку
    public void stopSound(AudioSource source) {
        if (source != null) {
            source.stop();
            activeSources.remove(source);
        }
    }
    
    // зупиняє всі звуки
    public void stopAllSounds() {
        for (AudioSource source : activeSources) {
            source.stop();
        }
        activeSources.clear();
    }
    
    // оновлює аудіо систему
    public void update() {
        // оновлюємо всі активні джерела
        for (int i = activeSources.size() - 1; i >= 0; i--) {
            AudioSource source = activeSources.get(i);
            if (!source.isPlaying()) {
                activeSources.remove(i);
            }
        }
        
        // оновлюємо 3D позиціонування
        update3DPositioning();
    }
    
    // оновлює 3D позиціонування звуку
    private void update3DPositioning() {
        for (AudioSource source : activeSources) {
            if (source.getPosition() != null) {
                // обчислюємо відстань до слухача
                double distance = listenerPosition.distance(source.getPosition());
                
                // обчислюємо гучність на основі відстані
                double volume = calculateVolumeByDistance(distance);
                source.setVolume(volume * masterVolume);
                
                // обчислюємо панорамування на основі кута
                double pan = calculatePanByAngle(source.getPosition());
                source.setPan(pan);
            }
        }
    }
    
    // обчислює гучність на основі відстані
    private double calculateVolumeByDistance(double distance) {
        // проста модель зменшення гучності з відстанню
        if (distance < 5) {
            return 1.0; // повна гучність
        } else if (distance < 50) {
            // лінійне зменшення
            return 1.0 - (distance - 5) / 45.0;
        } else {
            return 0.0; // занадто далеко
        }
    }
    
    // обчислює панорамування на основі кута
    private double calculatePanByAngle(Vector3D soundPosition) {
        // обчислюємо вектор від слухача до джерела
        Vector3D direction = soundPosition.subtract(listenerPosition);
        
        // проектуємо на горизонтальну площину
        Vector3D horizontalDirection = new Vector3D(direction.getX(), 0, direction.getZ());
        if (horizontalDirection.length() == 0) {
            return 0.0; // звук прямо перед слухачем
        }
        
        horizontalDirection = horizontalDirection.normalize();
        
        // обчислюємо кут між напрямком слухача та джерелом
        Vector3D listenerForward = new Vector3D(listenerOrientation.getX(), 0, listenerOrientation.getZ());
        if (listenerForward.length() == 0) {
            return 0.0;
        }
        
        listenerForward = listenerForward.normalize();
        
        // крос-добуток для визначення сторони
        double cross = listenerForward.getZ() * horizontalDirection.getX() - listenerForward.getX() * horizontalDirection.getZ();
        
        // точковий добуток для визначення кута
        double dot = listenerForward.dot(horizontalDirection);
        double angle = Math.acos(Math.max(-1.0, Math.min(1.0, dot)));
        
        // перетворюємо кут в панорамування (-1 ліворуч, 1 праворуч)
        double pan = Math.signum(cross) * (angle / Math.PI);
        return Math.max(-1.0, Math.min(1.0, pan));
    }
    
    // встановлює позицію слухача
    public void setListenerPosition(Vector3D position) {
        this.listenerPosition = new Vector3D(position);
    }
    
    // встановлює орієнтацію слухача
    public void setListenerOrientation(Vector3D orientation) {
        this.listenerOrientation = new Vector3D(orientation);
    }
    
    // встановлює загальну гучність
    public void setMasterVolume(double volume) {
        this.masterVolume = Math.max(0.0, Math.min(1.0, volume));
    }
    
    // отримує загальну гучність
    public double getMasterVolume() {
        return masterVolume;
    }
    
    // отримує кількість активних джерел
    public int getActiveSourceCount() {
        return activeSources.size();
    }
}