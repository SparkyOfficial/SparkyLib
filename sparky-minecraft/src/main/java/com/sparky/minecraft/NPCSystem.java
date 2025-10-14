package com.sparky.minecraft;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sparky.ecs.Component;
import com.sparky.ecs.Entity;
import com.sparky.ecs.EntityManager;
import com.sparky.minecraft.math.Vector3D;

/**
 * Система для управління неігровими персонажами (NPC) в грі.
 *
 * @author Андрій Будильников
 */
public class NPCSystem extends com.sparky.ecs.System {
    private EntityManager entityManager;
    
    public NPCSystem() {
        
    }
    
    public NPCSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        
        for (Entity entity : entities) {
            if (entity.hasComponent(NPCComponent.class)) {
                NPCComponent npc = entity.getComponent(NPCComponent.class);
                
                processNPCUpdate(npc);
            }
        }
    }
    
    /**
     * Обробляє оновлення NPC.
     */
    private void processNPCUpdate(NPCComponent npc) {
        
    }
    
    /**
     * Створює NPC з заданими параметрами.
     *
     * @param npcId ID NPC
     * @param npcName ім'я NPC
     * @param entityType тип сутності
     * @param position позиція
     * @param rotation обертання
     * @return створена сутність NPC
     */
    public Entity createNPC(String npcId, String npcName, String entityType, 
                           Vector3D position, Vector3D rotation) {
        if (entityManager == null) {
            return null;
        }
        
        Entity npcEntity = entityManager.createEntity();
        NPCComponent npcComponent = new NPCComponent(npcId, npcName, entityType, 
                                                    position, rotation, "passive", 
                                                    "", false, 20, 20, 1, "neutral");
        
        
        entityManager.addComponentToEntity(npcEntity, npcComponent);
        
        return npcEntity;
    }
    
    /**
     * Завдає шкоди NPC.
     *
     * @param entityId ID сутності NPC
     * @param damage кількість шкоди
     * @return true, якщо шкоду завдано успішно
     */
    public boolean damageNPC(int entityId, int damage) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(NPCComponent.class)) {
            return false;
        }
        
        NPCComponent npc = entity.getComponent(NPCComponent.class);
        npc.takeDamage(damage);
        return true;
    }
    
    /**
     * Лікує NPC.
     *
     * @param entityId ID сутності NPC
     * @param heal кількість здоров'я для відновлення
     * @return true, якщо лікування успішне
     */
    public boolean healNPC(int entityId, int heal) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(NPCComponent.class)) {
            return false;
        }
        
        NPCComponent npc = entity.getComponent(NPCComponent.class);
        npc.heal(heal);
        return true;
    }
    
    /**
     * Переміщує NPC до нової позиції.
     *
     * @param entityId ID сутності NPC
     * @param newPosition нова позиція
     * @return true, якщо переміщення успішне
     */
    public boolean moveNPC(int entityId, Vector3D newPosition) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(NPCComponent.class)) {
            return false;
        }
        
        NPCComponent npc = entity.getComponent(NPCComponent.class);
        npc.moveTo(newPosition);
        return true;
    }
    
    /**
     * Перевіряє, чи NPC живий.
     *
     * @param entityId ID сутності NPC
     * @return true, якщо NPC живий
     */
    public boolean isNPCAlive(int entityId) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(NPCComponent.class)) {
            return false;
        }
        
        NPCComponent npc = entity.getComponent(NPCComponent.class);
        return npc.isAlive();
    }
    
    /**
     * Встановлює тип AI для NPC.
     *
     * @param entityId ID сутності NPC
     * @param aiType тип AI
     * @return true, якщо тип AI встановлено успішно
     */
    public boolean setNPCAIType(int entityId, String aiType) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(NPCComponent.class)) {
            return false;
        }
        
        NPCComponent npc = entity.getComponent(NPCComponent.class);
        npc.setAiType(aiType);
        return true;
    }
    
    /**
     * Встановлює діалог для NPC.
     *
     * @param entityId ID сутності NPC
     * @param dialogueTreeId ID дерева діалогів
     * @return true, якщо діалог встановлено успішно
     */
    public boolean setNPCDialogue(int entityId, String dialogueTreeId) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(NPCComponent.class)) {
            return false;
        }
        
        NPCComponent npc = entity.getComponent(NPCComponent.class);
        npc.setDialogueTreeId(dialogueTreeId);
        return true;
    }
    
    /**
     * Робить NPC ворожим.
     *
     * @param entityId ID сутності NPC
     * @param hostile чи NPC ворожий
     * @return true, якщо статус змінено успішно
     */
    public boolean setNPCHostile(int entityId, boolean hostile) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(NPCComponent.class)) {
            return false;
        }
        
        NPCComponent npc = entity.getComponent(NPCComponent.class);
        npc.setHostile(hostile);
        return true;
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(NPCComponent.class);
        return required;
    }
    
    /**
     * Встановлює менеджер сутностей.
     */
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}