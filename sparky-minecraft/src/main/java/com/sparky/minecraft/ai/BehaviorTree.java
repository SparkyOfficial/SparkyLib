package com.sparky.minecraft.ai;

import com.sparky.minecraft.NPCComponent;

/**
 * Дерево поведінки для штучного інтелекту NPC.
 *
 * @author Андрій Будильников
 */
public class BehaviorTree {
    private BehaviorNode root;
    private NPCComponent npc;
    
    public BehaviorTree(NPCComponent npc) {
        this.npc = npc;
        this.root = new SelectorNode();
    }
    
    // виконує дерево поведінки
    public void execute() {
        if (root != null) {
            root.execute(npc);
        }
    }
    
    // додає поведінковий вузол до кореня
    public void addNode(BehaviorNode node) {
        if (root instanceof SelectorNode) {
            ((SelectorNode) root).addChild(node);
        }
    }
    
    // встановлює кореневий вузол
    public void setRoot(BehaviorNode root) {
        this.root = root;
    }
}