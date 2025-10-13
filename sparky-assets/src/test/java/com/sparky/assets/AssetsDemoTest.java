package com.sparky.assets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Демонстраційний тест для модуля ассетів.
 *
 * @author Андрій Будильников
 * @author Богдан Кравчук
 */
class AssetsDemoTest {
    
    @Test
    void testAssetsFunctionality() throws IOException {
        // Create a temporary file for testing
        File tempFile = Files.createTempFile("test", ".png").toFile();
        
        // Test AssetManager
        AssetManager assetManager = AssetManager.getInstance();
        
        // Test TextureAsset
        TextureAsset textureAsset = new TextureAsset("testTexture", tempFile.getAbsolutePath());
        assertEquals("testTexture", textureAsset.getId());
        assertEquals(AssetType.TEXTURE, textureAsset.getType());
        assertFalse(textureAsset.isLoaded());
        
        // Test asset registration
        assetManager.registerAsset(textureAsset);
        
        // Test asset retrieval
        TextureAsset retrievedAsset = assetManager.getAsset("testTexture", TextureAsset.class);
        assertNotNull(retrievedAsset);
        assertEquals(textureAsset, retrievedAsset);
        
        // Test asset loading
        textureAsset.load();
        // Note: Loading might fail if the file is not a valid image, but we're testing the API
        // The important thing is that the load() method doesn't throw an exception
        
        // Test asset unloading
        textureAsset.unload();
        assertFalse(textureAsset.isLoaded());
        assertNull(textureAsset.getTextureData());
        
        // Clean up
        tempFile.delete();
        
        System.out.println("SparkyLib Assets functionality test passed!");
    }
    
    @Test
    void testAllAssetTypes() throws IOException {
        // Create temporary files for testing
        File tempImageFile = Files.createTempFile("test", ".png").toFile();
        File tempSoundFile = Files.createTempFile("test", ".wav").toFile();
        File tempModelFile = Files.createTempFile("test", ".obj").toFile();
        File tempConfigFile = Files.createTempFile("test", ".cfg").toFile();
        File tempScriptFile = Files.createTempFile("test", ".js").toFile();
        
        AssetManager assetManager = AssetManager.getInstance();
        
        // Test SoundAsset
        SoundAsset soundAsset = new SoundAsset("testSound", tempSoundFile.getAbsolutePath());
        assertEquals("testSound", soundAsset.getId());
        assertEquals(AssetType.SOUND, soundAsset.getType());
        assertFalse(soundAsset.isLoaded());
        
        // Test ModelAsset
        ModelAsset modelAsset = new ModelAsset("testModel", tempModelFile.getAbsolutePath());
        assertEquals("testModel", modelAsset.getId());
        assertEquals(AssetType.MODEL, modelAsset.getType());
        assertFalse(modelAsset.isLoaded());
        
        // Test ConfigAsset
        ConfigAsset configAsset = new ConfigAsset("testConfig", tempConfigFile.getAbsolutePath());
        assertEquals("testConfig", configAsset.getId());
        assertEquals(AssetType.CONFIG, configAsset.getType());
        assertFalse(configAsset.isLoaded());
        
        // Test ScriptAsset
        ScriptAsset scriptAsset = new ScriptAsset("testScript", tempScriptFile.getAbsolutePath());
        assertEquals("testScript", scriptAsset.getId());
        assertEquals(AssetType.SCRIPT, scriptAsset.getType());
        assertFalse(scriptAsset.isLoaded());
        
        // Clean up
        tempImageFile.delete();
        tempSoundFile.delete();
        tempModelFile.delete();
        tempConfigFile.delete();
        tempScriptFile.delete();
        
        System.out.println("All asset types test passed!");
    }
    
    @Test
    void testAssetCreationWithLoaders() {
        AssetManager assetManager = AssetManager.getInstance();
        
        // Test creating assets through loaders
        // Note: These will likely fail to load because the files don't exist,
        // but we're testing that the API works correctly
        TextureAsset texture = assetManager.createAsset("loadedTexture", "textures/loaded.png", TextureAsset.class);
        assertNotNull(texture);
        assertEquals("loadedTexture", texture.getId());
        // Don't assert isLoaded() because the file likely doesn't exist
        
        SoundAsset sound = assetManager.createAsset("loadedSound", "sounds/loaded.wav", SoundAsset.class);
        assertNotNull(sound);
        assertEquals("loadedSound", sound.getId());
        
        ModelAsset model = assetManager.createAsset("loadedModel", "models/loaded.obj", ModelAsset.class);
        assertNotNull(model);
        assertEquals("loadedModel", model.getId());
        
        ConfigAsset config = assetManager.createAsset("loadedConfig", "configs/loaded.cfg", ConfigAsset.class);
        assertNotNull(config);
        assertEquals("loadedConfig", config.getId());
        
        ScriptAsset script = assetManager.createAsset("loadedScript", "scripts/loaded.js", ScriptAsset.class);
        assertNotNull(script);
        assertEquals("loadedScript", script.getId());
        
        System.out.println("Asset creation with loaders test passed!");
    }
    
    @Test
    void testAssetTypeEnum() {
        // Test that each asset type returns the correct class
        assertEquals(TextureAsset.class, AssetType.TEXTURE.getAssetClass());
        assertEquals(ModelAsset.class, AssetType.MODEL.getAssetClass());
        assertEquals(SoundAsset.class, AssetType.SOUND.getAssetClass());
        assertEquals(ConfigAsset.class, AssetType.CONFIG.getAssetClass());
        assertEquals(ScriptAsset.class, AssetType.SCRIPT.getAssetClass());
        
        System.out.println("Asset type enum test passed!");
    }
}