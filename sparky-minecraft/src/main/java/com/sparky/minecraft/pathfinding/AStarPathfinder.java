package com.sparky.minecraft.pathfinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.sparky.minecraft.math.Vector3D;

/**
 * Алгоритм пошуку шляху A* для Minecraft.
 *
 * @author Андрій Будильников
 */
public class AStarPathfinder {
    private WorldGrid worldGrid;
    
    public AStarPathfinder(WorldGrid worldGrid) {
        this.worldGrid = worldGrid;
    }
    
    // знаходить шлях між двома точками
    public List<Vector3D> findPath(Vector3D start, Vector3D goal) {
        // ініціалізуємо відкриті та закриті множини
        PriorityQueue<Node> openSet = new PriorityQueue<>((a, b) -> Double.compare(a.fScore, b.fScore));
        Set<Vector3D> closedSet = new HashSet<>();
        
        // створюємо початковий вузол
        Node startNode = new Node(start, null, 0, start.distance(goal));
        openSet.add(startNode);
        
        while (!openSet.isEmpty()) {
            // отримуємо вузол з найменшою f-оцінкою
            Node current = openSet.poll();
            
            // якщо досягли цілі, відновлюємо шлях
            if (current.position.equals(goal)) {
                return reconstructPath(current);
            }
            
            // додаємо поточний вузол до закритої множини
            closedSet.add(current.position);
            
            // досліджуємо сусідів
            for (Vector3D neighborPos : getNeighbors(current.position)) {
                // пропускаємо заблоковані позиції
                if (!worldGrid.isWalkable(neighborPos)) {
                    continue;
                }
                
                // пропускаємо вже оброблені позиції
                if (closedSet.contains(neighborPos)) {
                    continue;
                }
                
                // обчислюємо g-оцінку для сусіда
                double tentativeGScore = current.gScore + current.position.distance(neighborPos);
                
                // шукаємо існуючий вузол для сусіда
                Node neighbor = findNodeInOpenSet(openSet, neighborPos);
                
                if (neighbor == null) {
                    // створюємо новий вузол
                    double hScore = neighborPos.distance(goal);
                    neighbor = new Node(neighborPos, current, tentativeGScore, hScore);
                    openSet.add(neighbor);
                } else if (tentativeGScore < neighbor.gScore) {
                    // знайшли кращий шлях до сусіда
                    neighbor.parent = current;
                    neighbor.gScore = tentativeGScore;
                    neighbor.fScore = neighbor.gScore + neighbor.hScore;
                }
            }
        }
        
        // шлях не знайдено
        return new ArrayList<>();
    }
    
    // отримує сусідів позиції
    private List<Vector3D> getNeighbors(Vector3D position) {
        List<Vector3D> neighbors = new ArrayList<>();
        
        // додаємо 6 основних сусідів (верх, низ, перед, назад, ліво, право)
        neighbors.add(new Vector3D(position.getX() + 1, position.getY(), position.getZ()));
        neighbors.add(new Vector3D(position.getX() - 1, position.getY(), position.getZ()));
        neighbors.add(new Vector3D(position.getX(), position.getY() + 1, position.getZ()));
        neighbors.add(new Vector3D(position.getX(), position.getY() - 1, position.getZ()));
        neighbors.add(new Vector3D(position.getX(), position.getY(), position.getZ() + 1));
        neighbors.add(new Vector3D(position.getX(), position.getY(), position.getZ() - 1));
        
        return neighbors;
    }
    
    // шукає вузол у відкритій множині
    private Node findNodeInOpenSet(PriorityQueue<Node> openSet, Vector3D position) {
        for (Node node : openSet) {
            if (node.position.equals(position)) {
                return node;
            }
        }
        return null;
    }
    
    // відновлює шлях з вузлів
    private List<Vector3D> reconstructPath(Node node) {
        List<Vector3D> path = new ArrayList<>();
        Node current = node;
        
        while (current != null) {
            path.add(0, current.position); // додаємо на початок для зворотного порядку
            current = current.parent;
        }
        
        return path;
    }
    
    // внутрішній клас для представлення вузла в алгоритмі
    private static class Node {
        Vector3D position;
        Node parent;
        double gScore; // відстань від початку
        double hScore; // евристична оцінка до цілі
        double fScore; // загальна оцінка (g + h)
        
        Node(Vector3D position, Node parent, double gScore, double hScore) {
            this.position = position;
            this.parent = parent;
            this.gScore = gScore;
            this.hScore = hScore;
            this.fScore = gScore + hScore;
        }
    }
}