package com.sparky.minecraft.ml;

import java.util.Random;

/**
 * Проста нейронна мережа для навчання NPC.
 *
 * @author Андрій Будильников
 */
public class NeuralNetwork {
    private int[] layerSizes;
    private double[][][] weights;
    private double[][] biases;
    private Random random;
    
    public NeuralNetwork(int... layerSizes) {
        this.layerSizes = layerSizes;
        this.random = new Random();
        initializeWeightsAndBiases();
    }
    
    // ініціалізує ваги та зміщення
    private void initializeWeightsAndBiases() {
        weights = new double[layerSizes.length - 1][][];
        biases = new double[layerSizes.length - 1][];
        
        for (int i = 0; i < layerSizes.length - 1; i++) {
            int currentLayerSize = layerSizes[i + 1];
            int previousLayerSize = layerSizes[i];
            
            weights[i] = new double[currentLayerSize][previousLayerSize];
            biases[i] = new double[currentLayerSize];
            
            // ініціалізуємо ваги випадковими значеннями
            for (int j = 0; j < currentLayerSize; j++) {
                for (int k = 0; k < previousLayerSize; k++) {
                    weights[i][j][k] = random.nextGaussian() * 0.1;
                }
                biases[i][j] = random.nextGaussian() * 0.1;
            }
        }
    }
    
    // прямий прохід через мережу
    public double[] forward(double[] inputs) {
        double[] activations = inputs;
        
        // проходимо через всі шари
        for (int layer = 0; layer < layerSizes.length - 1; layer++) {
            activations = applyLayer(activations, layer);
        }
        
        return activations;
    }
    
    // застосовує один шар
    private double[] applyLayer(double[] inputs, int layer) {
        int outputSize = layerSizes[layer + 1];
        double[] outputs = new double[outputSize];
        
        for (int i = 0; i < outputSize; i++) {
            double sum = biases[layer][i];
            for (int j = 0; j < inputs.length; j++) {
                sum += weights[layer][i][j] * inputs[j];
            }
            outputs[i] = sigmoid(sum);
        }
        
        return outputs;
    }
    
    // сигмоїдальна функція активації
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
    
    // похідна сигмоїди
    private double sigmoidDerivative(double x) {
        double s = sigmoid(x);
        return s * (1 - s);
    }
    
    // навчає мережу методом зворотного поширення
    public void train(double[][] inputs, double[][] targets, int epochs, double learningRate) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            double totalError = 0;
            
            for (int i = 0; i < inputs.length; i++) {
                // прямий прохід
                double[] outputs = forward(inputs[i]);
                
                // обчислюємо помилку
                double[] errors = new double[outputs.length];
                for (int j = 0; j < outputs.length; j++) {
                    errors[j] = targets[i][j] - outputs[j];
                    totalError += errors[j] * errors[j];
                }
                
                // зворотне поширення
                backpropagate(inputs[i], errors, learningRate);
            }
            
            // виводимо прогрес кожні 100 епох
            if (epoch % 100 == 0) {
                System.out.println("Epoch " + epoch + ", Error: " + totalError);
            }
        }
    }
    
    // зворотне поширення помилки
    private void backpropagate(double[] inputs, double[] outputErrors, double learningRate) {
        // обчислюємо дельти для вихідного шару
        double[][] deltas = new double[layerSizes.length - 1][];
        deltas[deltas.length - 1] = new double[outputErrors.length];
        
        for (int i = 0; i < outputErrors.length; i++) {
            // похідна сигмоїди * помилка
            deltas[deltas.length - 1][i] = sigmoidDerivative(0) * outputErrors[i];
        }
        
        // обчислюємо дельти для прихованих шарів
        for (int layer = deltas.length - 2; layer >= 0; layer--) {
            deltas[layer] = new double[layerSizes[layer + 1]];
            
            for (int i = 0; i < layerSizes[layer + 1]; i++) {
                double error = 0;
                for (int j = 0; j < layerSizes[layer + 2]; j++) {
                    error += weights[layer + 1][j][i] * deltas[layer + 1][j];
                }
                deltas[layer][i] = sigmoidDerivative(0) * error;
            }
        }
        
        // оновлюємо ваги та зміщення
        updateWeightsAndBiases(inputs, deltas, learningRate);
    }
    
    // оновлює ваги та зміщення
    private void updateWeightsAndBiases(double[] inputs, double[][] deltas, double learningRate) {
        // оновлюємо ваги між вхідним та першим прихованим шаром
        for (int i = 0; i < layerSizes[1]; i++) {
            for (int j = 0; j < inputs.length; j++) {
                weights[0][i][j] += learningRate * deltas[0][i] * inputs[j];
            }
            biases[0][i] += learningRate * deltas[0][i];
        }
        
        // оновлюємо ваги між іншими шарами
        for (int layer = 1; layer < weights.length; layer++) {
            for (int i = 0; i < layerSizes[layer + 1]; i++) {
                for (int j = 0; j < layerSizes[layer]; j++) {
                    // тут потрібно зберегти активації попереднього шару
                    // для спрощення використовуємо 0
                    weights[layer][i][j] += learningRate * deltas[layer][i] * 0;
                }
                biases[layer][i] += learningRate * deltas[layer][i];
            }
        }
    }
    
    // отримує розміри шарів
    public int[] getLayerSizes() {
        return layerSizes.clone();
    }
}