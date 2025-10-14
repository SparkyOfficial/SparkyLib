package com.sparky.minecraft.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Тест для BoundingBox класу.
 *
 * @author Андрій Будильников
 */
public class BoundingBoxTest {
    
    @Test
    public void testBoundingBoxCreation() {
        BoundingBox box = new BoundingBox(0, 0, 0, 1, 1, 1);
        assertEquals(0, box.getMin().getX(), 0.001);
        assertEquals(0, box.getMin().getY(), 0.001);
        assertEquals(0, box.getMin().getZ(), 0.001);
        assertEquals(1, box.getMax().getX(), 0.001);
        assertEquals(1, box.getMax().getY(), 0.001);
        assertEquals(1, box.getMax().getZ(), 0.001);
    }
    
    @Test
    public void testBoundingBoxCenter() {
        BoundingBox box = new BoundingBox(0, 0, 0, 2, 4, 6);
        Vector3D center = box.getCenter();
        assertEquals(1, center.getX(), 0.001);
        assertEquals(2, center.getY(), 0.001);
        assertEquals(3, center.getZ(), 0.001);
    }
    
    @Test
    public void testBoundingBoxSize() {
        BoundingBox box = new BoundingBox(0, 0, 0, 2, 4, 6);
        Vector3D size = box.getSize();
        assertEquals(2, size.getX(), 0.001);
        assertEquals(4, size.getY(), 0.001);
        assertEquals(6, size.getZ(), 0.001);
    }
    
    @Test
    public void testBoundingBoxContainsPoint() {
        BoundingBox box = new BoundingBox(0, 0, 0, 2, 2, 2);
        assertTrue(box.contains(new Vector3D(1, 1, 1)));
        assertTrue(box.contains(new Vector3D(0, 0, 0)));
        assertTrue(box.contains(new Vector3D(2, 2, 2)));
        assertFalse(box.contains(new Vector3D(3, 3, 3)));
        assertFalse(box.contains(new Vector3D(-1, -1, -1)));
    }
    
    @Test
    public void testBoundingBoxIntersects() {
        BoundingBox box1 = new BoundingBox(0, 0, 0, 2, 2, 2);
        BoundingBox box2 = new BoundingBox(1, 1, 1, 3, 3, 3);
        BoundingBox box3 = new BoundingBox(3, 3, 3, 4, 4, 4);
        
        assertTrue(box1.intersects(box2));
        assertFalse(box1.intersects(box3));
        assertTrue(box2.intersects(box3));
    }
    
    @Test
    public void testBoundingBoxUnion() {
        BoundingBox box1 = new BoundingBox(0, 0, 0, 1, 1, 1);
        BoundingBox box2 = new BoundingBox(2, 2, 2, 3, 3, 3);
        BoundingBox union = box1.union(box2);
        
        assertEquals(0, union.getMin().getX(), 0.001);
        assertEquals(0, union.getMin().getY(), 0.001);
        assertEquals(0, union.getMin().getZ(), 0.001);
        assertEquals(3, union.getMax().getX(), 0.001);
        assertEquals(3, union.getMax().getY(), 0.001);
        assertEquals(3, union.getMax().getZ(), 0.001);
    }
    
    @Test
    public void testBoundingBoxIntersection() {
        BoundingBox box1 = new BoundingBox(0, 0, 0, 2, 2, 2);
        BoundingBox box2 = new BoundingBox(1, 1, 1, 3, 3, 3);
        BoundingBox intersection = box1.intersection(box2);
        
        assertEquals(1, intersection.getMin().getX(), 0.001);
        assertEquals(1, intersection.getMin().getY(), 0.001);
        assertEquals(1, intersection.getMin().getZ(), 0.001);
        assertEquals(2, intersection.getMax().getX(), 0.001);
        assertEquals(2, intersection.getMax().getY(), 0.001);
        assertEquals(2, intersection.getMax().getZ(), 0.001);
    }
    
    @Test
    public void testBoundingBoxExpand() {
        BoundingBox box = new BoundingBox(0, 0, 0, 1, 1, 1);
        BoundingBox expanded = box.expand(0.5);
        
        assertEquals(-0.5, expanded.getMin().getX(), 0.001);
        assertEquals(-0.5, expanded.getMin().getY(), 0.001);
        assertEquals(-0.5, expanded.getMin().getZ(), 0.001);
        assertEquals(1.5, expanded.getMax().getX(), 0.001);
        assertEquals(1.5, expanded.getMax().getY(), 0.001);
        assertEquals(1.5, expanded.getMax().getZ(), 0.001);
    }
    
    @Test
    public void testBoundingBoxOffset() {
        BoundingBox box = new BoundingBox(0, 0, 0, 1, 1, 1);
        BoundingBox offset = box.offset(new Vector3D(2, 3, 4));
        
        assertEquals(2, offset.getMin().getX(), 0.001);
        assertEquals(3, offset.getMin().getY(), 0.001);
        assertEquals(4, offset.getMin().getZ(), 0.001);
        assertEquals(3, offset.getMax().getX(), 0.001);
        assertEquals(4, offset.getMax().getY(), 0.001);
        assertEquals(5, offset.getMax().getZ(), 0.001);
    }
}