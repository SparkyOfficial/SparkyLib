package com.sparky.minecraft.redstone;

import com.sparky.minecraft.math.Vector3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для редстоун системи.
 *
 * @author Андрій Будильников
 */
public class RedstoneSystemTest {
    
    @Test
    public void testRedstoneSystemCreation() {
        RedstoneSystem redstoneSystem = new RedstoneSystem();
        assertNotNull(redstoneSystem);
        assertNotNull(redstoneSystem.getAllComponents());
        assertTrue(redstoneSystem.getAllComponents().isEmpty());
    }
    
    @Test
    public void testRedstoneComponentCreation() {
        Vector3D position = new Vector3D(0, 64, 0);
        RedstoneSystem.RedstoneComponent component = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.LEVER, 
            position
        );
        
        assertEquals(RedstoneSystem.RedstoneComponentType.LEVER, component.getType());
        assertEquals(position, component.getPosition());
        assertEquals(RedstoneSystem.RedstonePowerState.UNPOWERED, component.getPowerState());
        assertEquals(0, component.getPowerLevel());
    }
    
    @Test
    public void testComponentPowerLevel() {
        Vector3D position = new Vector3D(0, 64, 0);
        RedstoneSystem.RedstoneComponent component = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE, 
            position
        );
        
        // Встановлюємо рівень сигналу
        component.setPowerLevel(10);
        assertEquals(10, component.getPowerLevel());
        
        // Перевіряємо обмеження діапазону
        component.setPowerLevel(20); // Повинно бути обмежено 15
        assertEquals(15, component.getPowerLevel());
        
        component.setPowerLevel(-5); // Повинно бути обмежено 0
        assertEquals(0, component.getPowerLevel());
    }
    
    @Test
    public void testComponentConnections() {
        Vector3D position = new Vector3D(0, 64, 0);
        RedstoneSystem.RedstoneComponent component = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_TORCH, 
            position
        );
        
        // Спочатку всі з'єднання мають бути false
        assertFalse(component.isConnected(RedstoneSystem.RedstoneDirection.NORTH));
        assertFalse(component.isConnected(RedstoneSystem.RedstoneDirection.SOUTH));
        assertFalse(component.isConnected(RedstoneSystem.RedstoneDirection.EAST));
        assertFalse(component.isConnected(RedstoneSystem.RedstoneDirection.WEST));
        assertFalse(component.isConnected(RedstoneSystem.RedstoneDirection.UP));
        assertFalse(component.isConnected(RedstoneSystem.RedstoneDirection.DOWN));
        
        // Встановлюємо з'єднання
        component.setConnection(RedstoneSystem.RedstoneDirection.NORTH, true);
        assertTrue(component.isConnected(RedstoneSystem.RedstoneDirection.NORTH));
        
        component.setConnection(RedstoneSystem.RedstoneDirection.EAST, true);
        assertTrue(component.isConnected(RedstoneSystem.RedstoneDirection.EAST));
    }
    
    @Test
    public void testAddingAndRemovingComponents() {
        RedstoneSystem redstoneSystem = new RedstoneSystem();
        Vector3D position1 = new Vector3D(0, 64, 0);
        Vector3D position2 = new Vector3D(1, 64, 0);
        
        RedstoneSystem.RedstoneComponent component1 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.LEVER, 
            position1
        );
        
        RedstoneSystem.RedstoneComponent component2 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_LAMP, 
            position2
        );
        
        // Додаємо компоненти
        redstoneSystem.addComponent(component1);
        redstoneSystem.addComponent(component2);
        
        assertEquals(2, redstoneSystem.getAllComponents().size());
        
        // Отримуємо компонент за позицією
        RedstoneSystem.RedstoneComponent retrievedComponent = redstoneSystem.getComponent(position1);
        assertNotNull(retrievedComponent);
        assertEquals(RedstoneSystem.RedstoneComponentType.LEVER, retrievedComponent.getType());
        
        // Видаляємо компонент
        redstoneSystem.removeComponent(position1);
        assertEquals(1, redstoneSystem.getAllComponents().size());
        assertNull(redstoneSystem.getComponent(position1));
    }
    
    @Test
    public void testBasicCircuitCreation() {
        RedstoneSystem redstoneSystem = new RedstoneSystem();
        
        // Створюємо базову схему
        redstoneSystem.createBasicCircuit(new Vector3D(0, 64, 0));
        
        // Перевіряємо, що компоненти були створені
        assertFalse(redstoneSystem.getAllComponents().isEmpty());
        assertEquals(3, redstoneSystem.getAllComponents().size());
    }
    
    @Test
    public void testComponentConnectionLogic() {
        RedstoneSystem redstoneSystem = new RedstoneSystem();
        
        // Створюємо компоненти, які не можуть бути з'єднані
        RedstoneSystem.RedstoneComponent block = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_BLOCK, 
            new Vector3D(0, 64, 0)
        );
        
        RedstoneSystem.RedstoneComponent torch = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_TORCH, 
            new Vector3D(1, 64, 0)
        );
        
        // Перевіряємо, що їх не можна з'єднати
        assertFalse(redstoneSystem.canConnect(block, torch));
        assertFalse(redstoneSystem.canConnect(torch, block));
        
        // Створюємо компоненти, які можуть бути з'єднані
        RedstoneSystem.RedstoneComponent wire1 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE, 
            new Vector3D(0, 65, 0)
        );
        
        RedstoneSystem.RedstoneComponent wire2 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE, 
            new Vector3D(1, 65, 0)
        );
        
        // Перевіряємо, що їх можна з'єднати
        assertTrue(redstoneSystem.canConnect(wire1, wire2));
        assertTrue(redstoneSystem.canConnect(wire2, wire1));
    }
    
    @Test
    public void testMaxSignalDistance() {
        RedstoneSystem redstoneSystem = new RedstoneSystem();
        
        // Перевіряємо максимальну відстань для різних типів компонентів
        int wireDistance = redstoneSystem.getMaxSignalDistanceForType(RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE);
        assertEquals(redstoneSystem.getMaxSignalDistance(), wireDistance);
        
        int torchDistance = redstoneSystem.getMaxSignalDistanceForType(RedstoneSystem.RedstoneComponentType.REDSTONE_TORCH);
        assertEquals(12, torchDistance);
        
        int blockDistance = redstoneSystem.getMaxSignalDistanceForType(RedstoneSystem.RedstoneComponentType.REDSTONE_BLOCK);
        assertEquals(Integer.MAX_VALUE, blockDistance);
    }
}