package com.sparky.minecraft.math;

/**
 * Тривимірний вектор для математичних обчислень у Minecraft.
 *
 * @author Андрій Будильников
 */
public class Vector3D {
    private double x, y, z;
    
    public Vector3D() {
        this(0, 0, 0);
    }
    
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3D(Vector3D other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }
    
    
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getZ() {
        return z;
    }
    
    public void setZ(double z) {
        this.z = z;
    }
    
    /**
     * Додає інший вектор до цього вектора.
     */
    public Vector3D add(Vector3D other) {
        return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    
    /**
     * Віднімає інший вектор від цього вектора.
     */
    public Vector3D subtract(Vector3D other) {
        return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
    }
    
    /**
     * Множить вектор на скаляр.
     */
    public Vector3D multiply(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }
    
    /**
     * Ділить вектор на скаляр.
     */
    public Vector3D divide(double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return new Vector3D(this.x / scalar, this.y / scalar, this.z / scalar);
    }
    
    /**
     * Обчислює довжину (модуль) вектора.
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    /**
     * Обчислює квадрат довжини вектора (без квадратного кореня для ефективності).
     */
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }
    
    /**
     * Нормалізує вектор (робить його довжини 1).
     */
    public Vector3D normalize() {
        double len = length();
        if (len == 0) {
            return new Vector3D(0, 0, 0);
        }
        return divide(len);
    }
    
    /**
     * Обчислює скалярний добуток з іншим вектором.
     */
    public double dot(Vector3D other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }
    
    /**
     * Обчислює векторний добуток з іншим вектором.
     */
    public Vector3D cross(Vector3D other) {
        return new Vector3D(
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }
    
    /**
     * Обчислює відстань до іншого вектора.
     */
    public double distance(Vector3D other) {
        return subtract(other).length();
    }
    
    /**
     * Обчислює квадрат відстані до іншого вектора (без квадратного кореня для ефективності).
     */
    public double distanceSquared(Vector3D other) {
        return subtract(other).lengthSquared();
    }
    
    /**
     * Повертає кут між цим вектором та іншим у радіанах.
     */
    public double angle(Vector3D other) {
        double dot = this.dot(other);
        double lengths = this.length() * other.length();
        if (lengths == 0) {
            return 0;
        }
        return Math.acos(Math.max(-1.0, Math.min(1.0, dot / lengths)));
    }
    
    /**
     * Повертає кут між цим вектором та іншим у градусах.
     */
    public double angleDegrees(Vector3D other) {
        return Math.toDegrees(angle(other));
    }
    
    /**
     * Обертає вектор навколо осі X.
     */
    public Vector3D rotateX(double angleRadians) {
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        return new Vector3D(
            this.x,
            this.y * cos - this.z * sin,
            this.y * sin + this.z * cos
        );
    }
    
    /**
     * Обертає вектор навколо осі Y.
     */
    public Vector3D rotateY(double angleRadians) {
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        return new Vector3D(
            this.x * cos + this.z * sin,
            this.y,
            -this.x * sin + this.z * cos
        );
    }
    
    /**
     * Обертає вектор навколо осі Z.
     */
    public Vector3D rotateZ(double angleRadians) {
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        return new Vector3D(
            this.x * cos - this.y * sin,
            this.x * sin + this.y * cos,
            this.z
        );
    }
    
    /**
     * Інтерполює між цим вектором та іншим.
     */
    public Vector3D lerp(Vector3D other, double t) {
        t = Math.max(0, Math.min(1, t)); // Обмежуємо t між 0 та 1
        return new Vector3D(
            this.x + (other.x - this.x) * t,
            this.y + (other.y - this.y) * t,
            this.z + (other.z - this.z) * t
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector3D vector3D = (Vector3D) obj;
        return Double.compare(vector3D.x, x) == 0 &&
               Double.compare(vector3D.y, y) == 0 &&
               Double.compare(vector3D.z, z) == 0;
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y, z);
    }
    
    @Override
    public String toString() {
        return "Vector3D{" +
               "x=" + x +
               ", y=" + y +
               ", z=" + z +
               '}';
    }
}