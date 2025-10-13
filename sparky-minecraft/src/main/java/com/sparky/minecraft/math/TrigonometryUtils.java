package com.sparky.minecraft.math;

/**
 * Утилітарний клас для тригонометричних обчислень.
 *
 * @author Андрій Будильников
 */
public class TrigonometryUtils {
    
    // Константи
    public static final double PI = Math.PI;
    public static final double TWO_PI = 2 * Math.PI;
    public static final double HALF_PI = Math.PI / 2;
    public static final double QUARTER_PI = Math.PI / 4;
    
    /**
     * Перетворює градуси в радіани.
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }
    
    /**
     * Перетворює радіани в градуси.
     */
    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }
    
    /**
     * Нормалізує кут в радіанах до діапазону [0, 2π).
     */
    public static double normalizeAngle(double radians) {
        double result = radians % TWO_PI;
        if (result < 0) {
            result += TWO_PI;
        }
        return result;
    }
    
    /**
     * Нормалізує кут в градусах до діапазону [0, 360).
     */
    public static double normalizeAngleDegrees(double degrees) {
        double result = degrees % 360;
        if (result < 0) {
            result += 360;
        }
        return result;
    }
    
    /**
     * Обчислює відстань між двома кутами в радіанах.
     */
    public static double angleDistance(double angle1, double angle2) {
        double diff = normalizeAngle(angle1 - angle2);
        return Math.min(diff, TWO_PI - diff);
    }
    
    /**
     * Обчислює відстань між двома кутами в градусах.
     */
    public static double angleDistanceDegrees(double angle1, double angle2) {
        double diff = normalizeAngleDegrees(angle1 - angle2);
        return Math.min(diff, 360 - diff);
    }
    
    /**
     * Інтерполює між двома кутами в радіанах.
     */
    public static double lerpAngle(double start, double end, double t) {
        double diff = normalizeAngle(end - start);
        if (diff > Math.PI) {
            diff -= TWO_PI;
        }
        return normalizeAngle(start + diff * t);
    }
    
    /**
     * Інтерполює між двома кутами в градусах.
     */
    public static double lerpAngleDegrees(double start, double end, double t) {
        double diff = normalizeAngleDegrees(end - start);
        if (diff > 180) {
            diff -= 360;
        }
        return normalizeAngleDegrees(start + diff * t);
    }
    
    /**
     * Обчислює синус кута з оптимізацією для поширених значень.
     */
    public static double fastSin(double radians) {
        // Для простоти використовуємо стандартну реалізацію
        // В реальній реалізації можна використовувати таблиці пошуку
        return Math.sin(radians);
    }
    
    /**
     * Обчислює косинус кута з оптимізацією для поширених значень.
     */
    public static double fastCos(double radians) {
        // Для простоти використовуємо стандартну реалізацію
        // В реальній реалізації можна використовувати таблиці пошуку
        return Math.cos(radians);
    }
    
    /**
     * Обчислює тангенс кута.
     */
    public static double tan(double radians) {
        return Math.tan(radians);
    }
    
    /**
     * Обчислює арксинус.
     */
    public static double asin(double value) {
        return Math.asin(Math.max(-1.0, Math.min(1.0, value)));
    }
    
    /**
     * Обчислює арккосинус.
     */
    public static double acos(double value) {
        return Math.acos(Math.max(-1.0, Math.min(1.0, value)));
    }
    
    /**
     * Обчислює арктангенс.
     */
    public static double atan(double value) {
        return Math.atan(value);
    }
    
    /**
     * Обчислює арктангенс з двома аргументами.
     */
    public static double atan2(double y, double x) {
        return Math.atan2(y, x);
    }
    
    /**
     * Обчислює гіперболічний синус.
     */
    public static double sinh(double value) {
        return Math.sinh(value);
    }
    
    /**
     * Обчислює гіперболічний косинус.
     */
    public static double cosh(double value) {
        return Math.cosh(value);
    }
    
    /**
     * Обчислює гіперболічний тангенс.
     */
    public static double tanh(double value) {
        return Math.tanh(value);
    }
    
    /**
     * Перетворює сферичні координати в декартові.
     */
    public static com.sparky.minecraft.math.Vector3D sphericalToCartesian(double radius, double theta, double phi) {
        double x = radius * Math.sin(phi) * Math.cos(theta);
        double y = radius * Math.sin(phi) * Math.sin(theta);
        double z = radius * Math.cos(phi);
        return new com.sparky.minecraft.math.Vector3D(x, y, z);
    }
    
    /**
     * Перетворює декартові координати в сферичні.
     */
    public static double[] cartesianToSpherical(com.sparky.minecraft.math.Vector3D cartesian) {
        double radius = cartesian.length();
        if (radius == 0) {
            return new double[]{0, 0, 0};
        }
        
        double theta = atan2(cartesian.getY(), cartesian.getX());
        double phi = acos(cartesian.getZ() / radius);
        
        return new double[]{radius, theta, phi};
    }
}