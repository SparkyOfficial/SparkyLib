package com.sparky.minecraft.math;

import com.sparky.minecraft.math.Vector3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест для Vector3D класу.
 */
public class Vector3DTest {
    
    @Test
    public void testVectorCreation() {
        Vector3D vector = new Vector3D(1, 2, 3);
        assertEquals(1, vector.getX(), 0.001);
        assertEquals(2, vector.getY(), 0.001);
        assertEquals(3, vector.getZ(), 0.001);
    }
    
    @Test
    public void testVectorAddition() {
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(4, 5, 6);
        Vector3D result = v1.add(v2);
        assertEquals(5, result.getX(), 0.001);
        assertEquals(7, result.getY(), 0.001);
        assertEquals(9, result.getZ(), 0.001);
    }
}