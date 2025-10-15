package com.sparky.minecraft.ai;

import com.sparky.minecraft.NPCComponent;

/**
 * Вузол для пошуку гравця.
 *
 * @author Андрій Будильников
 */
public class FindPlayerNode extends BehaviorNode {
    private double searchRadius;
    
    public FindPlayerNode(double searchRadius) {
        this.searchRadius = searchRadius;
    }
    
    // шукає гравця в радіусі
    @Override
    public Status execute(NPCComponent npc) {
        // тут була б логіка пошуку гравця
        // для прикладу просто повертаємо успіх
        return Status.SUCCESS;
    }
}