package com.sparky.commands;

/**
 * Дозвіл для команди.
 *
 * @author Богдан Кравчук
 */
public class Permission {
    private final String permissionNode;
    private final String description;
    
    public Permission(String permissionNode, String description) {
        this.permissionNode = permissionNode;
        this.description = description;
    }
    
    public String getPermissionNode() {
        return permissionNode;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Permission that = (Permission) obj;
        return permissionNode.equals(that.permissionNode);
    }
    
    @Override
    public int hashCode() {
        return permissionNode.hashCode();
    }
}