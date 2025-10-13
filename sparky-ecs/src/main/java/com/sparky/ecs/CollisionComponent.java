package com.sparky.ecs;

/**
 * Компонент колізії для сутностей.
 * <p>
 * Визначає форму та розміри об'єкта для виявлення колізій.
 *
 * @author Андрій Будильников
 */
public class CollisionComponent extends Component {
    public enum Shape {
        CIRCLE,
        RECTANGLE,
        SPHERE,
        BOX
    }
    
    private Shape shape;
    private float width;   // Ширина (для прямокутника/коробки)
    private float height;  // Висота (для прямокутника/коробки)
    private float depth;   // Глибина (для коробки)
    private float radius;  // Радіус (для кола/сфери)
    
    // Позиція колізії відносно позиції сутності
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    
    public CollisionComponent() {
        this(Shape.RECTANGLE, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public CollisionComponent(Shape shape, float width, float height, float depth, float radius, float offsetX, float offsetY, float offsetZ) {
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.radius = radius;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }
    
    public CollisionComponent(Shape shape, float width, float height, float depth, float offsetX, float offsetY, float offsetZ) {
        this(shape, width, height, depth, 0.0f, offsetX, offsetY, offsetZ);
    }
    
    public CollisionComponent(Shape shape, float radius, float offsetX, float offsetY, float offsetZ) {
        this(shape, 0.0f, 0.0f, 0.0f, radius, offsetX, offsetY, offsetZ);
    }
    
    // Геттери та сеттери
    public Shape getShape() {
        return shape;
    }
    
    public void setShape(Shape shape) {
        this.shape = shape;
    }
    
    public float getWidth() {
        return width;
    }
    
    public void setWidth(float width) {
        this.width = width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public void setHeight(float height) {
        this.height = height;
    }
    
    public float getDepth() {
        return depth;
    }
    
    public void setDepth(float depth) {
        this.depth = depth;
    }
    
    public float getRadius() {
        return radius;
    }
    
    public void setRadius(float radius) {
        this.radius = radius;
    }
    
    public float getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }
    
    public float getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }
    
    public float getOffsetZ() {
        return offsetZ;
    }
    
    public void setOffsetZ(float offsetZ) {
        this.offsetZ = offsetZ;
    }
    
    /**
     * Перевіряє, чи перетинається ця колізія з іншою.
     *
     * @param other інший компонент колізії
     * @param thisPosition позиція цієї сутності
     * @param otherPosition позиція іншої сутності
     * @return true, якщо є перетин
     */
    public boolean intersects(CollisionComponent other, PositionComponent thisPosition, PositionComponent otherPosition) {
        // Розраховуємо абсолютні позиції колізій
        float thisX = thisPosition.getX() + this.offsetX;
        float thisY = thisPosition.getY() + this.offsetY;
        float thisZ = thisPosition.getZ() + this.offsetZ;
        
        float otherX = otherPosition.getX() + other.offsetX;
        float otherY = otherPosition.getY() + other.offsetY;
        float otherZ = otherPosition.getZ() + other.offsetZ;
        
        // Проста перевірка для прямокутників 2D
        if (this.shape == Shape.RECTANGLE && other.shape == Shape.RECTANGLE) {
            float thisLeft = thisX - this.width / 2;
            float thisRight = thisX + this.width / 2;
            float thisTop = thisY - this.height / 2;
            float thisBottom = thisY + this.height / 2;
            
            float otherLeft = otherX - other.width / 2;
            float otherRight = otherX + other.width / 2;
            float otherTop = otherY - other.height / 2;
            float otherBottom = otherY + other.height / 2;
            
            return thisLeft < otherRight && thisRight > otherLeft &&
                   thisTop < otherBottom && thisBottom > otherTop;
        }
        
        // Проста перевірка для кіл 2D
        if (this.shape == Shape.CIRCLE && other.shape == Shape.CIRCLE) {
            float dx = thisX - otherX;
            float dy = thisY - otherY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            return distance < (this.radius + other.radius);
        }
        
        // Якщо форми різні або не підтримуються, повертаємо false
        return false;
    }
}