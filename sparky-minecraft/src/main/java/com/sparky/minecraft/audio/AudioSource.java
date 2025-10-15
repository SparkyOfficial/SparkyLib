package com.sparky.minecraft.audio;

import com.sparky.minecraft.math.Vector3D;

/**
 * Джерело звуку для відтворення.
 *
 * @author Андрій Будильников
 */
public class AudioSource {
    private AudioSound sound;
    private Vector3D position;
    private double volume;
    private double pan; // -1 ліворуч, 1 праворуч
    private boolean looping;
    private boolean playing;
    private double playbackPosition; // позиція відтворення у секундах
    
    public AudioSource(AudioSound sound) {
        this.sound = sound;
        this.position = null; // null означає глобальний звук
        this.volume = 1.0;
        this.pan = 0.0;
        this.looping = false;
        this.playing = true;
        this.playbackPosition = 0.0;
    }
    
    // починає відтворення
    public void play() {
        this.playing = true;
        System.out.println("Playing sound: " + sound.getName());
    }
    
    // зупиняє відтворення
    public void stop() {
        this.playing = false;
        this.playbackPosition = 0.0;
        System.out.println("Stopped sound: " + sound.getName());
    }
    
    // пауза відтворення
    public void pause() {
        this.playing = false;
        System.out.println("Paused sound: " + sound.getName());
    }
    
    // продовжує відтворення
    public void resume() {
        this.playing = true;
        System.out.println("Resumed sound: " + sound.getName());
    }
    
    // оновлює джерело звуку
    public void update(double deltaTime) {
        if (playing) {
            playbackPosition += deltaTime;
            
            // перевіряємо, чи закінчився звук
            if (playbackPosition >= sound.getDuration()) {
                if (looping) {
                    // повторюємо
                    playbackPosition = 0.0;
                } else {
                    // зупиняємо
                    stop();
                }
            }
        }
    }
    
    // встановлює позицію
    public void setPosition(Vector3D position) {
        this.position = position != null ? new Vector3D(position) : null;
    }
    
    // отримує позицію
    public Vector3D getPosition() {
        return position != null ? new Vector3D(position) : null;
    }
    
    // встановлює гучність
    public void setVolume(double volume) {
        this.volume = Math.max(0.0, Math.min(1.0, volume));
    }
    
    // отримує гучність
    public double getVolume() {
        return volume;
    }
    
    // встановлює панорамування
    public void setPan(double pan) {
        this.pan = Math.max(-1.0, Math.min(1.0, pan));
    }
    
    // отримує панорамування
    public double getPan() {
        return pan;
    }
    
    // встановлює режим повторення
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
    
    // перевіряє, чи в режимі повторення
    public boolean isLooping() {
        return looping;
    }
    
    // перевіряє, чи відтворюється
    public boolean isPlaying() {
        return playing;
    }
    
    // отримує позицію відтворення
    public double getPlaybackPosition() {
        return playbackPosition;
    }
    
    // отримує звук
    public AudioSound getSound() {
        return sound;
    }
    
    @Override
    public String toString() {
        return "AudioSource{" +
                "sound=" + sound.getName() +
                ", position=" + position +
                ", volume=" + volume +
                ", playing=" + playing +
                '}';
    }
}