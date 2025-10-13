package com.sparky.rpc.ecs;

import java.util.Map;
import java.util.HashMap;

import com.sparky.rpc.RpcMessage;

/**
 * Повідомлення для синхронізації сутностей ECS між клієнтом і сервером.
 *
 * @author Андрій Будильников
 */
public class EntitySyncMessage extends RpcMessage {
    public static final String METHOD_CREATE_ENTITY = "create_entity";
    public static final String METHOD_UPDATE_ENTITY = "update_entity";
    public static final String METHOD_REMOVE_ENTITY = "remove_entity";
    public static final String METHOD_SYNC_COMPONENT = "sync_component";
    
    public EntitySyncMessage() {
        super();
    }
    
    public EntitySyncMessage(String method, int entityId, Map<String, Object> componentData, String id) {
        super(method, new HashMap<>(), id);
        
        // Додаємо ID сутності до параметрів
        getParams().put("entityId", entityId);
        
        // Додаємо дані компонента, якщо вони є
        if (componentData != null) {
            getParams().put("componentData", componentData);
        }
    }
    
    /**
     * Створює повідомлення для створення нової сутності.
     */
    public static EntitySyncMessage createEntityMessage(int entityId, String id) {
        return new EntitySyncMessage(METHOD_CREATE_ENTITY, entityId, null, id);
    }
    
    /**
     * Створює повідомлення для оновлення сутності.
     */
    public static EntitySyncMessage updateEntityMessage(int entityId, Map<String, Object> componentData, String id) {
        return new EntitySyncMessage(METHOD_UPDATE_ENTITY, entityId, componentData, id);
    }
    
    /**
     * Створює повідомлення для видалення сутності.
     */
    public static EntitySyncMessage removeEntityMessage(int entityId, String id) {
        return new EntitySyncMessage(METHOD_REMOVE_ENTITY, entityId, null, id);
    }
    
    /**
     * Створює повідомлення для синхронізації компонента.
     */
    public static EntitySyncMessage syncComponentMessage(int entityId, Map<String, Object> componentData, String id) {
        return new EntitySyncMessage(METHOD_SYNC_COMPONENT, entityId, componentData, id);
    }
    
    /**
     * Отримує ID сутності з повідомлення.
     */
    public int getEntityId() {
        Object entityIdObj = getParams().get("entityId");
        if (entityIdObj instanceof Number) {
            return ((Number) entityIdObj).intValue();
        }
        return -1;
    }
    
    /**
     * Отримує дані компонента з повідомлення.
     */
    public Map<String, Object> getComponentData() {
        Object componentDataObj = getParams().get("componentData");
        if (componentDataObj instanceof Map) {
            return (Map<String, Object>) componentDataObj;
        }
        return new HashMap<>();
    }
}