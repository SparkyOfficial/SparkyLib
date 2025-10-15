package com.sparky.minecraft.network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Пакет даних для мережевої передачі.
 *
 * @author Андрій Будильников
 */
public class NetworkPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum PacketType {
        PLAYER_POSITION,
        PLAYER_ACTION,
        CHAT_MESSAGE,
        BLOCK_CHANGE,
        ENTITY_SPAWN,
        ENTITY_UPDATE,
        WORLD_DATA,
        INVENTORY_UPDATE,
        PLAYER_JOIN,
        PLAYER_LEAVE
    }
    
    private PacketType type;
    private Map<String, Object> data;
    private long timestamp;
    
    public NetworkPacket(PacketType type) {
        this.type = type;
        this.data = new HashMap<>();
        this.timestamp = System.currentTimeMillis();
    }
    
    // додає дані до пакету
    public void addData(String key, Object value) {
        data.put(key, value);
    }
    
    // отримує дані з пакету
    public Object getData(String key) {
        return data.get(key);
    }
    
    // отримує тип пакету
    public PacketType getType() {
        return type;
    }
    
    // отримує часову мітку
    public long getTimestamp() {
        return timestamp;
    }
    
    // отримує всі дані
    public Map<String, Object> getAllData() {
        return new HashMap<>(data);
    }
    
    @Override
    public String toString() {
        return "NetworkPacket{" +
                "type=" + type +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}