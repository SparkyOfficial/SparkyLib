package com.sparky.ecs;

import java.util.HashMap;
import java.util.Map;

/**
 * Представляє сутність в ECS.
 *
 * @author Андрій Будильников
 */
public class Entity {
    private final int id;
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    
    public Entity(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public <T extends Component> void addComponent(T component) {
        component.setEntityId(id);
        components.put(component.getClass(), component);
    }
    
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }
    
    public <T extends Component> boolean hasComponent(Class<T> componentClass) {
        return components.containsKey(componentClass);
    }
    
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        components.remove(componentClass);
    }
    
    /**
     * Отримує всі компоненти сутності.
     *
     * @return мапа компонентів
     */
    public Map<Class<? extends Component>, Component> getComponents() {
        return new HashMap<>(components);
    }
    
    /**
     * Отримує кількість компонентів у сутності.
     *
     * @return кількість компонентів
     */
    public int getComponentCount() {
        return components.size();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entity entity = (Entity) obj;
        return id == entity.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    
    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", components=" + components.size() +
                '}';
    }
}