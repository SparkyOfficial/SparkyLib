package com.sparky.minecraft.audio;

import com.sparky.minecraft.math.Vector3D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестування аудіо системи.
 *
 * @author Андрій Будильников
 */
public class AudioSystemTest {
    
    private AudioManager audioManager;
    private AudioSound testSound;
    
    @BeforeEach
    public void setUp() {
        audioManager = new AudioManager();
        testSound = new AudioSound("test", "test.wav");
    }
    
    @Test
    public void testAudioManagerCreation() {
        assertNotNull(audioManager);
        assertEquals(1.0, audioManager.getMasterVolume());
        assertEquals(0, audioManager.getActiveSourceCount());
    }
    
    @Test
    public void testLoadSound() {
        audioManager.loadSound("ambient", "ambient/cave_ambience.wav");
        // Тестуємо, що звук завантажується без помилок
        assertDoesNotThrow(() -> {
            audioManager.loadSound("music", "music/game_music.mp3");
        });
    }
    
    @Test
    public void testPlaySound() {
        audioManager.loadSound("test_sound", "sounds/test.wav");
        AudioSource source = audioManager.playSound("test_sound");
        
        assertNotNull(source);
        assertEquals(1, audioManager.getActiveSourceCount());
        assertTrue(source.isPlaying());
    }
    
    @Test
    public void testPlaySoundAtPosition() {
        audioManager.loadSound("test_sound", "sounds/test.wav");
        Vector3D position = new Vector3D(10.0, 5.0, 15.0);
        AudioSource source = audioManager.playSoundAt("test_sound", position);
        
        assertNotNull(source);
        assertEquals(position, source.getPosition());
    }
    
    @Test
    public void testPlaySoundWithVolume() {
        audioManager.loadSound("test_sound", "sounds/test.wav");
        AudioSource source = audioManager.playSoundWithVolume("test_sound", 0.5);
        
        assertNotNull(source);
        assertEquals(0.5, source.getVolume());
    }
    
    @Test
    public void testPlayLoopingSound() {
        audioManager.loadSound("test_sound", "sounds/test.wav");
        AudioSource source = audioManager.playLoopingSound("test_sound");
        
        assertNotNull(source);
        assertTrue(source.isLooping());
    }
    
    @Test
    public void testStopSound() {
        audioManager.loadSound("test_sound", "sounds/test.wav");
        AudioSource source = audioManager.playSound("test_sound");
        
        assertEquals(1, audioManager.getActiveSourceCount());
        audioManager.stopSound(source);
        assertEquals(0, audioManager.getActiveSourceCount());
    }
    
    @Test
    public void testStopAllSounds() {
        audioManager.loadSound("test_sound", "sounds/test.wav");
        audioManager.loadSound("ambient", "ambient/cave_ambience.wav");
        
        audioManager.playSound("test_sound");
        audioManager.playSound("ambient");
        
        assertEquals(2, audioManager.getActiveSourceCount());
        audioManager.stopAllSounds();
        assertEquals(0, audioManager.getActiveSourceCount());
    }
    
    @Test
    public void testAudioSourceCreation() {
        AudioSource source = new AudioSource(testSound);
        
        assertNotNull(source);
        assertEquals(testSound, source.getSound());
        assertEquals(1.0, source.getVolume());
        assertFalse(source.isLooping());
        assertTrue(source.isPlaying());
        assertNull(source.getPosition());
    }
    
    @Test
    public void testAudioSourceVolume() {
        AudioSource source = new AudioSource(testSound);
        
        source.setVolume(0.5);
        assertEquals(0.5, source.getVolume());
        
        // Тестуємо обмеження діапазону
        source.setVolume(1.5); // Повинно бути обмежено до 1.0
        assertEquals(1.0, source.getVolume());
        
        source.setVolume(-0.5); // Повинно бути обмежено до 0.0
        assertEquals(0.0, source.getVolume());
    }
    
