package com.sparky.minecraft.rendering;

import com.sparky.minecraft.math.Vector3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для рендеринг системи.
 *
 * @author Андрій Будильников
 */
public class RenderingSystemTest {
    
    @Test
    public void testCameraCreation() {
        Camera camera = new Camera();
        assertNotNull(camera);
        assertNotNull(camera.getPosition());
        assertNotNull(camera.getRotation());
        assertEquals(50.0, camera.getFov());
    }
    
    @Test
    public void testCameraPositionAndRotation() {
        Camera camera = new Camera();
        Vector3D position = new Vector3D(1.0, 2.0, 3.0);
        Vector3D rotation = new Vector3D(0.0, 0.0, 0.0);
        
        camera.setPosition(position);
        camera.setRotation(rotation);
        
        assertEquals(position, camera.getPosition());
        assertEquals(rotation, camera.getRotation());
    }
    
    @Test
    public void testCameraFov() {
        Camera camera = new Camera();
        camera.setFov(90.0);
        assertEquals(90.0, camera.getFov());
        
        // Test with values outside typical range
        camera.setFov(200.0);
        assertEquals(200.0, camera.getFov());
        
        camera.setFov(-10.0);
        assertEquals(-10.0, camera.getFov());
    }
    
    @Test
    public void testLightCreation() {
        // Тест для точкового світла
        PointLight pointLight = new PointLight(
            new Vector3D(1.0, 1.0, 1.0),
            new Vector3D(0.0, 0.0, 0.0),
            1.0
        );
        assertNotNull(pointLight);
        assertNotNull(pointLight.getPosition());
        assertNotNull(pointLight.getColor());
        assertEquals(1.0, pointLight.getIntensity());
        
        // Тест для направленого світла
        DirectionalLight directionalLight = new DirectionalLight(
            new Vector3D(1.0, 1.0, 1.0),
            new Vector3D(0.0, -1.0, 0.0),
            1.0
        );
        assertNotNull(directionalLight);
        assertNotNull(directionalLight.getDirection());
        assertNotNull(directionalLight.getColor());
        assertEquals(1.0, directionalLight.getIntensity());
    }
    
    @Test
    public void testLightProperties() {
        PointLight pointLight = new PointLight(
            new Vector3D(1.0, 1.0, 1.0),
            new Vector3D(0.0, 0.0, 0.0),
            1.0
        );
        Vector3D position = new Vector3D(5.0, 10.0, 15.0);
        Vector3D color = new Vector3D(1.0f, 0.5f, 0.2f);
        
        pointLight.setPosition(position);
        pointLight.setColor(color);
        pointLight.setIntensity(0.8);
        pointLight.setAttenuation(1.0, 0.1, 0.01);
        
        assertEquals(position, pointLight.getPosition());
        assertEquals(color, pointLight.getColor());
        assertEquals(0.8, pointLight.getIntensity());
    }
    
    @Test
    public void testLightingSystem() {
        LightingSystem lightingSystem = new LightingSystem();
        assertNotNull(lightingSystem);
        assertNotNull(lightingSystem.getLights());
        assertTrue(lightingSystem.getLights().isEmpty());
        
        // Додаємо світло
        PointLight light = new PointLight(
            new Vector3D(1.0, 1.0, 1.0),
            new Vector3D(0.0, 0.0, 0.0),
            1.0
        );
        lightingSystem.addLight(light);
        assertEquals(1, lightingSystem.getLights().size());
        
        // Видаляємо світло
        lightingSystem.removeLight(light);
        assertTrue(lightingSystem.getLights().isEmpty());
    }
    
    @Test
    public void testTextureCreation() {
        Texture texture = new Texture("test.png");
        assertNotNull(texture);
        assertEquals("test.png", texture.getFilePath());
        assertTrue(texture.getWidth() > 0);
        assertTrue(texture.getHeight() > 0);
    }
    
    @Test
    public void testTextureManager() {
        TextureManager textureManager = new TextureManager();
        assertNotNull(textureManager);
        
        // Завантажуємо текстуру
        textureManager.loadTexture("test", "test.png");
        Texture texture = textureManager.getTexture("test");
        assertNotNull(texture);
        assertEquals("test.png", texture.getFilePath());
        
        // Перевіряємо кількість текстур
        assertEquals(1, textureManager.getTextureCount());
        
        // Видаляємо текстуру
        textureManager.removeTexture("test");
        assertNull(textureManager.getTexture("test"));
        assertEquals(0, textureManager.getTextureCount());
    }
    
    @Test
    public void testShaderProgram() {
        ShaderProgram shader = new ShaderProgram("vertex.glsl", "fragment.glsl");
        assertNotNull(shader);
        assertEquals("vertex.glsl", shader.getVertexShaderFile());
        assertEquals("fragment.glsl", shader.getFragmentShaderFile());
        assertTrue(shader.isCompiled());
    }
    
    @Test
    public void testShaderManager() {
        ShaderManager shaderManager = new ShaderManager();
        assertNotNull(shaderManager);
        
        // Завантажуємо шейдер
        shaderManager.loadShader("test", "vertex.glsl", "fragment.glsl");
        ShaderProgram shader = shaderManager.getShader("test");
        assertNotNull(shader);
        assertEquals("vertex.glsl", shader.getVertexShaderFile());
        
        // Перевіряємо кількість шейдерів
        assertEquals(1, shaderManager.getShaderCount());
        
        // Видаляємо шейдер
        shaderManager.removeShader("test");
        assertNull(shaderManager.getShader("test"));
        assertEquals(0, shaderManager.getShaderCount());
    }
    
    @Test
    public void testRenderEngine() {
        RenderEngine renderEngine = new RenderEngine();
        assertNotNull(renderEngine);
        
        // Перевіряємо, що всі підсистеми створені
        assertNotNull(renderEngine.getCamera());
        assertNotNull(renderEngine.getLightingSystem());
        assertNotNull(renderEngine.getShaderManager());
        assertNotNull(renderEngine.getTextureManager());
    }
}