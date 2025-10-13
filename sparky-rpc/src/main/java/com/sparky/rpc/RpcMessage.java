package com.sparky.rpc;

import java.util.Map;

import com.sparky.core.SerializationUtils;

/**
 * Базовий клас для RPC повідомлень.
 *
 * @author Андрій Будильников
 */
public class RpcMessage {
    private String method;
    private Map<String, Object> params;
    private String id;
    
    public RpcMessage() {}
    
    public RpcMessage(String method, Map<String, Object> params, String id) {
        this.method = method;
        this.params = params;
        this.id = id;
    }
    
    // Getters and setters
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public Map<String, Object> getParams() {
        return params;
    }
    
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Серіалізує повідомлення в JSON.
     */
    public String toJson() {
        try {
            return SerializationUtils.toJson(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize RPC message", e);
        }
    }
    
    /**
     * Десеріалізує повідомлення з JSON.
     */
    public static RpcMessage fromJson(String json) {
        try {
            return SerializationUtils.fromJson(json, RpcMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize RPC message", e);
        }
    }
}