package com.sparky.minecraft.ai;

import com.sparky.minecraft.NPCComponent;
import com.sparky.minecraft.math.Vector3D;

/**
 * Вузол для переміщення до цілі.
 *
 * @author Андрій Будильников
 */
public class MoveToNode extends BehaviorNode {
    private Vector3D target;
    private double speed;
    
    public MoveToNode(Vector3D target, double speed) {
        this.target = target;
        this.speed = speed;
    }
    
    // переміщує npc до цілі
    @Override
    public Status execute(NPCComponent npc) {
        // тут була б логіка переміщення
        // для прикладу просто повертаємо успіх
        return Status.SUCCESS;
    }
}