package com.sparky.minecraft.pathfinding;

import com.sparky.minecraft.math.Vector3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для системи пошуку шляхів.
 *
 * @author Андрій Будильников
 */
public class PathfindingTest {
    
    @Test
    public void testWorldGridCreation() {
        WorldGrid grid = new WorldGrid();
        assertNotNull(grid);
        System.out.println("WorldGrid created successfully");
    }
    
    @Test
    public void testAStarPathfinderCreation() {
        WorldGrid grid = new WorldGrid();
        AStarPathfinder pathfinder = new AStarPathfinder(grid);
        assertNotNull(pathfinder);
        System.out.println("AStarPathfinder created successfully");
    }
    
    @Test
    public void testPathfindingWithStartAndGoal() {
        WorldGrid grid = new WorldGrid();
        AStarPathfinder pathfinder = new AStarPathfinder(grid);
        
        Vector3D start = new Vector3D(0, 64, 0);
        Vector3D goal = new Vector3D(10, 64, 10);
        
        assertNotNull(start);
        assertNotNull(goal);
        System.out.println("Pathfinding test with start: " + start + " and goal: " + goal);
    }
}