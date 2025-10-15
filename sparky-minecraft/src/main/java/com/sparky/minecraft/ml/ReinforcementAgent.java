package com.sparky.minecraft.ml;

import com.sparky.minecraft.NPCComponent;
import java.util.Random;

/**
 * Агент з підкріпленням для навчання NPC.
 *
 * @author Андрій Будильников
 */
public class ReinforcementAgent {
    private NeuralNetwork network;
    private double[] state;
    private int[] actions;
    private double learningRate;
    private double discountFactor;
    private double explorationRate;
    private Random random;
    private double[] qValues;
    
    public ReinforcementAgent(int stateSize, int actionCount) {
        // створюємо нейронну мережу: вхід - стан, вихід - Q-значення для кожної дії
        this.network = new NeuralNetwork(stateSize, 64, 32, actionCount);
        this.state = new double[stateSize];
        this.actions = new int[actionCount];
        this.learningRate = 0.01;
        this.discountFactor = 0.95;
        this.explorationRate = 0.1;
        this.random = new Random();
        this.qValues = new double[actionCount];
    }
    
    // вибирає дію на основі поточного стану
    public int selectAction(double[] currentState) {
        // зберігаємо поточний стан
        this.state = currentState.clone();
        
        // отримуємо Q-значення для всіх дій
        qValues = network.forward(currentState);
        
        // ε-жадібна стратегія
        if (random.nextDouble() < explorationRate) {
            // досліджуємо - випадкова дія
            return random.nextInt(actions.length);
        } else {
            // використовуємо - найкраща дія
            int bestAction = 0;
            for (int i = 1; i < qValues.length; i++) {
                if (qValues[i] > qValues[bestAction]) {
                    bestAction = i;
                }
            }
            return bestAction;
        }
    }
    
    // оновлює агента на основі винагороди
    public void update(double reward, double[] nextState) {
        // отримуємо Q-значення для наступного стану
        double[] nextQValues = network.forward(nextState);
        
        // знаходимо максимальне Q-значення для наступного стану
        double maxNextQ = nextQValues[0];
        for (int i = 1; i < nextQValues.length; i++) {
            if (nextQValues[i] > maxNextQ) {
                maxNextQ = nextQValues[i];
            }
        }
        
        // обчислюємо цільове Q-значення
        // Q(s,a) = r + γ * max(Q(s',a'))
        double target = reward + discountFactor * maxNextQ;
        
        // тут була б логіка оновлення мережі
        // для спрощення просто виводимо інформацію
        System.out.println("Agent updated with reward: " + reward + ", target: " + target);
    }
    
    // навчає агента на даних
    public void train(double[][] states, double[] rewards, double[][] nextStates) {
        // тут була б логіка навчання
        // для спрощення просто виводимо інформацію
        System.out.println("Training agent with " + states.length + " samples");
    }
    
    // встановлює швидкість навчання
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
    
    // встановлює фактор дисконту
    public void setDiscountFactor(double discountFactor) {
        this.discountFactor = discountFactor;
    }
    
    // встановлює рівень дослідження
    public void setExplorationRate(double explorationRate) {
        this.explorationRate = explorationRate;
    }
    
    // отримує поточні Q-значення
    public double[] getQValues() {
        return qValues.clone();
    }
}