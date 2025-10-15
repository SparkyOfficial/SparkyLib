package com.sparky.minecraft.ai;

import java.util.ArrayList;
import java.util.List;

import com.sparky.minecraft.NPCComponent;

/**
 * Вузол-селектор, який виконує дочірні вузли до першого успіху.
 *
 * @author Андрій Будильников
 */
public class SelectorNode extends BehaviorNode {
    private List<BehaviorNode> children;
    
    public SelectorNode() {
        this.children = new ArrayList<>();
    }
    
    // додає дочірній вузол
    public void addChild(BehaviorNode child) {
        children.add(child);
    }
    
    // виконує всі дочірні вузли до першого успіху
    @Override
    public Status execute(NPCComponent npc) {
        for (BehaviorNode child : children) {
            Status status = child.execute(npc);
            if (status == Status.SUCCESS) {
                return Status.SUCCESS;
            }
            if (status == Status.RUNNING) {
                return Status.RUNNING;
            }
        }
        return Status.FAILURE;
    }
}