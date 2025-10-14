package com.sparky.minecraft.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 * Тест для Quaternion класу.
 *
 * @author Андрій Будильников
 */
public class QuaternionTest {
    
    @Test
    public void testQuaternionCreation() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        assertEquals(1, q.getW(), 0.001);
        assertEquals(2, q.getX(), 0.001);
        assertEquals(3, q.getY(), 0.001);
        assertEquals(4, q.getZ(), 0.001);
    }
    
    @Test
    public void testQuaternionAddition() {
        Quaternion q1 = new Quaternion(1, 2, 3, 4);
        Quaternion q2 = new Quaternion(2, 3, 4, 5);
        Quaternion result = q1.add(q2);
        assertEquals(3, result.getW(), 0.001);
        assertEquals(5, result.getX(), 0.001);
        assertEquals(7, result.getY(), 0.001);
        assertEquals(9, result.getZ(), 0.001);
    }
    
    @Test
    public void testQuaternionSubtraction() {
        Quaternion q1 = new Quaternion(3, 4, 5, 6);
        Quaternion q2 = new Quaternion(1, 2, 3, 4);
        Quaternion result = q1.subtract(q2);
        assertEquals(2, result.getW(), 0.001);
        assertEquals(2, result.getX(), 0.001);
        assertEquals(2, result.getY(), 0.001);
        assertEquals(2, result.getZ(), 0.001);
    }
    
    @Test
    public void testQuaternionMultiplication() {
        Quaternion q1 = new Quaternion(1, 2, 3, 4);
        Quaternion q2 = new Quaternion(2, 3, 4, 5);
        Quaternion result = q1.multiply(q2);
        // (1+2i+3j+4k) * (2+3i+4j+5k) = -36 + 6i + 12j + 12k
        assertEquals(-36, result.getW(), 0.001);
        assertEquals(6, result.getX(), 0.001);
        assertEquals(12, result.getY(), 0.001);
        assertEquals(12, result.getZ(), 0.001);
    }
    
    @Test
    public void testQuaternionScalarMultiplication() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        Quaternion result = q.multiply(2);
        assertEquals(2, result.getW(), 0.001);
        assertEquals(4, result.getX(), 0.001);
        assertEquals(6, result.getY(), 0.001);
        assertEquals(8, result.getZ(), 0.001);
    }
    
    @Test
    public void testQuaternionConjugate() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        Quaternion result = q.conjugate();
        assertEquals(1, result.getW(), 0.001);
        assertEquals(-2, result.getX(), 0.001);
        assertEquals(-3, result.getY(), 0.001);
        assertEquals(-4, result.getZ(), 0.001);
    }
    
    @Test
    public void testQuaternionNorm() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        double norm = q.norm();
        assertEquals(Math.sqrt(30), norm, 0.001); // sqrt(1^2 + 2^2 + 3^2 + 4^2) = sqrt(30)
    }
    
    @Test
    public void testQuaternionNormalize() {
        Quaternion q = new Quaternion(1, 2, 3, 4);
        Quaternion normalized = q.normalize();
        assertEquals(1.0, normalized.norm(), 0.001); // Нормалізований кватерніон має норму 1
    }
    
    @Test
    public void testFromAxisAngle() {
        Vector3D axis = new Vector3D(0, 1, 0); // Обертання навколо осі Y
        double angle = Math.PI / 2; // 90 градусів
        Quaternion q = Quaternion.fromAxisAngle(axis, angle);
        assertNotNull(q);
        // Для обертання на 90 градусів навколо Y: cos(45°) + sin(45°)j
        assertEquals(Math.cos(Math.PI / 4), q.getW(), 0.001);
        assertEquals(0, q.getX(), 0.001);
        assertEquals(Math.sin(Math.PI / 4), q.getY(), 0.001);
        assertEquals(0, q.getZ(), 0.001);
    }
}