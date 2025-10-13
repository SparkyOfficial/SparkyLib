package com.sparky.ecs;

/**
 * Компонент позиції для сутності.
 *
 * @author Андрій Будильников
 */
public class PositionComponent extends Component {
    private float x;
    private float y;
    private float z;
    
    public PositionComponent() {}
    
    public PositionComponent(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    // Getters and setters
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public float getY() {
        return y;
    }
    
    public void setY(float y) {
        this.y = y;
    }
    
    public float getZ() {
        return z;
    }
    
    public void setZ(float z) {
        this.z = z;
    }
}