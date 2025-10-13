package com.sparky.ecs;

/**
 * Компонент спрайту для візуального представлення сутності.
 *
 * @author Андрій Будильников
 */
public class SpriteComponent extends Component {
    private String textureId;     // ID текстури
    private float width;          // Ширина спрайту
    private float height;         // Висота спрайту
    private float rotation;       // Обертання спрайту в градусах
    private float scale;          // Масштаб спрайту
    private int layer;            // Шар відображення (для сортування)
    private float alpha;          // Прозорість (0.0 - повністю прозорий, 1.0 - повністю непрозорий)
    
    public SpriteComponent() {
        this("", 1.0f, 1.0f, 0.0f, 1.0f, 0, 1.0f);
    }
    
    public SpriteComponent(String textureId, float width, float height) {
        this(textureId, width, height, 0.0f, 1.0f, 0, 1.0f);
    }
    
    public SpriteComponent(String textureId, float width, float height, float rotation, float scale, int layer, float alpha) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.scale = scale;
        this.layer = layer;
        this.alpha = alpha;
    }
    
    // Геттери та сеттери
    public String getTextureId() {
        return textureId;
    }
    
    public void setTextureId(String textureId) {
        this.textureId = textureId;
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
    
    public float getRotation() {
        return rotation;
    }
    
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
    public float getScale() {
        return scale;
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public int getLayer() {
        return layer;
    }
    
    public void setLayer(int layer) {
        this.layer = layer;
    }
    
    public float getAlpha() {
        return alpha;
    }
    
    public void setAlpha(float alpha) {
        this.alpha = Math.max(0.0f, Math.min(1.0f, alpha));
    }
}