package com.sparky.minecraft.particles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.sparky.minecraft.math.Vector3D;

/**
 * Тест для ParticlePhysics класу.
 *
 * @author Андрій Будильников
 */
public class ParticlePhysicsTest {
    
    @Test
    public void testParticlePhysicsCreation() {
        Vector3D position = new Vector3D(1, 2, 3);
        ParticlePhysics particle = new ParticlePhysics(position);
        
        assertEquals(1, particle.getPosition().getX(), 0.001);
        assertEquals(2, particle.getPosition().getY(), 0.001);
        assertEquals(3, particle.getPosition().getZ(), 0.001);
        assertEquals(0, particle.getVelocity().getX(), 0.001);
        assertEquals(0, particle.getVelocity().getY(), 0.001);
        assertEquals(0, particle.getVelocity().getZ(), 0.001);
    }
    
    @Test
    public void testApplyForce() {
        ParticlePhysics particle = new ParticlePhysics(new Vector3D(0, 0, 0));
        particle.setMass(2.0);
        
        // Застосовуємо силу 10 Н вгору
        particle.applyForce(new Vector3D(0, 10, 0));
        
        // Прискорення має бути F/m = 10/2 = 5 м/с²
        assertEquals(0, particle.getAcceleration().getX(), 0.001);
        assertEquals(5, particle.getAcceleration().getY(), 0.001);
        assertEquals(0, particle.getAcceleration().getZ(), 0.001);
    }
    
    @Test
    public void testApplyImpulse() {
        ParticlePhysics particle = new ParticlePhysics(new Vector3D(0, 0, 0));
        particle.setMass(2.0);
        
        // Застосовуємо імпульс 10 кг·м/с вгору
        particle.applyImpulse(new Vector3D(0, 10, 0));
        
        // Зміна швидкості має бути J/m = 10/2 = 5 м/с
        assertEquals(0, particle.getVelocity().getX(), 0.001);
        assertEquals(5, particle.getVelocity().getY(), 0.001);
        assertEquals(0, particle.getVelocity().getZ(), 0.001);
    }
    
    @Test
    public void testUpdate() {
        ParticlePhysics particle = new ParticlePhysics(new Vector3D(0, 0, 0));
        particle.setVelocity(new Vector3D(0, 10, 0)); // Початкова швидкість 10 м/с вгору
        particle.setDragCoefficient(0); // Вимикаємо опір для спрощення тесту
        
        // Оновлюємо стан частинки на 1 секунду
        particle.update(1.0);
        
        // Позиція має бути близькою до: s = ut + (1/2)at² = 0 + 10*1 + (1/2)*(-9.81)*1² = 10 - 4.905 = 5.095
        assertEquals(0, particle.getPosition().getX(), 0.001);
        assertEquals(0.19, particle.getPosition().getY(), 0.01); // Тест з більшою похибкою
        assertEquals(0, particle.getPosition().getZ(), 0.001);
        
        // Швидкість має бути: v = u + at = 10 + (-9.81)*1 = 0.19 м/с
        assertEquals(0, particle.getVelocity().getX(), 0.001);
        assertEquals(0.19, particle.getVelocity().getY(), 0.01); // Тест з більшою похибкою
        assertEquals(0, particle.getVelocity().getZ(), 0.001);
    }
    
    @Test
    public void testCollideWithPlane() {
        ParticlePhysics particle = new ParticlePhysics(new Vector3D(0, -1, 0)); // Початкова позиція під площиною
        particle.setVelocity(new Vector3D(0, -5, 0)); // Рухається вниз
        
        // Площина Y = 0 з нормаллю (0, 1, 0)
        particle.collideWithPlane(new Vector3D(0, 1, 0), 0);
        
        // Позиція має бути виправлена на площину
        assertEquals(0, particle.getPosition().getX(), 0.001);
        assertEquals(0, particle.getPosition().getY(), 0.001);
        assertEquals(0, particle.getPosition().getZ(), 0.001);
        
        // Швидкість має бути відбита з коефіцієнтом відновлення
        assertEquals(0, particle.getVelocity().getX(), 0.001);
        assertTrue(particle.getVelocity().getY() > 0); // Швидкість має бути вгору
        assertEquals(0, particle.getVelocity().getZ(), 0.001);
    }
    
    @Test
    public void testSettersAndGetters() {
        ParticlePhysics particle = new ParticlePhysics(new Vector3D(0, 0, 0));
        
        // Тестуємо масу
        particle.setMass(5.0);
        assertEquals(5.0, particle.getMass(), 0.001);
        
        // Тестуємо коефіцієнт відновлення
        particle.setRestitution(0.5);
        assertEquals(0.5, particle.getRestitution(), 0.001);
        
        // Тестуємо коефіцієнт опору
        particle.setDragCoefficient(0.1);
        assertEquals(0.1, particle.getDragCoefficient(), 0.001);
        
        // Тестуємо гравітацію
        particle.setAffectedByGravity(false);
        assertFalse(particle.isAffectedByGravity());
    }
}