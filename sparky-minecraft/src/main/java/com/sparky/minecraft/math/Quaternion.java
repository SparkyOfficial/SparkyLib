package com.sparky.minecraft.math;

/**
 * Кватерніон для 3D обертань у Minecraft.
 *
 * @author Андрій Будильников
 */
public class Quaternion {
    private double w, x, y, z;

    public Quaternion() {
        this(1, 0, 0, 0);
    }

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternion(Quaternion other) {
        this.w = other.w;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    
    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
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
     * Створює кватерніон з кута обертання навколо осі.
     */
    public static Quaternion fromAxisAngle(Vector3D axis, double angle) {
        double halfAngle = angle / 2.0;
        double sinHalfAngle = Math.sin(halfAngle);
        Vector3D normalizedAxis = axis.normalize();
        
        return new Quaternion(
            Math.cos(halfAngle),
            normalizedAxis.getX() * sinHalfAngle,
            normalizedAxis.getY() * sinHalfAngle,
            normalizedAxis.getZ() * sinHalfAngle
        );
    }

    /**
     * Створює кватерніон з кутів Ейлера.
     */
    public static Quaternion fromEulerAngles(double yaw, double pitch, double roll) {
        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);

        double w = cr * cp * cy + sr * sp * sy;
        double x = sr * cp * cy - cr * sp * sy;
        double y = cr * sp * cy + sr * cp * sy;
        double z = cr * cp * sy - sr * sp * cy;

        return new Quaternion(w, x, y, z);
    }

    /**
     * Додає інший кватерніон до цього.
     */
    public Quaternion add(Quaternion other) {
        return new Quaternion(
            this.w + other.w,
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        );
    }

    /**
     * Віднімає інший кватерніон від цього.
     */
    public Quaternion subtract(Quaternion other) {
        return new Quaternion(
            this.w - other.w,
            this.x - other.x,
            this.y - other.y,
            this.z - other.z
        );
    }

    /**
     * Множить цей кватерніон на інший.
     */
    public Quaternion multiply(Quaternion other) {
        return new Quaternion(
            this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z,
            this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y,
            this.w * other.y - this.x * other.z + this.y * other.w + this.z * other.x,
            this.w * other.z + this.x * other.y - this.y * other.x + this.z * other.w
        );
    }

    /**
     * Множить кватерніон на скаляр.
     */
    public Quaternion multiply(double scalar) {
        return new Quaternion(
            this.w * scalar,
            this.x * scalar,
            this.y * scalar,
            this.z * scalar
        );
    }

    /**
     * Повертає спряжений кватерніон.
     */
    public Quaternion conjugate() {
        return new Quaternion(w, -x, -y, -z);
    }

    /**
     * Повертає обернений кватерніон.
     */
    public Quaternion inverse() {
        double normSquared = w * w + x * x + y * y + z * z;
        if (normSquared == 0) {
            throw new ArithmeticException("Cannot invert zero quaternion");
        }
        return conjugate().multiply(1.0 / normSquared);
    }

    /**
     * Обчислює норму кватерніона.
     */
    public double norm() {
        return Math.sqrt(w * w + x * x + y * y + z * z);
    }

    /**
     * Нормалізує кватерніон.
     */
    public Quaternion normalize() {
        double norm = norm();
        if (norm == 0) {
            return new Quaternion(1, 0, 0, 0);
        }
        return multiply(1.0 / norm);
    }

    /**
     * Інтерполює між цим кватерніоном та іншим (SLERP).
     */
    public Quaternion slerp(Quaternion other, double t) {
        // Нормалізуємо обидва кватерніони
        Quaternion q1 = this.normalize();
        Quaternion q2 = other.normalize();

        // Обчислюємо косинус кута між ними
        double dot = q1.w * q2.w + q1.x * q2.x + q1.y * q2.y + q1.z * q2.z;

        // Якщо кватерніони протилежні, обертаємо один з них
        if (dot < 0.0) {
            q2 = q2.multiply(-1);
            dot = -dot;
        }

        // Якщо кватерніони дуже близькі, використовуємо лінійну інтерполяцію
        if (dot > 0.9995) {
            return q1.add(q2.subtract(q1).multiply(t)).normalize();
        }

        // Обчислюємо кути
        double theta0 = Math.acos(Math.max(-1.0, Math.min(1.0, dot)));
        double theta = theta0 * t;
        double sinTheta = Math.sin(theta);
        double sinTheta0 = Math.sin(theta0);

        double s1 = Math.cos(theta) - dot * sinTheta / sinTheta0;
        double s2 = sinTheta / sinTheta0;

        return q1.multiply(s1).add(q2.multiply(s2)).normalize();
    }

    /**
     * Перетворює кватерніон в матрицю 4x4.
     */
    public Matrix4x4 toMatrix() {
        double xx = x * x;
        double xy = x * y;
        double xz = x * z;
        double xw = x * w;
        double yy = y * y;
        double yz = y * z;
        double yw = y * w;
        double zz = z * z;
        double zw = z * w;

        Matrix4x4 matrix = new Matrix4x4();
        matrix.set(0, 0, 1 - 2 * (yy + zz));
        matrix.set(0, 1, 2 * (xy - zw));
        matrix.set(0, 2, 2 * (xz + yw));
        matrix.set(1, 0, 2 * (xy + zw));
        matrix.set(1, 1, 1 - 2 * (xx + zz));
        matrix.set(1, 2, 2 * (yz - xw));
        matrix.set(2, 0, 2 * (xz - yw));
        matrix.set(2, 1, 2 * (yz + xw));
        matrix.set(2, 2, 1 - 2 * (xx + yy));

        return matrix;
    }

    /**
     * Повертає вектор, обернений цим кватерніоном.
     */
    public Vector3D rotate(Vector3D vector) {
        // q * v * q^-1
        Quaternion v = new Quaternion(0, vector.getX(), vector.getY(), vector.getZ());
        Quaternion result = this.multiply(v).multiply(this.inverse());
        return new Vector3D(result.x, result.y, result.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quaternion that = (Quaternion) obj;
        return Double.compare(that.w, w) == 0 &&
               Double.compare(that.x, x) == 0 &&
               Double.compare(that.y, y) == 0 &&
               Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(w, x, y, z);
    }

    @Override
    public String toString() {
        return "Quaternion{" +
               "w=" + w +
               ", x=" + x +
               ", y=" + y +
               ", z=" + z +
               '}';
    }
}