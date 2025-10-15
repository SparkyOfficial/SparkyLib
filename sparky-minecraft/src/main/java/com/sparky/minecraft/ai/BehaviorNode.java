package com.sparky.minecraft.ai;

import com.sparky.minecraft.NPCComponent;

/**
 * Базовий клас для вузлів поведінкового дерева.
 *
 * @author Андрій Будильников
 */
public abstract class BehaviorNode {
    public enum Status {
        SUCCESS, FAILURE, RUNNING
    }
    
    // виконує вузол
    public abstract Status execute(NPCComponent npc);
}