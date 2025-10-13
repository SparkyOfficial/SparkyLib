package com.sparky.ecs;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Система візуалізації для відображення сутностей з компонентами спрайтів.
 *
 * @author Андрій Будильников
 */
public class RenderingSystem extends System {
    private CameraComponent activeCamera;
    
    @Override
    public void update(List<Entity> entities) {
        // Знаходимо активну камеру
        findActiveCamera(entities);
        
        // Сортуємо сутності за шарами для правильного відображення
        List<Entity> sortedEntities = new ArrayList<>(entities);
        Collections.sort(sortedEntities, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                int layer1 = e1.hasComponent(SpriteComponent.class) ? 
                    e1.getComponent(SpriteComponent.class).getLayer() : 0;
                int layer2 = e2.hasComponent(SpriteComponent.class) ? 
                    e2.getComponent(SpriteComponent.class).getLayer() : 0;
                return Integer.compare(layer1, layer2);
            }
        });
        
        // Відображаємо кожну сутність
        for (Entity entity : sortedEntities) {
            if (entity.hasComponent(SpriteComponent.class) && entity.hasComponent(PositionComponent.class)) {
                renderEntity(entity);
            }
        }
    }
    
    /**
     * Знаходить активну камеру серед сутностей.
     */
    private void findActiveCamera(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.hasComponent(CameraComponent.class)) {
                CameraComponent camera = entity.getComponent(CameraComponent.class);
                if (camera.isActive()) {
                    this.activeCamera = camera;
                    return;
                }
            }
        }
    }
    
    /**
     * Відображає окрему сутність.
     */
    private void renderEntity(Entity entity) {
        PositionComponent position = entity.getComponent(PositionComponent.class);
        SpriteComponent sprite = entity.getComponent(SpriteComponent.class);
        
        // Розраховуємо екранні координати
        float screenX = position.getX();
        float screenY = position.getY();
        
        if (activeCamera != null) {
            float[] screenCoords = activeCamera.worldToScreen(position.getX(), position.getY());
            screenX = screenCoords[0];
            screenY = screenCoords[1];
        }
        
        // Виводимо інформацію про відображення (в реальній реалізації тут було б малювання)
        // System.out.println("Rendering entity " + entity.getId() + " at (" + screenX + ", " + screenY + 
        //                   ") with texture " + sprite.getTextureId());
    }
    
    /**
     * Отримує набір компонентів, необхідних для цієї системи.
     *
     * @return набір необхідних компонентів
     */
    @Override
    public Set<Class<? extends Component>> getRequiredComponents() {
        // Ця система працює з різними типами компонентів, тому не вимагає конкретних
        return new HashSet<>();
    }
    
    /**
     * Отримує сутності для цієї системи з урахуванням специфічних вимог.
     *
     * @param entityManager менеджер сутностей
     * @return список сутностей для системи
     */
    public List<Entity> getEntitiesForRendering(EntityManager entityManager) {
        List<Entity> allEntities = entityManager.getAllEntities();
        List<Entity> renderingEntities = new ArrayList<>();
        
        // Додаємо сутності з компонентами спрайтів або камер
        for (Entity entity : allEntities) {
            if (entity.hasComponent(SpriteComponent.class) || entity.hasComponent(CameraComponent.class)) {
                renderingEntities.add(entity);
            }
        }
        
        return renderingEntities;
    }
}