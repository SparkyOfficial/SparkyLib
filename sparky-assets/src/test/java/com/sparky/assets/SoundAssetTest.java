package com.sparky.assets;

import static java.lang.System.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Тест для ассету звуку.
 *
 * @author Андрій Будильников
 */
class SoundAssetTest {
    
    @Test
    void testSoundAssetCreation() {
        SoundAsset soundAsset = new SoundAsset("test_sound", "nonexistent.wav");
        
        assertEquals("test_sound", soundAsset.getId());
        assertEquals(AssetType.SOUND, soundAsset.getType());
        assertFalse(soundAsset.isLoaded());
        assertFalse(soundAsset.isPlaying());
        
        out.println("Sound asset creation test passed!");
    }
    
    @Test
    void testVolumeControl() {
        SoundAsset soundAsset = new SoundAsset("test_sound", "nonexistent.wav");
        
        // Тестуємо встановлення гучності
        soundAsset.setVolume(0.5f);
        assertEquals(0.5f, soundAsset.getVolume(), 0.001f);
        
        // Тестуємо обмеження гучності
        soundAsset.setVolume(1.5f); // Повинно бути обмежено до 1.0
        assertEquals(1.0f, soundAsset.getVolume(), 0.001f);
        
        soundAsset.setVolume(-0.5f); // Повинно бути обмежено до 0.0
        assertEquals(0.0f, soundAsset.getVolume(), 0.001f);
        
        out.println("Sound asset volume control test passed!");
    }
    
    @Test
    void testPanControl() {
        SoundAsset soundAsset = new SoundAsset("test_sound", "nonexistent.wav");
        
        // Тестуємо встановлення панорамування
        soundAsset.setPan(0.5f);
        assertEquals(0.5f, soundAsset.getPan(), 0.001f);
        
        // Тестуємо обмеження панорамування
        soundAsset.setPan(1.5f); // Повинно бути обмежено до 1.0
        assertEquals(1.0f, soundAsset.getPan(), 0.001f);
        
        soundAsset.setPan(-1.5f); // Повинно бути обмежено до -1.0
        assertEquals(-1.0f, soundAsset.getPan(), 0.001f);
        
        out.println("Sound asset pan control test passed!");
    }
    
    @Test
    void testPitchControl() {
        SoundAsset soundAsset = new SoundAsset("test_sound", "nonexistent.wav");
        
        // Тестуємо встановлення pitch
        soundAsset.setPitch(1.5f);
        assertEquals(1.5f, soundAsset.getPitch(), 0.001f);
        
        // Тестуємо обмеження pitch
        soundAsset.setPitch(2.5f); // Повинно бути обмежено до 2.0
        assertEquals(2.0f, soundAsset.getPitch(), 0.001f);
        
        soundAsset.setPitch(0.3f); // Повинно бути обмежено до 0.5
        assertEquals(0.5f, soundAsset.getPitch(), 0.001f);
        
        out.println("Sound asset pitch control test passed!");
    }
    
    @Test
    void testLoopControl() {
        SoundAsset soundAsset = new SoundAsset("test_sound", "nonexistent.wav");
        
        // Тестуємо встановлення режиму повторення
        soundAsset.setLoop(true);
        assertTrue(soundAsset.isLoop());
        
        soundAsset.setLoop(false);
        assertFalse(soundAsset.isLoop());
        
        out.println("Sound asset loop control test passed!");
    }
}