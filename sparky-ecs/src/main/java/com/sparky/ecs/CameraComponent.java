package com.sparky.ecs;

/**
 * Компонент камери для управління виглядом сцени.
 *
 * @author Андрій Будильников
 */
public class CameraComponent extends Component {
    private float x;              // Позиція камери по X
    private float y;              // Позиція камери по Y
    private float z;              // Позиція камери по Z
    private float zoom;           // Масштаб вигляду
    private float rotation;       // Обертання камери
    private float viewportWidth;  // Ширина вікна перегляду
    private float viewportHeight; // Висота вікна перегляду
    private boolean active;       // Чи є камера активною
    
    public CameraComponent() {
        this(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 800.0f, 600.0f, true);
    }
    
    public CameraComponent(float x, float y, float z, float zoom, float rotation, 
                          float viewportWidth, float viewportHeight, boolean active) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.zoom = zoom;
        this.rotation = rotation;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.active = active;
    }
    
    // Геттери та сеттери
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
    
    public float getZoom() {
        return zoom;
    }
    
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
    
    public float getRotation() {
        return rotation;
    }
    
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
    public float getViewportWidth() {
        return viewportWidth;
    }
    
    public void setViewportWidth(float viewportWidth) {
        this.viewportWidth = viewportWidth;
    }
    
    public float getViewportHeight() {
        return viewportHeight;
    }
    
    public void setViewportHeight(float viewportHeight) {
        this.viewportHeight = viewportHeight;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Перетворює світові координати в екранні координати.
     *
     * @param worldX світова координата X
     * @param worldY світова координата Y
     * @return масив з екранними координатами [screenX, screenY]
     */
    public float[] worldToScreen(float worldX, float worldY) {
        // Застосовуємо зміщення камери
        float relativeX = worldX - x;
        float relativeY = worldY - y;
        
        // Застосовуємо обертання
        double rad = Math.toRadians(rotation);
        float rotatedX = (float) (relativeX * Math.cos(rad) - relativeY * Math.sin(rad));
        float rotatedY = (float) (relativeX * Math.sin(rad) + relativeY * Math.cos(rad));
        
        // Застосовуємо масштаб
        float scaledX = rotatedX * zoom;
        float scaledY = rotatedY * zoom;
        
        // Центруємо відносно вікна перегляду
        float screenX = scaledX + viewportWidth / 2;
        float screenY = scaledY + viewportHeight / 2;
        
        return new float[]{screenX, screenY};
    }
    
    /**
     * Перетворює екранні координати в світові координати.
     *
     * @param screenX екранна координата X
     * @param screenY екранна координата Y
     * @return масив зі світовими координатами [worldX, worldY]
     */
    public float[] screenToWorld(float screenX, float screenY) {
        // Зміщуємо відносно центру вікна перегляду
        float centeredX = screenX - viewportWidth / 2;
        float centeredY = screenY - viewportHeight / 2;
        
        // Застосовуємо масштаб
        float scaledX = centeredX / zoom;
        float scaledY = centeredY / zoom;
        
        // Застосовуємо обертання (зворотнє)
        double rad = Math.toRadians(-rotation);
        float rotatedX = (float) (scaledX * Math.cos(rad) - scaledY * Math.sin(rad));
        float rotatedY = (float) (scaledX * Math.sin(rad) + scaledY * Math.cos(rad));
        
        // Застосовуємо позицію камери
        float worldX = rotatedX + x;
        float worldY = rotatedY + y;
        
        return new float[]{worldX, worldY};
    }
}