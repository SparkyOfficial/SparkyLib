package com.sparky.minecraft.ai;

import java.util.ArrayList;
import java.util.List;

import com.sparky.minecraft.NPCComponent;

/**
 * Вузол-послідовність, який виконує всі дочірні вузли до першої невдачі.
 *
 * @author Андрій Будильников
 */
public class SequenceNode extends BehaviorNode {
    private List<BehaviorNode> children;
    
    public SequenceNode() {
        this.children = new ArrayList<>();
    }
    
    // додає дочірній вузол
    public void addChild(BehaviorNode child) {
        children.add(child);
    }
    
    // виконує всі дочірні вузли до першої невдачі
    @Override
    public Status execute(NPCComponent npc) {
        for (BehaviorNode child : children) {
            Status status = child.execute(npc);
            if (status == Status.FAILURE) {
                return Status.FAILURE;
            }
            if (status == Status.RUNNING) {
                return Status.RUNNING;
            }
        }
        return Status.SUCCESS;
    }
}