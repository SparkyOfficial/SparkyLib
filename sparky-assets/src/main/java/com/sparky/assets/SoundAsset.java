package com.sparky.assets;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sparky.core.SparkyLogger;

/**
 * Ассет звуку.
 *
 * @author Андрій Будильников
 */
public class SoundAsset implements Asset {
    private final SparkyLogger logger = SparkyLogger.getLogger(SoundAsset.class);
    
    private final String id;
    private final String path;
    private boolean loaded = false;
    private Clip soundData;
    
    // Параметри аудіо
    private float volume = 1.0f; // 0.0 to 1.0
    private float pan = 0.0f;    // -1.0 (ліво) to 1.0 (право)
    private float pitch = 1.0f;  // 0.5 to 2.0 (швидкість відтворення)
    private boolean loop = false;
    
    public SoundAsset(String id, String path) {
        this.id = id;
        this.path = path;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public AssetType getType() {
        return AssetType.SOUND;
    }
    
    @Override
    public void load() {
        try {
            File file = new File(path);
            if (file.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                soundData = AudioSystem.getClip();
                soundData.open(audioInputStream);
                loaded = true;
                logger.info("Loaded sound: " + id + " from " + path);
                updateAudioControls();
            } else {
                logger.warn("Sound file not found: " + path);
            }
        } catch (UnsupportedAudioFileException | IOException | javax.sound.sampled.LineUnavailableException e) {
            logger.error("Failed to load sound: " + id + " from " + path, e);
        }
    }
    
    @Override
    public void unload() {
        if (soundData != null) {
            soundData.close();
            soundData = null;
        }
        loaded = false;
        logger.info("Unloaded sound: " + id);
    }
    
    @Override
    public boolean isLoaded() {
        return loaded;
    }
    
    /**
     * Відтворює звук.
     */
    public void play() {
        if (loaded && soundData != null) {
            if (soundData.isRunning()) {
                soundData.stop();
            }
            soundData.setFramePosition(0);
            updateAudioControls();
            soundData.start();
            if (loop) {
                soundData.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }
    
    /**
     * Зупиняє відтворення звуку.
     */
    public void stop() {
        if (loaded && soundData != null && soundData.isRunning()) {
            soundData.stop();
        }
    }
    
    /**
     * Пауза відтворення звуку.
     */
    public void pause() {
        if (loaded && soundData != null && soundData.isRunning()) {
            soundData.stop();
        }
    }
    
    /**
     * Відновлює відтворення звуку з паузи.
     */
    public void resume() {
        if (loaded && soundData != null && !soundData.isRunning()) {
            soundData.start();
        }
    }
    
    /**
     * Оновлює аудіо контроли (гучність, панорамування тощо).
     */
    private void updateAudioControls() {
        if (soundData == null) return;
        
        // Налаштовуємо гучність
        try {
            FloatControl gainControl = (FloatControl) soundData.getControl(FloatControl.Type.MASTER_GAIN);
            // Конвертуємо лінійну гучність в децибели
            float dB = (volume == 0.0f) ? -80.0f : (float) (20 * Math.log10(volume));
            gainControl.setValue(dB);
        } catch (Exception e) {
            // Не всі системи підтримують MASTER_GAIN
            try {
                FloatControl volumeControl = (FloatControl) soundData.getControl(FloatControl.Type.VOLUME);
                float min = volumeControl.getMinimum();
                float max = volumeControl.getMaximum();
                float range = max - min;
                volumeControl.setValue(min + (range * volume));
            } catch (Exception ex) {
                // Ігноруємо, якщо не можемо налаштувати гучність
            }
        }
        
        // Налаштовуємо панорамування
        try {
            FloatControl panControl = (FloatControl) soundData.getControl(FloatControl.Type.PAN);
            panControl.setValue(pan);
        } catch (Exception e) {
            // Не всі системи підтримують панорамування
        }
    }
    
    /**
     * Встановлює гучність звуку.
     *
     * @param volume гучність (0.0 до 1.0)
     */
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
        if (loaded) {
            updateAudioControls();
        }
    }
    
    /**
     * Отримує поточну гучність звуку.
     *
     * @return гучність (0.0 до 1.0)
     */
    public float getVolume() {
        return volume;
    }
    
    /**
     * Встановлює панорамування звуку.
     *
     * @param pan панорамування (-1.0 ліво, 1.0 право, 0.0 центр)
     */
    public void setPan(float pan) {
        this.pan = Math.max(-1.0f, Math.min(1.0f, pan));
        if (loaded) {
            updateAudioControls();
        }
    }
    
    /**
     * Отримує поточне панорамування звуку.
     *
     * @return панорамування (-1.0 до 1.0)
     */
    public float getPan() {
        return pan;
    }
    
    /**
     * Встановлює швидкість відтворення (pitch).
     *
     * @param pitch швидкість (0.5 до 2.0)
     */
    public void setPitch(float pitch) {
        this.pitch = Math.max(0.5f, Math.min(2.0f, pitch));
        // Примітка: Зміна pitch в Java Sound API складна, тому ми зберігаємо значення для можливого використання в майбутньому
    }
    
    /**
     * Отримує поточну швидкість відтворення.
     *
     * @return швидкість відтворення
     */
    public float getPitch() {
        return pitch;
    }
    
    /**
     * Встановлює режим повторення.
     *
     * @param loop true для повторення, false для одноразового відтворення
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    
    /**
     * Перевіряє, чи ввімкнено повторення.
     *
     * @return true, якщо повторення ввімкнено
     */
    public boolean isLoop() {
        return loop;
    }
    
    /**
     * Отримує дані звуку (платформо-специфічні).
     */
    public Clip getSoundData() {
        return soundData;
    }
    
    /**
     * Перевіряє, чи звук зараз відтворюється.
     *
     * @return true, якщо звук відтворюється
     */
    public boolean isPlaying() {
        return loaded && soundData != null && soundData.isRunning();
    }
}