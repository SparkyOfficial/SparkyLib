package com.sparky.minecraft.particles;

import com.sparky.minecraft.math.Vector3D;
import com.sparky.minecraft.particles.ParticleEffect3D;
import com.sparky.minecraft.particles.ParticleSystem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест для ParticleSystem класу.
 */
public class ParticleSystemTest {
    
    @Test
    public void testParticleSystemCreation() {
        ParticleSystem system = new ParticleSystem();
        
        assertNotNull(system);
        assertFalse(system.isRunning());
        assertEquals(0, system.getEffects().size());
    }
    
    @Test
    public void testAddAndRemoveEffect() {
        ParticleSystem system = new ParticleSystem();
        Vector3D position = new Vector3D(0, 0, 0);
        ParticleEffect3D effect = new ParticleEffect3D("test", position);
        
        // Додаємо ефект
        system.addEffect(effect);
        assertEquals(1, system.getEffects().size());
        
        // Видаляємо ефект
        system.removeEffect(effect);
        assertEquals(0, system.getEffects().size());
    }
    
    @Test
    public void testStartAndStop() {
        ParticleSystem system = new ParticleSystem();
        
        assertFalse(system.isRunning());
        
        system.start();
        assertTrue(system.isRunning());
        
        system.stop();
        assertFalse(system.isRunning());
    }
    
    @Test
    public void testUpdate() {
        ParticleSystem system = new ParticleSystem();
        system.start();
        
        Vector3D position = new Vector3D(0, 0, 0);
        Vector3D velocity = new Vector3D(1, 0, 0);
        ParticleEffect3D effect = new ParticleEffect3D("test", position, velocity, 2.0);
        
        system.addEffect(effect);
        assertEquals(1, system.getEffects().size());
        
        // Оновлюємо систему на 1 секунду
        system.update(1.0);
        assertEquals(1, system.getEffects().size()); // Ефект ще живий
        
        // Оновлюємо систему на 1 секунду (загалом 2 секунди)
        system.update(1.0);
        assertEquals(0, system.getEffects().size()); // Ефект більше не живий
    }
    
    @Test
    public void testCreateExplosionSystem() {
        Vector3D position = new Vector3D(0, 0, 0);
        ParticleSystem system = ParticleSystem.createExplosionSystem(position, 10);
        
        assertNotNull(system);
        // Має бути 4 ефекти (1 основний + 3 вторинних)
        assertEquals(4, system.getEffects().size());
    }
}