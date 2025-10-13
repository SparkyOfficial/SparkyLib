package com.sparky.rpc.ecs;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.sparky.rpc.RpcClient;
import com.sparky.rpc.RpcServer;
import com.sparky.rpc.RpcMessage;
import com.sparky.ecs.EntityManager;
import com.sparky.ecs.Entity;
import com.sparky.ecs.Component;

/**
 * Менеджер синхронізації сутностей ECS між клієнтом і сервером.
 *
 * @author Андрій Будильников
 */
public class EntitySyncManager {
    private final EntityManager entityManager;
    private RpcClient rpcClient;
    private RpcServer rpcServer;
    private final Map<Integer, Integer> entityNetworkIds; // локальний ID -> мережевий ID
    private final Map<Integer, Integer> networkEntityIds; // мережевий ID -> локальний ID
    private Consumer<EntitySyncMessage> syncHandler;
    
    public EntitySyncManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityNetworkIds = new ConcurrentHashMap<>();
        this.networkEntityIds = new ConcurrentHashMap<>();
    }
    
    /**
     * Встановлює RPC клієнта для синхронізації з сервером.
     */
    public void setRpcClient(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
        setupClientHandlers();
    }
    
    /**
     * Встановлює RPC сервер для синхронізації з клієнтами.
     */
    public void setRpcServer(RpcServer rpcServer) {
        this.rpcServer = rpcServer;
        setupServerHandlers();
    }
    
    /**
     * Встановлює обробник для вхідних повідомлень синхронізації.
     */
    public void setSyncHandler(Consumer<EntitySyncMessage> syncHandler) {
        this.syncHandler = syncHandler;
    }
    
    /**
     * Налаштовує обробники для клієнта.
     */
    private void setupClientHandlers() {
        if (rpcClient != null) {
            // Реєструємо обробник для відповідей від сервера
            // В реальній реалізації це було б більш складно, але для прикладу достатньо
        }
    }
    
    /**
     * Налаштовує обробники для сервера.
     */
    private void setupServerHandlers() {
        if (rpcServer != null) {
            // Реєструємо обробники для методів синхронізації
            rpcServer.registerHandler(EntitySyncMessage.METHOD_CREATE_ENTITY, this::handleCreateEntity);
            rpcServer.registerHandler(EntitySyncMessage.METHOD_UPDATE_ENTITY, this::handleUpdateEntity);
            rpcServer.registerHandler(EntitySyncMessage.METHOD_REMOVE_ENTITY, this::handleRemoveEntity);
            rpcServer.registerHandler(EntitySyncMessage.METHOD_SYNC_COMPONENT, this::handleSyncComponent);
        }
    }
    
    /**
     * Обробляє запит на створення сутності.
     */
    private RpcMessage handleCreateEntity(RpcMessage message) {
        EntitySyncMessage syncMessage = new EntitySyncMessage();
        syncMessage.setMethod(message.getMethod());
        syncMessage.setParams(message.getParams());
        syncMessage.setId(message.getId());
        
        // Створюємо нову сутність
        Entity entity = entityManager.createEntity();
        int localEntityId = entity.getId();
        
        // Зберігаємо відповідність між мережевим та локальним ID
        int networkEntityId = syncMessage.getEntityId();
        entityNetworkIds.put(localEntityId, networkEntityId);
        networkEntityIds.put(networkEntityId, localEntityId);
        
        // Викликаємо обробник, якщо він встановлений
        if (syncHandler != null) {
            syncHandler.accept(syncMessage);
        }
        
        // Повертаємо підтвердження
        Map<String, Object> responseParams = new HashMap<>();
        responseParams.put("status", "success");
        responseParams.put("localEntityId", localEntityId);
        
        return new RpcMessage("create_entity_response", responseParams, message.getId());
    }
    
    /**
     * Обробляє запит на оновлення сутності.
     */
    private RpcMessage handleUpdateEntity(RpcMessage message) {
        EntitySyncMessage syncMessage = new EntitySyncMessage();
        syncMessage.setMethod(message.getMethod());
        syncMessage.setParams(message.getParams());
        syncMessage.setId(message.getId());
        
        // Отримуємо локальний ID сутності
        int networkEntityId = syncMessage.getEntityId();
        Integer localEntityId = networkEntityIds.get(networkEntityId);
        
        if (localEntityId != null) {
            Entity entity = entityManager.getEntity(localEntityId);
            if (entity != null) {
                // Оновлюємо компоненти сутності
                // В реальній реалізації тут була б більш складна логіка
            }
        }
        
        // Викликаємо обробник, якщо він встановлений
        if (syncHandler != null) {
            syncHandler.accept(syncMessage);
        }
        
        // Повертаємо підтвердження
        Map<String, Object> responseParams = new HashMap<>();
        responseParams.put("status", "success");
        
        return new RpcMessage("update_entity_response", responseParams, message.getId());
    }
    
    /**
     * Обробляє запит на видалення сутності.
     */
    private RpcMessage handleRemoveEntity(RpcMessage message) {
        EntitySyncMessage syncMessage = new EntitySyncMessage();
        syncMessage.setMethod(message.getMethod());
        syncMessage.setParams(message.getParams());
        syncMessage.setId(message.getId());
        
        // Отримуємо локальний ID сутності
        int networkEntityId = syncMessage.getEntityId();
        Integer localEntityId = networkEntityIds.get(networkEntityId);
        
        if (localEntityId != null) {
            // Видаляємо сутність
            entityManager.removeEntity(localEntityId);
            
            // Видаляємо відповідності
            entityNetworkIds.remove(localEntityId);
            networkEntityIds.remove(networkEntityId);
        }
        
        // Викликаємо обробник, якщо він встановлений
        if (syncHandler != null) {
            syncHandler.accept(syncMessage);
        }
        
        // Повертаємо підтвердження
        Map<String, Object> responseParams = new HashMap<>();
        responseParams.put("status", "success");
        
        return new RpcMessage("remove_entity_response", responseParams, message.getId());
    }
    
    /**
     * Обробляє запит на синхронізацію компонента.
     */
    private RpcMessage handleSyncComponent(RpcMessage message) {
        EntitySyncMessage syncMessage = new EntitySyncMessage();
        syncMessage.setMethod(message.getMethod());
        syncMessage.setParams(message.getParams());
        syncMessage.setId(message.getId());
        
        // Отримуємо локальний ID сутності
        int networkEntityId = syncMessage.getEntityId();
        Integer localEntityId = networkEntityIds.get(networkEntityId);
        
        if (localEntityId != null) {
            Entity entity = entityManager.getEntity(localEntityId);
            if (entity != null) {
                // Синхронізуємо компонент
                // В реальній реалізації тут була б більш складна логіка
            }
        }
        
        // Викликаємо обробник, якщо він встановлений
        if (syncHandler != null) {
            syncHandler.accept(syncMessage);
        }
        
        // Повертаємо підтвердження
        Map<String, Object> responseParams = new HashMap<>();
        responseParams.put("status", "success");
        
        return new RpcMessage("sync_component_response", responseParams, message.getId());
    }
    
    /**
     * Синхронізує створення сутності з сервером.
     */
    public void syncEntityCreation(Entity entity) {
        if (rpcClient != null) {
            int localEntityId = entity.getId();
            int networkEntityId = localEntityId; // Для прикладу використовуємо однакові ID
            
            // Зберігаємо відповідність
            entityNetworkIds.put(localEntityId, networkEntityId);
            networkEntityIds.put(networkEntityId, localEntityId);
            
            // Відправляємо повідомлення серверу
            EntitySyncMessage message = EntitySyncMessage.createEntityMessage(networkEntityId, String.valueOf(System.currentTimeMillis()));
            rpcClient.call(message.getMethod(), message);
        }
    }
    
    /**
     * Синхронізує оновлення сутності з сервером.
     */
    public void syncEntityUpdate(Entity entity) {
        if (rpcClient != null) {
            Integer networkEntityId = entityNetworkIds.get(entity.getId());
            if (networkEntityId != null) {
                // В реальній реалізації тут були б дані компонентів
                Map<String, Object> componentData = new HashMap<>();
                
                // Відправляємо повідомлення серверу
                EntitySyncMessage message = EntitySyncMessage.updateEntityMessage(networkEntityId, componentData, String.valueOf(System.currentTimeMillis()));
                rpcClient.call(message.getMethod(), message);
            }
        }
    }
    
    /**
     * Синхронізує видалення сутності з сервером.
     */
    public void syncEntityRemoval(Entity entity) {
        if (rpcClient != null) {
            Integer networkEntityId = entityNetworkIds.get(entity.getId());
            if (networkEntityId != null) {
                // Відправляємо повідомлення серверу
                EntitySyncMessage message = EntitySyncMessage.removeEntityMessage(networkEntityId, String.valueOf(System.currentTimeMillis()));
                rpcClient.call(message.getMethod(), message);
                
                // Видаляємо відповідності
                entityNetworkIds.remove(entity.getId());
                networkEntityIds.remove(networkEntityId);
            }
        }
    }
}