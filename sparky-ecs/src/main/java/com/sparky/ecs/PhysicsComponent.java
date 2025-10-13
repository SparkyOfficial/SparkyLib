package com.sparky.ecs;

/**
 * Компонент фізики для сутностей.
 * <p>
 * Зберігає фізичні властивості сутності, такі як маса, прискорення, сила тертя тощо.
 *
 * @author Андрій Будильников
 */
public class PhysicsComponent extends Component {
    private float mass;           // Маса об'єкта
    private float gravityScale;   // Масштаб гравітації (1.0 = нормальна гравітація)
    private float friction;       // Коефіцієнт тертя
    private float bounciness;     // Коефіцієнт пружності (0.0 = без відскоку, 1.0 = повний відскок)
    private boolean affectedByGravity; // Чи впливає гравітація на об'єкт
    
    // Поточні фізичні параметри
    private float accelerationX;
    private float accelerationY;
    private float accelerationZ;
    
    public PhysicsComponent() {
        this(1.0f, 1.0f, 0.1f, 0.3f, true);
    }
    
    public PhysicsComponent(float mass, float gravityScale, float friction, float bounciness, boolean affectedByGravity) {
        this.mass = mass;
        this.gravityScale = gravityScale;
        this.friction = friction;
        this.bounciness = bounciness;
        this.affectedByGravity = affectedByGravity;
    }
    
    // Геттери та сеттери
    public float getMass() {
        return mass;
    }
    
    public void setMass(float mass) {
        this.mass = mass;
    }
    
    public float getGravityScale() {
        return gravityScale;
    }
    
    public void setGravityScale(float gravityScale) {
        this.gravityScale = gravityScale;
    }
    
    public float getFriction() {
        return friction;
    }
    
    public void setFriction(float friction) {
        this.friction = friction;
    }
    
    public float getBounciness() {
        return bounciness;
    }
    
    public void setBounciness(float bounciness) {
        this.bounciness = bounciness;
    }
    
    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }
    
    public void setAffectedByGravity(boolean affectedByGravity) {
        this.affectedByGravity = affectedByGravity;
    }
    
    public float getAccelerationX() {
        return accelerationX;
    }
    
    public void setAccelerationX(float accelerationX) {
        this.accelerationX = accelerationX;
    }
    
    public float getAccelerationY() {
        return accelerationY;
    }
    
    public void setAccelerationY(float accelerationY) {
        this.accelerationY = accelerationY;
    }
    
    public float getAccelerationZ() {
        return accelerationZ;
    }
    
    public void setAccelerationZ(float accelerationZ) {
        this.accelerationZ = accelerationZ;
    }
    
    /**
     * Застосовує силу до об'єкта.
     *
     * @param forceX сила по осі X
     * @param forceY сила по осі Y
     * @param forceZ сила по осі Z
     */
    public void applyForce(float forceX, float forceY, float forceZ) {
        // F = ma => a = F/m
        this.accelerationX += forceX / mass;
        this.accelerationY += forceY / mass;
        this.accelerationZ += forceZ / mass;
    }
}