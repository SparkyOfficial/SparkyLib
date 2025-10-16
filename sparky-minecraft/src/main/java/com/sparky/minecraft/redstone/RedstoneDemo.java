package com.sparky.minecraft.redstone;

import com.sparky.minecraft.math.Vector3D;

/**
 * Демонстрація редстоун системи.
 *
 * @author Андрій Будильников
 */
public class RedstoneDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрація редстоун системи ===\n");
        
        // Створюємо систему редстоуну
        RedstoneSystem redstoneSystem = new RedstoneSystem();
        
        // Демонстрація базової схеми
        demonstrateBasicCircuit(redstoneSystem);
        
        System.out.println();
        
        // Демонстрація просунутої схеми
        demonstrateAdvancedCircuit(redstoneSystem);
        
        System.out.println();
        
        // Демонстрація компонентів
        demonstrateComponents(redstoneSystem);
        
        System.out.println();
        
        // Демонстрація з'єднань
        demonstrateConnections(redstoneSystem);
        
        System.out.println("\n=== Демонстрація завершена ===");
    }
    
    /**
     * Демонстрація базової редстоун схеми
     */
    private static void demonstrateBasicCircuit(RedstoneSystem redstoneSystem) {
        System.out.println("1. Базова редстоун схема:");
        
        // Створюємо просту схему: важіль -> провід -> лампа
        redstoneSystem.createBasicCircuit(new Vector3D(0, 64, 0));
        
        System.out.println("  Створено базову схему з важеля, проводу та лампи");
        
        // Отримуємо всі компоненти
        var components = redstoneSystem.getAllComponents();
        System.out.println("  Кількість компонентів: " + components.size());
        
        // Оновлюємо систему
        redstoneSystem.updateRedstoneSystem();
        System.out.println("  Систему оновлено");
    }
    
    /**
     * Демонстрація просунутої редстоун схеми
     */
    private static void demonstrateAdvancedCircuit(RedstoneSystem redstoneSystem) {
        System.out.println("2. Просунута редстоун схема:");
        
        // Створюємо просунуту схему
        redstoneSystem.createAdvancedCircuit(new Vector3D(10, 64, 10));
        
        System.out.println("  Створено просунуту схему");
        
        // Вмикаємо складні схеми
        redstoneSystem.setEnableComplexCircuits(true);
        System.out.println("  Просунуті схеми ввімкнено: " + redstoneSystem.isEnableComplexCircuits());
    }
    
    /**
     * Демонстрація редстоун компонентів
     */
    private static void demonstrateComponents(RedstoneSystem redstoneSystem) {
        System.out.println("3. Редстоун компоненти:");
        
        // Створюємо різні компоненти
        RedstoneSystem.RedstoneComponent lever = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.LEVER, 
            new Vector3D(5, 64, 5)
        );
        
        RedstoneSystem.RedstoneComponent wire = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE, 
            new Vector3D(6, 64, 5)
        );
        
        RedstoneSystem.RedstoneComponent lamp = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_LAMP, 
            new Vector3D(7, 64, 5)
        );
        
        // Додаємо компоненти до системи
        redstoneSystem.addComponent(lever);
        redstoneSystem.addComponent(wire);
        redstoneSystem.addComponent(lamp);
        
        System.out.println("  Створено компоненти: важіль, провід, лампа");
        
        // Встановлюємо з'єднання
        lever.setConnection(RedstoneSystem.RedstoneDirection.EAST, true);
        wire.setConnection(RedstoneSystem.RedstoneDirection.WEST, true);
        wire.setConnection(RedstoneSystem.RedstoneDirection.EAST, true);
        lamp.setConnection(RedstoneSystem.RedstoneDirection.WEST, true);
        
        System.out.println("  Встановлено з'єднання між компонентами");
        
        // Активуємо важіль
        lever.setPoweredByDirectSignal(true);
        lever.setPowerLevel(15);
        
        System.out.println("  Активовано важіль (рівень сигналу: " + lever.getPowerLevel() + ")");
        
        // Оновлюємо систему
        redstoneSystem.updateRedstoneSystem();
        
        System.out.println("  Стан лампи після оновлення: " + lamp.getPowerState());
        System.out.println("  Рівень сигналу лампи: " + lamp.getPowerLevel());
    }
    
    /**
     * Демонстрація з'єднань між компонентами
     */
    private static void demonstrateConnections(RedstoneSystem redstoneSystem) {
        System.out.println("4. З'єднання компонентів:");
        
        // Створюємо два компоненти
        RedstoneSystem.RedstoneComponent component1 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_TORCH, 
            new Vector3D(0, 65, 0)
        );
        
        RedstoneSystem.RedstoneComponent component2 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_BLOCK, 
            new Vector3D(1, 65, 0)
        );
        
        // Перевіряємо, чи можна їх з'єднати
        boolean canConnect = redstoneSystem.canConnect(component1, component2);
        System.out.println("  Чи можна з'єднати смолоскип і блок редстоуну: " + canConnect);
        
        // Створюємо інші компоненти
        RedstoneSystem.RedstoneComponent component3 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE, 
            new Vector3D(0, 65, 1)
        );
        
        RedstoneSystem.RedstoneComponent component4 = new RedstoneSystem.RedstoneComponent(
            RedstoneSystem.RedstoneComponentType.REDSTONE_REPEATER, 
            new Vector3D(1, 65, 1)
        );
        
        // Перевіряємо з'єднання
        boolean canConnect2 = redstoneSystem.canConnect(component3, component4);
        System.out.println("  Чи можна з'єднати провід і повторювач: " + canConnect2);
        
        // Отримуємо максимальну відстань сигналу
        int maxDistance = redstoneSystem.getMaxSignalDistanceForType(RedstoneSystem.RedstoneComponentType.REDSTONE_WIRE);
        System.out.println("  Максимальна відстань сигналу для проводу: " + maxDistance);
        
        // Симулюємо випадковий імпульс
        redstoneSystem.simulateRandomPulse();
        System.out.println("  Симульовано випадковий редстоун імпульс");
    }
}