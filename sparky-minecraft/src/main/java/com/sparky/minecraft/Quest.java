package com.sparky.minecraft;

/**
 * Представляє квест в Minecraft.
 *
 * @author Андрій Будильников
 */
public class Quest {
    private String questId;
    private String questName;
    private String description;
    private int requiredProgress;
    private int rewardPoints;
    private ItemStack[] rewards;
    private String[] objectives;
    
    public Quest(String questId, String questName, String description) {
        this.questId = questId;
        this.questName = questName;
        this.description = description;
        this.requiredProgress = 100;
        this.rewardPoints = 10;
        this.rewards = new ItemStack[0];
        this.objectives = new String[0];
    }
    
    
    public String getQuestId() {
        return questId;
    }
    
    public void setQuestId(String questId) {
        this.questId = questId;
    }
    
    public String getQuestName() {
        return questName;
    }
    
    public void setQuestName(String questName) {
        this.questName = questName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getRequiredProgress() {
        return requiredProgress;
    }
    
    public void setRequiredProgress(int requiredProgress) {
        this.requiredProgress = requiredProgress;
    }
    
    public int getRewardPoints() {
        return rewardPoints;
    }
    
    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
    
    public ItemStack[] getRewards() {
        return rewards != null ? rewards.clone() : new ItemStack[0];
    }
    
    public void setRewards(ItemStack[] rewards) {
        this.rewards = rewards != null ? rewards.clone() : new ItemStack[0];
    }
    
    public String[] getObjectives() {
        return objectives != null ? objectives.clone() : new String[0];
    }
    
    public void setObjectives(String[] objectives) {
        this.objectives = objectives != null ? objectives.clone() : new String[0];
    }
    
    /**
     * Додає нагороду до квесту.
     *
     * @param reward нагорода для додавання
     */
    public void addReward(ItemStack reward) {
        ItemStack[] newRewards = new ItemStack[rewards.length + 1];
        System.arraycopy(rewards, 0, newRewards, 0, rewards.length);
        newRewards[rewards.length] = reward;
        this.rewards = newRewards;
    }
    
    /**
     * Додає ціль до квесту.
     *
     * @param objective ціль для додавання
     */
    public void addObjective(String objective) {
        String[] newObjectives = new String[objectives.length + 1];
        System.arraycopy(objectives, 0, newObjectives, 0, objectives.length);
        newObjectives[objectives.length] = objective;
        this.objectives = newObjectives;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quest quest = (Quest) obj;
        return questId.equals(quest.questId);
    }
    
    @Override
    public int hashCode() {
        return questId.hashCode();
    }
    
    @Override
    public String toString() {
        return "Quest{" +
                "questId='" + questId + '\'' +
                ", questName='" + questName + '\'' +
                ", requiredProgress=" + requiredProgress +
                ", rewardPoints=" + rewardPoints +
                '}';
    }
}