package com.sparky.minecraft.particles;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.particles.ParticleSpec;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

/**
 * Тест для ParticleEffect3D класу.
 */
public class ParticleEffect3DTest {
    
    @Test
    public void testParticleEffect3DCreation() {
        Vector3D position = new Vector3D(1, 2, 3);
        ParticleEffect3D effect = new ParticleEffect3D("test", position);
        
        assertNotNull(effect);
        assertEquals("test", effect.getName());
        assertEquals(position.getX(), effect.getPosition().getX(), 0.001);
        assertEquals(position.getY(), effect.getPosition().getY(), 0.001);
        assertEquals(position.getZ(), effect.getPosition().getZ(), 0.001);
        assertTrue(effect.isAlive());
    }
    
    @Test
    public void testParticleEffect3DWithVelocity() {
        Vector3D position = new Vector3D(1, 2, 3);
        Vector3D velocity = new Vector3D(4, 5, 6);
        ParticleEffect3D effect = new ParticleEffect3D("test", position, velocity, 10.0);
        
        assertNotNull(effect);
        assertEquals("test", effect.getName());
        assertEquals(position.getX(), effect.getPosition().getX(), 0.001);
        assertEquals(position.getY(), effect.getPosition().getY(), 0.001);
        assertEquals(position.getZ(), effect.getPosition().getZ(), 0.001);
        assertEquals(velocity.getX(), effect.getVelocity().getX(), 0.001);
        assertEquals(velocity.getY(), effect.getVelocity().getY(), 0.001);
        assertEquals(velocity.getZ(), effect.getVelocity().getZ(), 0.001);
        assertEquals(10.0, effect.getMaxLifetime(), 0.001);
        assertTrue(effect.isAlive());
    }
    
    @Test
    public void testParticleEffect3DUpdate() {
        Vector3D position = new Vector3D(0, 0, 0);
        Vector3D velocity = new Vector3D(1, 0, 0);
        ParticleEffect3D effect = new ParticleEffect3D("test", position, velocity, 5.0);
        
        // Оновлюємо ефект на 1 секунду
        effect.update(1.0);
        
        // Перевіряємо, що позиція оновилася
        assertEquals(1.0, effect.getPosition().getX(), 0.001);
        assertEquals(0.0, effect.getPosition().getY(), 0.001);
        assertEquals(0.0, effect.getPosition().getZ(), 0.001);
        
        // Перевіряємо, що ефект все ще живий
        assertTrue(effect.isAlive());
        
        // Оновлюємо ефект на 4 секунди (загалом 5 секунд)
        effect.update(4.0);
        
        // Перевіряємо, що ефект більше не живий
        assertFalse(effect.isAlive());
    }
    
    @Test
    public void testExplosionCreation() {
        Vector3D position = new Vector3D(0, 0, 0);
        ParticleEffect3D explosion = ParticleEffect3D.createExplosion("explosion", position, 10);
        
        assertNotNull(explosion);
        assertEquals("explosion", explosion.getName());
        assertEquals(10, explosion.getSpecs().size());
    }
    
    @Test
    public void testApplyForce() {
        Vector3D position = new Vector3D(0, 0, 0);
        ParticleEffect3D effect = new ParticleEffect3D("test", position);
        
        Vector3D force = new Vector3D(0, -9.8, 0); // Гравітація
        effect.applyForce(force);
        
        // Перевіряємо, що прискорення оновилося
        assertEquals(0, effect.getAcceleration().getX(), 0.001);
        assertEquals(-9.8, effect.getAcceleration().getY(), 0.001);
        assertEquals(0, effect.getAcceleration().getZ(), 0.001);
    }
}