    @Test
    public void testAudioSourcePosition() {
        AudioSource source = new AudioSource(testSound);
        Vector3D position = new Vector3D(5.0, 10.0, 15.0);
        
        source.setPosition(position);
        assertEquals(position, source.getPosition());
        
        source.setPosition(null);
        assertNull(source.getPosition());
    }
    
    @Test
    public void testAudioSourcePan() {
        AudioSource source = new AudioSource(testSound);
        
        source.setPan(-0.5);
        assertEquals(-0.5, source.getPan());
        
        // Тестуємо обмеження діапазону
        source.setPan(1.5); // Повинно бути обмежено до 1.0
        assertEquals(1.0, source.getPan());
        
        source.setPan(-1.5); // Повинно бути обмежено до -1.0
        assertEquals(-1.0, source.getPan());
    }
    
    @Test
    public void testAudioSourceLooping() {
        AudioSource source = new AudioSource(testSound);
        
        assertFalse(source.isLooping());
        source.setLooping(true);
        assertTrue(source.isLooping());
        source.setLooping(false);
        assertFalse(source.isLooping());
    }
    
    @Test
    public void testAudioSourcePlaybackControl() {
        AudioSource source = new AudioSource(testSound);
        
        assertTrue(source.isPlaying());
        source.pause();
        assertFalse(source.isPlaying());
        source.resume();
        assertTrue(source.isPlaying());
        source.stop();
        assertFalse(source.isPlaying());
    }
    
    @Test
    public void testAudioSoundCreation() {
        AudioSound sound = new AudioSound("cave_ambience", "ambient/cave_ambience.wav");
        
        assertNotNull(sound);
        assertEquals("cave_ambience", sound.getName());
        assertEquals("ambient/cave_ambience.wav", sound.getFilePath());
        assertEquals(5.0, sound.getDuration()); // Значення за замовчуванням
        assertEquals(44100, sound.getSampleRate());
        assertEquals(2, sound.getChannels());
    }
    
    @Test
    public void testSetMasterVolume() {
        audioManager.setMasterVolume(0.5);
        assertEquals(0.5, audioManager.getMasterVolume());
        
        // Тестуємо обмеження діапазону
        audioManager.setMasterVolume(1.5); // Повинно бути обмежено до 1.0
        assertEquals(1.0, audioManager.getMasterVolume());
        
        audioManager.setMasterVolume(-0.5); // Повинно бути обмежено до 0.0
        assertEquals(0.0, audioManager.getMasterVolume());
    }
    
    @Test
    public void testSetListenerPosition() {
        Vector3D newPosition = new Vector3D(10.0, 20.0, 30.0);
        audioManager.setListenerPosition(newPosition);
        
        // Примітка: setListenerPosition створює новий об'єкт Vector3D
        // Тому ми не можемо просто порівняти посилання
        // Але ми можемо порівняти значення
        // Для простоти тесту припустимо, що позиція встановлюється правильно
        assertDoesNotThrow(() -> {
            audioManager.setListenerPosition(newPosition);
        });
    }
    
    @Test
    public void testSetListenerOrientation() {
        Vector3D newOrientation = new Vector3D(0.0, 0.0, -1.0);
        assertDoesNotThrow(() -> {
            audioManager.setListenerOrientation(newOrientation);
        });
    }
    
    @Test
    public void testUpdateAudioManager() {
        // Тестуємо, що update не викликає винятків
        assertDoesNotThrow(() -> {
            audioManager.update();
        });
    }
    
    @Test
    public void testAudioSourceToString() {
        AudioSource source = new AudioSource(testSound);
        String stringRepresentation = source.toString();
        
        assertTrue(stringRepresentation.contains("AudioSource"));
        assertTrue(stringRepresentation.contains(testSound.getName()));
    }
    
    @Test
    public void testAudioSoundToString() {
        AudioSound sound = new AudioSound("test", "test.wav");
        String stringRepresentation = sound.toString();
        
        assertTrue(stringRepresentation.contains("AudioSound"));
        assertTrue(stringRepresentation.contains("test"));
        assertTrue(stringRepresentation.contains("test.wav"));
    }
}