package com.sparky.minecraft.math;

/**
 * Обмежувальна коробка для 3D об'єктів у Minecraft.
 *
 * @author Андрій Будильников
 */
public class BoundingBox {
    private Vector3D min;
    private Vector3D max;

    public BoundingBox() {
        this.min = new Vector3D(0, 0, 0);
        this.max = new Vector3D(0, 0, 0);
    }

    public BoundingBox(Vector3D min, Vector3D max) {
        this.min = new Vector3D(min);
        this.max = new Vector3D(max);
    }

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.min = new Vector3D(minX, minY, minZ);
        this.max = new Vector3D(maxX, maxY, maxZ);
    }

    // Геттери та сеттери
    public Vector3D getMin() {
        return new Vector3D(min);
    }

    public void setMin(Vector3D min) {
        this.min = new Vector3D(min);
    }

    public Vector3D getMax() {
        return new Vector3D(max);
    }

    public void setMax(Vector3D max) {
        this.max = new Vector3D(max);
    }

    /**
     * Повертає центр коробки.
     */
    public Vector3D getCenter() {
        return min.add(max).multiply(0.5);
    }

    /**
     * Повертає розміри коробки.
     */
    public Vector3D getSize() {
        return max.subtract(min);
    }

    /**
     * Повертає ширину коробки (по X).
     */
    public double getWidth() {
        return max.getX() - min.getX();
    }

    /**
     * Повертає висоту коробки (по Y).
     */
    public double getHeight() {
        return max.getY() - min.getY();
    }

    /**
     * Повертає глибину коробки (по Z).
     */
    public double getDepth() {
        return max.getZ() - min.getZ();
    }

    /**
     * Перевіряє, чи містить точка в коробці.
     */
    public boolean contains(Vector3D point) {
        return point.getX() >= min.getX() && point.getX() <= max.getX() &&
               point.getY() >= min.getY() && point.getY() <= max.getY() &&
               point.getZ() >= min.getZ() && point.getZ() <= max.getZ();
    }

    /**
     * Перевіряє, чи містить інша коробка в цій коробці.
     */
    public boolean contains(BoundingBox other) {
        return this.contains(other.min) && this.contains(other.max);
    }

    /**
     * Перевіряє, чи перетинається з іншою коробкою.
     */
    public boolean intersects(BoundingBox other) {
        return !(other.max.getX() < this.min.getX() ||
                 other.min.getX() > this.max.getX() ||
                 other.max.getY() < this.min.getY() ||
                 other.min.getY() > this.max.getY() ||
                 other.max.getZ() < this.min.getZ() ||
                 other.min.getZ() > this.max.getZ());
    }

    /**
     * Об'єднує цю коробку з іншою.
     */
    public BoundingBox union(BoundingBox other) {
        Vector3D newMin = new Vector3D(
            Math.min(this.min.getX(), other.min.getX()),
            Math.min(this.min.getY(), other.min.getY()),
            Math.min(this.min.getZ(), other.min.getZ())
        );
        Vector3D newMax = new Vector3D(
            Math.max(this.max.getX(), other.max.getX()),
            Math.max(this.max.getY(), other.max.getY()),
            Math.max(this.max.getZ(), other.max.getZ())
        );
        return new BoundingBox(newMin, newMax);
    }

    /**
     * Перетин з іншою коробкою.
     */
    public BoundingBox intersection(BoundingBox other) {
        Vector3D newMin = new Vector3D(
            Math.max(this.min.getX(), other.min.getX()),
            Math.max(this.min.getY(), other.min.getY()),
            Math.max(this.min.getZ(), other.min.getZ())
        );
        Vector3D newMax = new Vector3D(
            Math.min(this.max.getX(), other.max.getX()),
            Math.min(this.max.getY(), other.max.getY()),
            Math.min(this.max.getZ(), other.max.getZ())
        );
        // Перевіряємо, чи перетин існує
        if (newMin.getX() > newMax.getX() || 
            newMin.getY() > newMax.getY() || 
            newMin.getZ() > newMax.getZ()) {
            // Повертаємо порожню коробку
            return new BoundingBox(0, 0, 0, 0, 0, 0);
        }
        return new BoundingBox(newMin, newMax);
    }

    /**
     * Збільшує коробку на вказаний розмір.
     */
    public BoundingBox expand(double amount) {
        return new BoundingBox(
            min.getX() - amount, min.getY() - amount, min.getZ() - amount,
            max.getX() + amount, max.getY() + amount, max.getZ() + amount
        );
    }

    /**
     * Збільшує коробку на вказаний вектор.
     */
    public BoundingBox expand(Vector3D amount) {
        return new BoundingBox(
            min.subtract(amount),
            max.add(amount)
        );
    }

    /**
     * Переміщує коробку на вказаний вектор.
     */
    public BoundingBox offset(Vector3D offset) {
        return new BoundingBox(
            min.add(offset),
            max.add(offset)
        );
    }

    /**
     * Перевіряє, чи коробка порожня.
     */
    public boolean isEmpty() {
        return min.getX() >= max.getX() || 
               min.getY() >= max.getY() || 
               min.getZ() >= max.getZ();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoundingBox that = (BoundingBox) obj;
        return min.equals(that.min) && max.equals(that.max);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
               "min=" + min +
               ", max=" + max +
               '}';
    }
}