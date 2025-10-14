package com.sparky.minecraft;

import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;
import com.sparky.ecs.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Система для управління квестами гравців та сутностей.
 *
 * @author Андрій Будильников
 */
public class QuestSystem extends com.sparky.ecs.System {
    private EntityManager entityManager;
    
    public QuestSystem() {
    }
    
    public QuestSystem(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void update(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.hasComponent(QuestComponent.class)) {
                QuestComponent questComponent = entity.getComponent(QuestComponent.class);
                processQuestUpdate(questComponent);
            }
        }
    }
    
    /**
     * Обробляє оновлення квестів.
     */
    private void processQuestUpdate(QuestComponent questComponent) {
    }
    
    /**
     * Додає квест до сутності.
     *
     * @param entityId ID сутності
     * @param quest квест для додавання
     * @return true, якщо квест додано успішно
     */
    public boolean addQuestToEntity(int entityId, Quest quest) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return false;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        questComponent.addActiveQuest(quest);
        return true;
    }
    
    /**
     * Видаляє квест з сутності.
     *
     * @param entityId ID сутності
     * @param quest квест для видалення
     * @return true, якщо квест видалено успішно
     */
    public boolean removeQuestFromEntity(int entityId, Quest quest) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return false;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        questComponent.removeActiveQuest(quest);
        return true;
    }
    
    /**
     * Завершує квест для сутності.
     *
     * @param entityId ID сутності
     * @param quest квест для завершення
     * @return true, якщо квест завершено успішно
     */
    public boolean completeQuestForEntity(int entityId, Quest quest) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return false;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        return questComponent.completeQuest(quest);
    }
    
    /**
     * Оновлює прогрес квесту для сутності.
     *
     * @param entityId ID сутності
     * @param quest квест для оновлення
     * @param progress новий прогрес
     * @return true, якщо прогрес оновлено успішно
     */
    public boolean updateQuestProgress(int entityId, Quest quest, int progress) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return false;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        questComponent.updateQuestProgress(quest, progress);
        return true;
    }
    
    /**
     * Створює компонент квестів для сутності.
     *
     * @param entityId ID сутності
     * @return true, якщо компонент створено успішно
     */
    public boolean createQuestsForEntity(int entityId) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null) {
            return false;
        }
        
        if (entity.hasComponent(QuestComponent.class)) {
            entity.removeComponent(QuestComponent.class);
        }
        
        QuestComponent questComponent = new QuestComponent();
        
        entityManager.addComponentToEntity(entity, questComponent);
        
        return true;
    }
    
    /**
     * Перевіряє, чи сутність має активний квест.
     *
     * @param entityId ID сутності
     * @param quest квест для перевірки
     * @return true, якщо квест активний
     */
    public boolean isQuestActiveForEntity(int entityId, Quest quest) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return false;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        return questComponent.isQuestActive(quest);
    }
    
    /**
     * Перевіряє, чи сутність завершила квест.
     *
     * @param entityId ID сутності
     * @param quest квест для перевірки
     * @return true, якщо квест завершений
     */
    public boolean isQuestCompletedForEntity(int entityId, Quest quest) {
        if (entityManager == null) {
            return false;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return false;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        return questComponent.isQuestCompleted(quest);
    }
    
    /**
     * Отримує кількість активних квестів у сутності.
     *
     * @param entityId ID сутності
     * @return кількість активних квестів
     */
    public int getActiveQuestCountForEntity(int entityId) {
        if (entityManager == null) {
            return 0;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return 0;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        return questComponent.getActiveQuests().size();
    }
    
    /**
     * Отримує кількість завершених квестів у сутності.
     *
     * @param entityId ID сутності
     * @return кількість завершених квестів
     */
    public int getCompletedQuestCountForEntity(int entityId) {
        if (entityManager == null) {
            return 0;
        }
        
        Entity entity = entityManager.getEntity(entityId);
        if (entity == null || !entity.hasComponent(QuestComponent.class)) {
            return 0;
        }
        
        QuestComponent questComponent = entity.getComponent(QuestComponent.class);
        return questComponent.getCompletedQuests().size();
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        Set<Class<? extends Component>> required = new HashSet<>();
        required.add(QuestComponent.class);
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