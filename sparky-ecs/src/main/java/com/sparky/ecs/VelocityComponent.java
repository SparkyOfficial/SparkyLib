package com.sparky.ecs;

/**
 * Компонент швидкості для сутності.
 *
 * @author Богдан Кравчук
 */
public class VelocityComponent extends Component {
    private float dx;
    private float dy;
    private float dz;
    
    public VelocityComponent(float dx, float dy, float dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
    
    public float getDx() {
        return dx;
    }
    
    public void setDx(float dx) {
        this.dx = dx;
    }
    
    public float getDy() {
        return dy;
    }
    
    public void setDy(float dy) {
        this.dy = dy;
    }
    
    public float getDz() {
        return dz;
    }
    
    public void setDz(float dz) {
        this.dz = dz;
    }
}