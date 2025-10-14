package com.sparky.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sparky.ecs.Component;

/**
 * Компонент квестів для представлення активних та завершених квестів сутності в ECS.
 *
 * @author Андрій Будильников
 */
public class QuestComponent extends Component {
    private List<Quest> activeQuests;
    private List<Quest> completedQuests;
    private Map<String, Integer> questProgress;
    private int questPoints;
    
    public QuestComponent() {
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
        this.questProgress = new HashMap<>();
        this.questPoints = 0;
    }
    
    /**
     * Додає квест до активних квестів.
     *
     * @param quest квест для додавання
     */
    public void addActiveQuest(Quest quest) {
        if (!activeQuests.contains(quest) && !completedQuests.contains(quest)) {
            activeQuests.add(quest);
            questProgress.put(quest.getQuestId(), 0);
        }
    }
    
    /**
     * Видаляє квест з активних квестів.
     *
     * @param quest квест для видалення
     */
    public void removeActiveQuest(Quest quest) {
        activeQuests.remove(quest);
        questProgress.remove(quest.getQuestId());
    }
    
    /**
     * Завершує квест.
     *
     * @param quest квест для завершення
     * @return true, якщо квест завершено успішно
     */
    public boolean completeQuest(Quest quest) {
        if (!activeQuests.contains(quest)) {
            return false;
        }
        
        // Перевіряємо, чи виконані всі умови квесту
        Integer progress = questProgress.get(quest.getQuestId());
        if (progress == null || progress < quest.getRequiredProgress()) {
            return false;
        }
        
        // Видаляємо з активних квестів
        activeQuests.remove(quest);
        questProgress.remove(quest.getQuestId());
        
        // Додаємо до завершених квестів
        completedQuests.add(quest);
        
        // Додаємо очки квестів
        questPoints += quest.getRewardPoints();
        
        return true;
    }
    
    /**
     * Оновлює прогрес квесту.
     *
     * @param quest квест для оновлення
     * @param progress новий прогрес
     */
    public void updateQuestProgress(Quest quest, int progress) {
        if (activeQuests.contains(quest)) {
            questProgress.put(quest.getQuestId(), Math.max(0, progress));
        }
    }
    
    /**
     * Отримує прогрес квесту.
     *
     * @param quest квест для отримання прогресу
     * @return прогрес квесту
     */
    public int getQuestProgress(Quest quest) {
        Integer progress = questProgress.get(quest.getQuestId());
        return progress != null ? progress : 0;
    }
    
    /**
     * Перевіряє, чи квест активний.
     *
     * @param quest квест для перевірки
     * @return true, якщо квест активний
     */
    public boolean isQuestActive(Quest quest) {
        return activeQuests.contains(quest);
    }
    
    /**
     * Перевіряє, чи квест завершений.
     *
     * @param quest квест для перевірки
     * @return true, якщо квест завершений
     */
    public boolean isQuestCompleted(Quest quest) {
        return completedQuests.contains(quest);
    }
    
    /**
     * Отримує список активних квестів.
     *
     * @return копія списку активних квестів
     */
    public List<Quest> getActiveQuests() {
        return new ArrayList<>(activeQuests);
    }
    
    /**
     * Отримує список завершених квестів.
     *
     * @return копія списку завершених квестів
     */
    public List<Quest> getCompletedQuests() {
        return new ArrayList<>(completedQuests);
    }
    
    
    public int getQuestPoints() {
        return questPoints;
    }
    
    public void setQuestPoints(int questPoints) {
        this.questPoints = questPoints;
    }
    
    /**
     * Додає очки квестів.
     *
     * @param points кількість очок для додавання
     */
    public void addQuestPoints(int points) {
        this.questPoints += points;
    }
}