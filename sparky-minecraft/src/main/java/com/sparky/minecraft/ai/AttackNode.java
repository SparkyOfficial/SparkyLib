package com.sparky.minecraft.ai;

import com.sparky.minecraft.NPCComponent;

/**
 * Вузол для атаки цілі.
 *
 * @author Андрій Будильников
 */
public class AttackNode extends BehaviorNode {
    private double damage;
    private double attackRange;
    
    public AttackNode(double damage, double attackRange) {
        this.damage = damage;
        this.attackRange = attackRange;
    }
    
    // атакує ціль
    @Override
    public Status execute(NPCComponent npc) {
        // тут була б логіка атаки
        // для прикладу просто повертаємо успіх
        return Status.SUCCESS;
    }
}