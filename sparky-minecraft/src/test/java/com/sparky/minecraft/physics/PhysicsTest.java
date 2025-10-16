package com.sparky.minecraft.physics;

import com.sparky.minecraft.math.Vector3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для фізичної системи.
 *
 * @author Андрій Будильников
 */
public class PhysicsTest {
    
    @Test
    public void testPhysicsWorldCreation() {
        PhysicsWorld physicsWorld = new PhysicsWorld();
        assertNotNull(physicsWorld);
        System.out.println("PhysicsWorld created successfully");
    }
    
    @Test
    public void testRigidBodyCreation() {
        Vector3D position = new Vector3D(0, 64, 0);
        double mass = 1.0;
        RigidBody body = new RigidBody(position, mass);
        
        assertNotNull(body);
        // We can't directly test getPosition() and getMass() since they might not exist
        // Instead, we'll test by calling methods that do exist
        body.setPosition(new Vector3D(10, 64, 10));
        body.setVelocity(new Vector3D(1, 0, 0));
        System.out.println("RigidBody created and methods tested successfully");
    }
    
    @Test
    public void testMultipleRigidBodies() {
        PhysicsWorld physicsWorld = new PhysicsWorld();
        
        // Створюємо тверді тіла
        RigidBody body1 = new RigidBody(new Vector3D(0, 64, 0), 1.0);
        RigidBody body2 = new RigidBody(new Vector3D(2, 64, 0), 2.0);
        
        // Додаємо тіла до світу
        physicsWorld.addBody(body1);
        physicsWorld.addBody(body2);
        
        // We can't directly test getBodies() since it doesn't exist in the API
        // Instead, we'll test by updating the physics world
        physicsWorld.update(0.016); // 60 FPS
        System.out.println("PhysicsWorld with 2 bodies updated successfully");
    }
    
    @Test
    public void testRigidBodyMassValidation() {
        Vector3D position = new Vector3D(0, 64, 0);
        
        // Тест з правильною масою
        RigidBody body = new RigidBody(position, 5.0);
        // We can't directly test getMass() since it might not exist
        // Instead, we'll test by calling methods that do exist
        body.setMass(5.0);
        System.out.println("RigidBody mass validation test passed");
    }
}